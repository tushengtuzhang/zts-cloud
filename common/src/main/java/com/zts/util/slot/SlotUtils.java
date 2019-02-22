package com.zts.util.slot;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangtusheng
 */
@Slf4j
public class SlotUtils {

    public static boolean isSlotQuestion(String question){
        String zuoDaKuoHao="{";
        String youDaKuoHao="}";
        if(question.contains(zuoDaKuoHao)&&question.contains(youDaKuoHao)){
            return question.indexOf(zuoDaKuoHao)<question.indexOf(youDaKuoHao);
        }
        return false;
    }

    public static boolean slotMatch(String input,String question){
        question = question.replaceAll("\\{(.+?)}", "(.+?)");
        question = ".*?" + question + ".*?";

        boolean match = Pattern.matches(question, input);

        return match;
    }

    public static List<String> getSlotKeyList(String question){
        String reg="\\{(.+?)}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(question);

        List<String> keyList=new ArrayList<>();
        //部分匹配
        while(matcher.find()){
            int i=1;
            keyList.add(matcher.group(i));
            i++;
        }
        return keyList;
    }

    public static List<String> getSlotValueList(String input, String question){
        String questionRegex = ".*?" + question.replaceAll("\\{(.+?)}", "\\(.+?\\)") + ".*?";

        List<String> valueList = new ArrayList<>();

        Pattern pattern = Pattern.compile(questionRegex);
        Matcher matcher = pattern.matcher(input);
        //全部匹配
        if (matcher.matches()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                valueList.add(matcher.group(i));
            }
        }
        return valueList;
    }

    public static void main(String[] args){
        String input="我想了解从珠海到北京的茉莉花呢";
        String question="{start}到{end}茉莉花";
        System.out.println(isSlotQuestion(question));

        boolean match= slotMatch(input,question);
        System.out.println(match);
        List<String> keyList=getSlotKeyList(question);
        System.out.println(keyList);

        List<String> valueList= getSlotValueList(input,question);
        System.out.println(valueList);

    }
}
