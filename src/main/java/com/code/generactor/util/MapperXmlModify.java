package com.code.generactor.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangYanHai
 * @className: MapperXmlModify
 * @createDate 2021/8/8 17:35
 */
public class MapperXmlModify {

    public static String getUpdateIdUpdate(String str){
        str = str.substring(str.indexOf("id=\"update\""));
        return str.substring(0,str.indexOf("</set>"));
    }

    public static List<String> lineList1(String str) throws IOException {
        List<String> list = new ArrayList<>();
        String[] split = str.split("test=");
        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            list.add(s.substring(1,s.indexOf("!")));
        }
        return list;
    }

    public static Map<String,String> getMapParam(String paramsNews) throws IOException {
        Map<String,String> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(paramsNews.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        String paramLine = "";
        String param = "";
        while ((line = br.readLine()) != null) {
            int index = line.indexOf("test=");
            if(index!=-1){
                paramLine = line+"\r\n";
                String lowerHalf = line.split("test=")[1];
                param = lowerHalf.substring(1,lowerHalf.indexOf("!"));
            }else if(line.contains("</if>")){
                paramLine += line;
                map.put(param,paramLine);
            }else {
                paramLine += line+"\r\n";
            }
        }

        return map;
    }

    public static String replaceSqlIdDoQueryParams(StringBuilder sb,String fileContent) throws IOException {
        String params = getUpdateIdUpdate(fileContent);
        String paramsNew = getUpdateIdUpdate(sb.toString());

        List<String> paramList = lineList1(params);
        List<String> paramNewList = lineList1(paramsNew);

        //需要新增的param
        List<String> needAddParamNewList = new ArrayList<>();
        for (String paramNew : paramNewList) {
            if(!paramList.contains(paramNew)){
                needAddParamNewList.add(paramNew);
            }
        }

        Map<String,String> map = getMapParam(paramsNew);
        String resultParam = params;
        for (String param : needAddParamNewList) {
            int indexOf = paramNewList.indexOf(param);
            String lastParam = paramNewList.get(indexOf - 1);

            int index = resultParam.indexOf("test=\""+lastParam+"!");
            String temp = resultParam.substring(index);
            int last = temp.indexOf("</if>")+5;
            resultParam = resultParam.substring(0,index+last)+"\r\n"+map.get(param)+resultParam.substring(index+last);
        }

        int start = fileContent.indexOf("id=\"update\"");
        String content = fileContent.substring(start);
        int end = content.indexOf("</set>");
        return fileContent.substring(0,start)+resultParam+content.substring(end);
    }

    public static String getNewContent(StringBuilder sb,String fileContent) throws IOException {
        //sql id="doQueryParams"
        fileContent = replaceSqlIdDoQueryParams(sb,fileContent);
        return fileContent;
    }

}
