package com.zts.service;

import com.zts.entity.Persona;
import com.zts.service.match.constant.CaseConstant;
import com.zts.util.redis.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangtusheng
 */
@Service
public class PersonaRedisService {

    @Resource
    private RedisUtils<String,List<Persona>> redisUtilsPersona;

    private static String PERSONA_PRE="persona_";

    private void save(String userId,List<Persona> personaList){
        redisUtilsPersona.save(PERSONA_PRE+userId,personaList,1,TimeUnit.HOURS);
    }

    List<Persona> list(String userId){
        List<Persona> personaList = redisUtilsPersona.get(PERSONA_PRE + userId);
        if(personaList==null||personaList.size()==0){
            personaList = new ArrayList<>();
        }
        return personaList;
    }

    Persona selectGeneral(Integer corpusTypeId, String userId){

        List<Persona> personaList = list(userId);

        for(Persona persona:personaList){
            if(persona.getLabelKey()!=null&&corpusTypeId!=null&&persona.getLabelKey().equals(String.valueOf(corpusTypeId))){
                return persona;
            }
        }

        return null;
    }

    Persona selectOneByCaseIdAndUserIdAndLabelType(Integer caseId,String userId,Integer labelTypeId,String labelKey){
        List<Persona> personaList = list(userId);

        for(Persona persona:personaList){
            if(persona.getCaseId()!=null&&caseId!=null&&persona.getCaseId().equals(caseId)
                    &&persona.getLabelType()!=null&&labelTypeId!=null&&persona.getLabelType().equals(String.valueOf(labelTypeId))
                    &&persona.getLabelKey()!=null&&labelKey!=null&&persona.getLabelKey().equals(labelKey)){
                return persona;
            }
        }

        return null;
    }

    void updateSetLastUseTime(Persona persona){

        List<Persona> personaList = list(persona.getUserId());
        if(personaList.size()>0){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            persona.setRedisId(uuid);
            personaList.add(persona);

            this.save(persona.getUserId(),personaList);
        }

    }

    void saveOrUpdate(Integer general,Persona persona,String value){
        if(this.get(general,persona)!=null){
            persona=this.get(general,persona);

            List<Persona> personaList = list(persona.getUserId());
            if(personaList.size()>0){
                for(Persona p:personaList){
                    if(p.getRedisId().equals(persona.getRedisId())){
                        //p.setType(type);
                        p.setLabelValue(value);
                        p.setUpdateTime(new Date());
                        break;
                    }
                }
            }else{
                personaList.add(persona);
            }

            this.save(persona.getUserId(),personaList);

        }else{
            //persona.setType(type);
            persona.setLabelValue(value);
            persona.setCreateTime(new Date());

            List<Persona> personaList = list(persona.getUserId());

            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            persona.setRedisId(uuid);
            personaList.add(persona);

            this.save(persona.getUserId(),personaList);

        }
    }



    Persona get(Integer general,Persona persona){

        List<Persona> personaList=this.list(persona.getUserId());

        for(Persona p:personaList){
            if(p.getCompany().getId().equals(persona.getCompany().getId()) && p.getLabelType().equals(String.valueOf(persona.getLabelType()))){
                //专用
                if("0".equals(persona.getLabelType())||general== CaseConstant.CaseActionGeneral.ITSELF.getValue()) {
                    if(p.getCaseId()!=null&&persona.getCaseId()!=null&&p.getCaseId().equals(persona.getCaseId())&&p.getLabelKey()!=null&&persona.getLabelKey()!=null&&p.getLabelKey().equals(persona.getLabelKey())){
                        return p;
                    }
                }else {
                    if(p.getCaseId()==null){
                        return p;
                    }

                }
            }
        }

        return null;
    }

    void deletePersona(Integer companyId, String userId, Integer caseId, Integer labelType, String labelKey, Integer general) {
        List<Persona> personaList = list(userId);
        for(Persona p:personaList){
            if(p.getCompany().getId().equals(companyId) && p.getLabelType().equals(String.valueOf(labelType))){
                //专用
                if(labelType==0||general==CaseConstant.CaseActionGeneral.ITSELF.getValue()) {
                    if(p.getCaseId().equals(caseId)&&p.getLabelKey().equals(labelKey)){
                        //移除对象操作
                        personaList.remove(p);
                        break;
                    }
                }else {
                    if(p.getCaseId()==null){
                        //移除对象操作
                        personaList.remove(p);
                        break;
                    }

                }
            }
        }
        this.save(userId,personaList);
    }
}
