package com.code.generactor.upload;

import com.code.generactor.model.Column;
import com.code.generactor.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoHai
 */
public class MapperSearchXml extends CommonUtil {
    /**
     * 生成mapper.xml文件
     *
     * @param columnList
     */
    public static void uploadXmlFile(List<Column> columnList) {
        sb = new StringBuilder();

        resultMap(sb, columnList);

        sqlDoColumns(sb, columnList);

        sqlDoQueryParams(sb, columnList);

        select(sb,columnList);

        selectOne(sb);

        listByExample(sb);

        countListByExample(sb);

        sb.append(RN);
        sb.append("</mapper>");



        FileUtil.outputReplace(sb, mapperSearchName, ".xml", mapperXmlFolderUrl);
    }

    public static void resultMap(StringBuilder sb, List<Column> columnList) {
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
                .append(RN)
                .append("<!DOCTYPE mapper PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >")
                .append(RN)
                .append(String.format("<mapper namespace=\"%s\" >", mapperPackage + "." + mapperSearchName))
                .append(RN)
                .append(RN_SPACE)
                .append(String.format("<resultMap id=\"%s\"  type=\"%s\" >", resultMap, beanUrl));


        for (Column column : columnList) {
            if ("id".equals(column.getColumnName())) {
                sb.append(RN_SPACE).append(SPACE).append(String.format("<id column=\"%s\" property=\"%s\"  />", column.getColumnName(), column.getModelName()));
                continue;
            }
            sb.append(RN_SPACE).append(SPACE).append(String.format("<result column=\"%s\" property=\"%s\" />", column.getColumnName(), column.getModelName()));
        }
        sb.append(RN_SPACE + "</resultMap>");
        sb.append(RN);
    }

    public static void sqlDoColumns(StringBuilder sb, List<Column> columnList) {
        sb.append(RN_SPACE + "<sql id=\"do_columns\">");
        sb.append(RN_SPACE + SPACE);
        StringBuilder columns = new StringBuilder();
        for (Column column : columnList) {
            columns.append(column.getColumnName() + ",");
        }
        sb.append(columns.substring(0, columns.length() - 1));
        sb.append(RN_SPACE + "</sql>" + RN);
    }

    public static void sqlDoQueryParams(StringBuilder sb, List<Column> columnList) {
        sb.append(RN_SPACE + "<sql id=\"doQueryParams\">");
        sb.append(RN_SPACE + "  <where >");
        StringBuilder condition = new StringBuilder();

        List<Column> columnList1 = new ArrayList<Column>();
        Column d = new Column();
        d.setColumnDesc("");
        d.setModelType("Integer");
        d.setModelName("ids");
        d.setColumnName("ids");
        columnList1.add(d);

        for (Column column : columnList) {
            if ("String".equals(column.getModelType()) || "TIMESTAMP".equals(column.getColumnType())) {
                condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<if test=\"" + column.getModelName() + "!= null and " + column.getModelName() + "!='' \">");
            } else {
                condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<if test=\"" + column.getModelName() + "!= null\">");
            }
            switch (column.getColumnName()) {
                case "create_date":
                    condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<![CDATA[  and " + column.getColumnName() + " >= #{" + column.getModelName() + "} ]]>");
                    break;
                case "update_date":
                    condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<![CDATA[  and " + column.getColumnName() + " <= #{" + column.getModelName() + "} ]]>");
                    break;
                default:
                    condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "  and " + column.getColumnName() + " = #{" + column.getModelName() + "}");
            }
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</if>");
        }
        for (Column column : columnList1) {
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<if test=\"" + column.getModelName() + "!= null and " + column.getModelName() + ".size()>0 \">");
            //condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "  and " + column.getColumnName() + " in");
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "  and id in");
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<foreach item=\"item\" index=\"index\" collection=\"" + column.getModelName() + "\" open=\"(\" separator=\",\" close=\")\">");
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "  #{item} ");
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</foreach>");
            condition.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</if>");
            continue;
        }

        sb.append(condition);
        sb.append(RN_SPACE + "  </where >");
        sb.append(RN_SPACE + "</sql>" + RN);
    }

    public static void select(StringBuilder sb,List<Column> columnList) {
        sb.append(RN_SPACE + "<select id=\"findByPrimaryKey\" resultMap=\"" + resultMap + "\" parameterType=\"" + beanNameParamDtoUrl + "\" >");
        sb.append(RN_SPACE + DOUBLE_SPACE + "SELECT");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"do_columns\" />");
        sb.append(RN_SPACE + DOUBLE_SPACE + "FROM " + tableName + " WHERE id = #{id} ");
        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "delflag":
                    sb.append("and delflag=0");
                case "del_flag":
                    sb.append("and del_flag=0");
                    break;
                default:
            }
        }
        sb.append(RN_SPACE + "</select>" + RN_DOUBLE);
    }

    public static void selectOne(StringBuilder sb) {
        sb.append(RN_SPACE + "<select id=\"findByParamDto\" resultMap=\"" + resultMap + "\" parameterType=\"" + beanNameParamDtoUrl + "\" >");
        sb.append(RN_SPACE + DOUBLE_SPACE + "SELECT");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"do_columns\" />");
        sb.append(RN_SPACE + DOUBLE_SPACE + "FROM " + tableName);
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"doQueryParams\"/>");
        sb.append(RN_SPACE + "</select>" + RN_DOUBLE);
    }

    public static void listByExample(StringBuilder sb) {
        sb.append(RN_SPACE + "<select id=\"listByExample\" resultMap=\"" + resultMap + "\" parameterType=\"" + beanNameParamDtoUrl + "\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "SELECT");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"do_columns\" />");
        sb.append(RN_SPACE + DOUBLE_SPACE + "FROM " + tableName);
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"doQueryParams\"/>");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<choose>");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<when test=\"sidx != null and sidx.trim() != ''\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "order by ${sidx} ${sord}");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</when>");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<when test=\"sidxs != null and sidxs.trim() != ''\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "order by ${sidxs}");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</when>");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<otherwise>");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "order by id desc");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</otherwise>");
        sb.append(RN_SPACE + DOUBLE_SPACE + "</choose>");
        sb.append(RN_SPACE + "</select>" + RN_DOUBLE);
    }

    public static void countListByExample(StringBuilder sb) {
        sb.append(RN_SPACE + "<select id=\"countListByExample\" resultType=\"long\" parameterType=\"" + beanNameParamDtoUrl + "\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "SELECT");
        sb.append(RN_SPACE + DOUBLE_SPACE + "count(1)");
        sb.append(RN_SPACE + DOUBLE_SPACE + "FROM " + tableName);
        sb.append(RN_SPACE + DOUBLE_SPACE + "<include refid=\"doQueryParams\"/>");
        sb.append(RN_SPACE + "</select>" + RN_DOUBLE);
    }


}
