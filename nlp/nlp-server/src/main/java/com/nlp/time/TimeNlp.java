package com.nlp.time;


import com.nlp.time.nlp.TimeNormalizer;
import com.nlp.time.nlp.TimeUnit;
import com.nlp.time.util.DateUtil;

/**
 * @author zhangtusheng
 */
public class TimeNlp {

    private static TimeNlp instance=null;

    private static TimeNormalizer normalizer=null;

    public static TimeNlp getInstance(){
        if(instance==null){
            normalizer = new TimeNormalizer();
            instance=new TimeNlp();
        }

        return instance;
    }

    public TimeUnit[] getTime(String ask){

        normalizer.parse(ask);
        return normalizer.getTimeUnit();
    }

    public static void print(String ask){
        TimeUnit[] timeUnits = TimeNlp.getInstance().getTime(ask);
        for(TimeUnit timeUnit:timeUnits){
            System.out.println(timeUnit.getTime());
            System.out.println(timeUnit.Time_Expression);
            System.out.println(timeUnit.Time_Norm);
            System.out.println(DateUtil.formatDate(timeUnit.getTime(),"yyyy-MM-dd HH:mm:ss"));
        }
        System.out.println("----------------------");
    }

    public static void main(String[] args){
        print("今天天气怎么样周一哈哈下周");
        print("下个月去那里玩");
        print("明天晚上十一点半");
        print("后天凌晨两点半");
        print("三点");

        print("去年九月");

        print("大大后天十一点二十三分四十五秒");

        print("去年");

        print("上半年");

        print("明天下午到后天早上");

    }



}
