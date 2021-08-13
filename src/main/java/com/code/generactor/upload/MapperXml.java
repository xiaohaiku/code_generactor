package com.code.generactor.upload;

import com.code.generactor.model.Column;
import com.code.generactor.util.FileUtil;

import java.util.List;

/**
 * @author xiaoHai
 */
public class MapperXml extends CommonUtil {
    /**
     * 生成mapper.xml文件
     *
     * @param columnList
     */
    public static void uploadXmlFile(List<Column> columnList) {
        sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
                .append(RN)
                .append("<!DOCTYPE mapper PUBLIC \"-//ibatis.apache.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >")
                .append(RN)
                .append(String.format("<mapper namespace=\"%s\" >", mapperPackage + "." + mapperName))
                .append(RN)
                .append(RN_SPACE);


        insert(sb, columnList);

        insertBatch(sb, columnList);

        update(sb, columnList);

        delete(sb);

        sb.append(RN);
        sb.append("</mapper>");

        FileUtil.outputReplace(sb, mapperName, ".xml", mapperXmlFolderUrl);
    }

    public static void insert(StringBuilder sb, List<Column> columnList) {
        sb.append(RN_SPACE + "<insert id=\"insert\" parameterType=\"" + beanUrl + "\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "insert into " + tableName + " (");
        sb.append(RN_SPACE + DOUBLE_SPACE);
        StringBuilder columns = new StringBuilder();
        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "id":
                case "create_date":
                case "update_date":
                case "delflag":
                case "del_flag":
                    break;
                default:
                    columns.append(column.getColumnName() + ",");
            }
        }
        sb.append(columns.substring(0, columns.length() - 1));
        sb.append(RN_SPACE + DOUBLE_SPACE + ")values (");
        sb.append(RN_SPACE + DOUBLE_SPACE);
        StringBuilder modelNames = new StringBuilder();
        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "id":
                case "create_date":
                case "update_date":
                case "delflag":
                case "del_flag":
                    break;
                case "updateby":
                case "update_by":
                    modelNames.append("#{createBy" + ",jdbcType=" + column.getColumnType() + "},");
                    break;
                default:
                    modelNames.append("#{" + column.getModelName() + ",jdbcType=" + column.getColumnType() + "},");
            }
        }
        sb.append(modelNames.substring(0, modelNames.length() - 1));
        sb.append(RN_SPACE + DOUBLE_SPACE + ")");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<selectKey keyProperty=\"id\" order=\"AFTER\" resultType=\"java.lang.Integer\" >");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "/*FORCE_MASTER*/");
        sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "SELECT LAST_INSERT_ID() AS value");
        sb.append(RN_SPACE + DOUBLE_SPACE + "</selectKey>");
        sb.append(RN_SPACE + "</insert>");
        sb.append(RN_DOUBLE);
    }

    public static void insertBatch(StringBuilder sb, List<Column> columnList) {
        sb.append(RN_SPACE + "<insert id=\"insertBatch\" parameterType=\"" + beanUrl + "\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "insert into " + tableName + " (");
        sb.append(RN_SPACE + DOUBLE_SPACE);
        StringBuilder columns = new StringBuilder();
        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "id":
                case "create_date":
                case "update_date":
                case "delflag":
                case "del_flag":
                    break;
                default:
                    columns.append(column.getColumnName() + ",");
            }
        }
        sb.append(columns.substring(0, columns.length() - 1));
        sb.append(RN_SPACE + DOUBLE_SPACE + ")values ");
        sb.append(RN_SPACE + DOUBLE_SPACE + "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "    (");
        sb.append(RN_SPACE + DOUBLE_SPACE);
        StringBuilder modelNames = new StringBuilder();
        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "id":
                case "create_date":
                case "update_date":
                case "delflag":
                case "del_flag":
                    break;
                case "updateby":
                case "update_by":
                    modelNames.append("#{item.createBy" + ",jdbcType=" + column.getColumnType() + "},");
                    break;
                default:
                    modelNames.append("#{item." + column.getModelName() + ",jdbcType=" + column.getColumnType() + "},");
            }
        }
        sb.append(modelNames.substring(0, modelNames.length() - 1));
        sb.append(RN_SPACE + DOUBLE_SPACE + "    )");
        sb.append(RN_SPACE + DOUBLE_SPACE + "</foreach>");
        sb.append(RN_SPACE + "</insert>");
        sb.append(RN_DOUBLE);
    }

    public static void update(StringBuilder sb, List<Column> columnList) {
        sb.append(RN_SPACE + "<update id=\"update\" parameterType=\"" + beanUrl + "\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "update " + tableName);
        sb.append(RN_SPACE + DOUBLE_SPACE + "<set>");

        for (Column column : columnList) {
            switch (column.getColumnName()) {
                case "createby":
                case "create_by":
                case "create_date":
                case "update_date":
                    break;
                default:
                    sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "<if test=\"" + column.getModelName() + "!= null\">");
                    sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "  " + column.getColumnName() + " = #{" + column.getModelName() + ",jdbcType=" + column.getColumnType() + "},");
                    sb.append(RN_SPACE + DOUBLE_SPACE + DOUBLE_SPACE + "</if>");
            }
        }
        sb.append(RN_SPACE + DOUBLE_SPACE + "</set>");
        sb.append(RN_SPACE + DOUBLE_SPACE + "where id = #{id,jdbcType=INTEGER}");
        sb.append(RN_SPACE + "</update>" + RN_DOUBLE);
    }

    public static void delete(StringBuilder sb) {
        sb.append(RN_SPACE + "<delete id=\"delete\" parameterType=\"java.lang.Integer\">");
        sb.append(RN_SPACE + DOUBLE_SPACE + "delete from " + tableName);
        sb.append(RN_SPACE + DOUBLE_SPACE + "where id = #{id,jdbcType=INTEGER}");
        sb.append(RN_SPACE + "</delete>" + RN_DOUBLE);
    }


}
