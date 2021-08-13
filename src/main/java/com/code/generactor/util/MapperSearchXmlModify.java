package com.code.generactor.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangYanHai
 * @className: MapperXmlModify
 * @description: https://blog.csdn.net/weixin_44580670/article/details/89930499
 * @createDate 2021/8/8 17:35
 */
public class MapperSearchXmlModify {

    /**
     * 将新的xml的column放入集合中
     * @param str
     * @return
     * @throws IOException
     */
    public static List<String> lineList(String str) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            int index = line.indexOf("column=");
            if(index!=-1){
                list.add(line);
            }
        }
        return list;
    }

    public static String getColumn(String str){
        str = str.substring(str.indexOf("column=")+8);
        return str.substring(0,str.indexOf("\""));
    }

    public static String getResultMap(String str){
        int start = str.indexOf("<resultMap");
        int end = str.indexOf("</resultMap>")+12;
        return str.substring(start,end);
    }

    public static String getSqlIdDoColumns(String str){
        int start = str.indexOf("id=\"do_columns\"");
        int end = str.indexOf("</sql>");
        return str.substring(start,end);
    }

    public static String getSqlIdDoQueryParams(String str){
        str = str.substring(str.indexOf("id=\"doQueryParams\""));
        return str.substring(0,str.indexOf("</sql>"));
    }

    public static String getNewResultMap1(String contentNew,String content) throws IOException {
        StringBuilder text = new StringBuilder();
        List<String> newList = lineList(contentNew);

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        String line;
        int lineNum = 0;
        //只处理增加字段
        while ((line = br.readLine()) != null) {
            int index = line.indexOf("column=");
            if(index!=-1){
                String column = getColumn(line);
                String columnLineNew = newList.get(lineNum);
                while (!column.equals(getColumn(columnLineNew))){
                    text.append(columnLineNew).append("\r\n");
                    System.out.println(columnLineNew);
                    newList.remove(lineNum);
                    columnLineNew = newList.get(lineNum);
                }
                text.append(line).append("\r\n");
                System.out.println(line);
                lineNum++;
            }else {
                text.append(line).append("\r\n");
            }
        }
        System.out.println("text");
        System.out.println(text);
        return text.toString();
    }

    /**
     * 完整替换ResultMap
     * @param sb
     * @param fileContent
     */
    public static String replaceResultMap(StringBuilder sb,String fileContent){
        String newResultMap = getResultMap(sb.toString());
        int start = fileContent.indexOf("<resultMap");
        int end = fileContent.indexOf("</resultMap>")+12;
       return fileContent.substring(0,start)+newResultMap+fileContent.substring(end);
    }

    /**
     * 完整替换sql id="do_columns"
     * @param sb
     * @param fileContent
     */
    public static String replaceSqlIdDoColumns(StringBuilder sb,String fileContent){
        String sqlIdDoColumns = getSqlIdDoColumns(sb.toString());
        int start = fileContent.indexOf("id=\"do_columns\"");
        String content = fileContent.substring(start);
        int end = content.indexOf("</sql>");
        return fileContent.substring(0,start)+sqlIdDoColumns+content.substring(end);
    }

    public static String getNewContent(StringBuilder sb,String fileContent) throws IOException {
        //resultMap
        fileContent = replaceResultMap(sb, fileContent);
        //sql id="do_columns"
        fileContent = replaceSqlIdDoColumns(sb,fileContent);
        //sql id="doQueryParams"
        fileContent = replaceSqlIdDoQueryParams(sb,fileContent);
        return fileContent;
    }

    public static void main(String[] args) throws IOException {
        /*String[] split = ParamsNes.split("</if>");
        for (String s : split) {
            System.out.println(s);
        }*/
        //lineList2(null);
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
        String params = getSqlIdDoQueryParams(fileContent);
        String paramsNew = getSqlIdDoQueryParams(sb.toString());

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

        int start = fileContent.indexOf("id=\"doQueryParams\"");
        String content = fileContent.substring(start);
        int end = content.indexOf("</sql>");
        return fileContent.substring(0,start)+resultParam+content.substring(end);
    }


}
