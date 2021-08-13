package com.code.generactor.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangYanHai
 * @className: BeanModify
 * @description:
 * @createDate 2021/8/13 12:17
 */
public class BeanModify {
    public static void main(String[] args) throws IOException {
        String content = FileUtil.readFileContent(new File("E:\\generactor\\question_test\\QuestionTestDto.java"));

        String contentNew = FileUtil.readFileContent(new File("E:\\generactor\\question_test\\QuestionTestNewDto.java"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contentNew);

        String s = replaceBean(stringBuilder, content);
        System.out.println(s);

    }


    public static List<String> getBeanName(String content){
        List<String> list = new ArrayList<>();
        String[] array = content.split("private");
        for (String s : array) {
            if(s.contains("public") || s.contains("static")){
                continue;
            }
            String[] s1 = s.split(" ");
            String s2 = s1[2];
            String s3 = s2.split(";")[0];
            list.add(s3);
        }
        return list;
    }

    public static Map<String,String> getBeanNames(String contentNew){
        Map<String,String> map = new HashMap<>();
        contentNew = contentNew.substring(contentNew.indexOf("1L;")+3);
        String[] split = contentNew.split(";");
        for (String s : split) {
            if(s.contains("}")){
                continue;
            }
            String beanStr = s+";\r\n";
            String[] s1 = s.split(" ");
            String beanName = s1[s1.length - 1];
            map.put(beanName,beanStr);
        }
        return map;
    }

    public static String replaceBean(StringBuilder sb,String fileContent) throws IOException {
        List<String> beanNameList = getBeanName(fileContent);
        List<String> beanNameNewList = getBeanName(sb.toString());

        //需要新增的beanName
        List<String> needAddBeanNewList = new ArrayList<>();
        for (String paramNew : beanNameNewList) {
            if(!beanNameList.contains(paramNew)){
                needAddBeanNewList.add(paramNew);
            }
        }

        Map<String,String> map = getBeanNames(sb.toString());
        String resultContent = fileContent;
        for (String beanName : needAddBeanNewList) {
            int indexOf = beanNameNewList.indexOf(beanName);
            String lastBeanName = beanNameNewList.get(indexOf - 1);

            String[] split = resultContent.split(" " + lastBeanName + ";");

            resultContent = split[0]+" " + lastBeanName + ";"+ map.get(beanName) +split[1];
        }


        return resultContent;
    }

    public static String getNewContent(StringBuilder sb,String fileContent) throws IOException {
        fileContent = replaceBean(sb,fileContent);
        return fileContent;
    }
}
