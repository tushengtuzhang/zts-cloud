package com.zts.service;

import com.hankcs.hanlp.seg.common.Term;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lycan on 2017/9/13.
 */
@Component
public class OTTokenizer {
    public static final String TAG_COMPANY_SEGMENT = "TAG_COMPANY_SEGMENT";
    public static HashMap<String, OTSegment> dicMap = new HashMap<String, OTSegment>();
    private static OTTokenizer instance;

    static {
        File f = new File(OTNlp.Config.CompanyDicRoot);
        if(f.isDirectory()){
            File[] fileArray=f.listFiles();
            if(fileArray!=null){
                for (File file : fileArray) {
                    if (!file.getName().contains("DS")){
                        getSegment(file.getAbsolutePath());
                    }
                }
            }
        }
    }


    private OTTokenizer() {
    }

    public static synchronized OTTokenizer getInstance() {
        if (instance == null) {
            instance = new OTTokenizer();
        }
        return instance;
    }

    public static OTSegment getSegment(String path) {
        OTSegment segment;
        String redisKey = TAG_COMPANY_SEGMENT + path;

        if (dicMap.get(redisKey) == null) {
            FileOperator.createDirectory(path);
            if(path.endsWith("0.txt")){
                segment = new OTSegment(path);
            }else{
                FileOperator.createFile(OTNlp.getCompanyDic(0));
                segment =new OTSegment(path,OTNlp.getCompanyDic(0));
            }

            dicMap.put(redisKey, segment);
        } else {
            segment = dicMap.get(redisKey);
        }
        return segment;
    }

    public static void refreshDic(Integer companyId){
        if (companyId != null) {
            String key = TAG_COMPANY_SEGMENT + OTNlp.getCompanyDic(companyId);
            dicMap.remove(key);
            getSegment(OTNlp.getCompanyDic(companyId));
        }
    }

    // 初始化的时候，将本类中的instancer赋值给静态的本类变量
    @PostConstruct
    public void init() {
        instance = this;
    }

    //指定词库文件
    public List<Term> segment(String text, String pathArray) {
        OTSegment segment = getSegment(pathArray);
        return segment.segByCustomDic(text);
    }
}
