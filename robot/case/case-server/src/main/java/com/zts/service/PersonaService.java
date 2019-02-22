package com.zts.service;

import com.zts.dao.IBaseDao;
import com.zts.dao.PersonaDao;
import com.zts.entity.Persona;
import com.zts.feign.CompanyFeign;
import com.zts.service.impl.BaseServiceImpl;
import com.zts.util.redis.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtusheng
 */
@Service
public class PersonaService extends BaseServiceImpl<Persona,Integer> {
    @Resource
    private PersonaDao personaDao;

    @Resource
    private CompanyFeign companyFeign;

    @Resource
    private PersonaRedisService personaRedisService;

    @Override
    public IBaseDao<Persona, Integer> getBaseDao() {
        return personaDao;
    }

    public Persona selectGeneral(Integer corpusTypeId,String userId,boolean isAnonymous){//isAnonymous 1 匿名用户
        //匿名用户
        if(isAnonymous){
            return personaRedisService.selectGeneral(corpusTypeId, userId);

        }else{
            return this.selectGeneral(corpusTypeId,userId);
        }
    }

    /**
     * 通用
     * @param corpusTypeId corpusTypeId
     * @param userId userId
     * @return Persona
     */
    private Persona selectGeneral(Integer corpusTypeId,String userId){
        /*Example example=new Example(Persona.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("labelType",corpusTypeId).andEqualTo("bounded",false);



        return this.selectOneByExample(example);*/
        return null;
    }

    /**
     * 删除用户画像
     * @param companyId companyId
     * @param userId userId
     * @param caseId caseId
     * @param corpusTypeId corpusTypeId
     * @param actionName actionName
     * @param actionGeneral actionGeneral
     */
    private void deletePersona(Integer companyId,String userId,Integer caseId,Integer corpusTypeId,String actionName,Integer actionGeneral){
        /*Example example=new Example((Persona.class));
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("companyId",companyId)
                .andEqualTo("userId",userId)
                .andEqualTo("labelType",corpusTypeId)
                .andEqualTo("bounded",false);
        //专用（包含自定义动作）
        if(actionGeneral.equals(CaseConstant.CaseActionGeneral.ITSELF.getValue())||corpusTypeId==0){
            criteria.andEqualTo("caseId",caseId)
                    .andEqualTo("labelKey",actionName);
        }else{//通用
            criteria.andIsNull("caseId");
        }
        this.deleteByExample(example);*/
    }

    /**
     * 删除用户画像
     * @param companyId companyId
     * @param userId userId
     * @param caseId caseId
     * @param corpusTypeId corpusTypeId
     * @param actionName actionName
     * @param actionGeneral actionGeneral
     * @param isAnonymous isAnonymous
     */
    public void deletePersona(Integer companyId,String userId,Integer caseId,Integer corpusTypeId,String actionName,Integer actionGeneral,boolean isAnonymous){
        if(isAnonymous){
            personaRedisService.deletePersona(companyId ,userId, caseId, corpusTypeId, actionName, actionGeneral);
        }else{
            deletePersona(companyId ,userId, caseId, corpusTypeId, actionName, actionGeneral);
        }
    }

    /**
     * 专用
     * @param caseId caseId
     * @param userId userId
     * @param labelTypeId labelTypeId
     * @param labelKey labelKey
     * @param isAnonymous isAnonymous
     * @return Persona
     */
    public Persona selectOneByCaseIdAndUserIdAndLabelType(Integer caseId,String userId,Integer labelTypeId,String labelKey,boolean isAnonymous){
        //匿名用户
        if(isAnonymous){
            return personaRedisService.selectOneByCaseIdAndUserIdAndLabelType(caseId,userId,labelTypeId,labelKey);

        }else{
            return selectOneByCaseIdAndUserIdAndLabelType(caseId,userId,labelTypeId,labelKey);
        }
    }

    private Persona selectOneByCaseIdAndUserIdAndLabelType(Integer caseId,String userId,Integer labelTypeId,String labelKey){
        /*Example example=new Example(Persona.class);
        example.createCriteria().andEqualTo("caseId",caseId).andEqualTo("labelType",labelTypeId)
                .andEqualTo("labelKey",labelKey).andEqualTo("userId",userId).andEqualTo("bounded",false);
        return this.selectOneByExample(example);*/
        return null;
    }


    public void saveOrUpdate(Integer general,Persona persona,String value,boolean isAnonymous){
        if(isAnonymous){
            personaRedisService.saveOrUpdate(general, persona, value);
        }else{
            saveOrUpdate(general, persona, value);
        }

    }

    public void saveOrUpdate(Integer general,Persona persona,String value){
        if(this.get(general,persona)!=null){
            persona=this.get(general,persona);
            if(persona!=null){
                persona.setLabelValue(value);
                persona.setUpdateTime(new Date());
                this.save(persona);
            }
        }else{
            persona.setLabelValue(value);
            persona.setCreateTime(new Date());

            this.save(persona);
        }
    }

    public void updateSetLastUseTime(Persona persona,boolean isAnonymous){
        persona.setLastUseTime(new Date());

        if(isAnonymous){
            personaRedisService.updateSetLastUseTime(persona);
        }else{
            this.save(persona);
        }

    }

    public Persona get(Integer general,Persona persona,boolean isAnonymous){
        if(isAnonymous){
            return personaRedisService.get(general,persona);

        }else{
            return this.get(general,persona);
        }
    }

    private Persona get(Integer general,Persona persona){
        /*Example example=new Example(Persona.class);
        Example.Criteria criteria = example.createCriteria()
                .andEqualTo("companyId", persona.getCompanyId())
                .andEqualTo("userId", persona.getUserId())
                .andEqualTo("labelType",persona.getLabelType())
                .andEqualTo("labelName",persona.getLabelName())
                .andEqualTo("labelKey",persona.getLabelKey()).andEqualTo("bounded",false);

        //通用
        if(general==1){
            criteria.andIsNull("caseId");
        }else{ //专用
            criteria.andEqualTo("caseId",persona.getCaseId());
        }

        List<Persona> personaList = this.selectByExample(example);
        if(personaList!=null&&personaList.size()>0){
            return personaList.get(0);
        }*/

        return null;
        //return this.selectOneByExample(example);
    }

    public List<Persona> selectListByBounded(Integer companyId,String identification,Integer labelType,String labelKey,boolean bounded){
        Map<String, Object> params=new HashMap<>(5);
        params.put("companyId",companyId);
        params.put("identification",identification);
        params.put("labelType",labelType);
        params.put("labelKey",labelKey);
        params.put("bounded",true);
        return this.selectListByParams(params);
    }

    public List<Persona> selectListByParams(Map<String,Object> params){

        return null;//mapper.selectListByParams(params);
    }

    public int selectCountByParams(Map<String,Object> params){
        return 0; //mapper.selectCountByParams(params);
    }

    public Persona selectById(Integer id){
        return personaDao.selectById(id);
    }

    /**
     * 插入 受限用户画像
     * @param companyId 公司Id
     * @param identification 对应companyUser的identification
     * @param labelType 对应comCorpusType的id
     * @param labelName 对应compCorpusType的name
     * @param labelKey  对应动作的name,即英文字母，eg:xiangmu,renyuan
     * @param labelValue 对应compCorpus的name
     */
    public void insertBoundedPersona(Integer companyId,String identification,Integer labelType,String labelName,String labelKey,String labelValue){
        Persona persona=new Persona();
        persona.setCompany(companyFeign.get(companyId));
        persona.setUserId(identification);
        persona.setLabelType(String.valueOf(labelType));
        persona.setLabelName(labelName);
        persona.setLabelKey(labelKey);
        persona.setLabelValue(labelValue);

        persona.setCreateTime(new Date());

        persona.setBounded(true);

        this.save(persona);
    }


    /**
     * 根据公司Id删除 受限的用户画像
     * @param companyId 公司Id
     */
    public void deleteBoundedListByCompanyId(Integer companyId){

        personaDao.deleteBoundedListByCompanyId(companyId);

    }
}
