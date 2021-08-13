package com.code.generactor.util;



import com.code.generactor.impl.DbConnectionImp;
import com.code.generactor.model.Column;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据库表信息获取
 *
 * @author taohu
 */
public class TableUtils extends ConfigUtil {

    public static Connection connection = DbConnectionImp.getInstance().getDBConnection();

    /**
     * 获取所有的表名信息
     *
     * @return
     */
    public static List<String> getTableList() {
        List<String> result = new LinkedList<>();
        if (connection == null) {
            System.out.println("数据库连接信息不正确,无法获取表名信息...");
            return result;
        }
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(DbConnectionImp.dbName, null, tableName, null);
            while (rs.next()) {
                result.add(rs.getString(3));
            }
        } catch (Exception e) {
            System.out.println("获取信息发生异常:" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取列的信息
     *
     * @return
     */
    public static List<Column> getColumnsInfo() {
        List<Column> columnList = new LinkedList<>();
        List<String> tableNames = getTableList();
        if (!tableNames.contains(tableName)) {
            System.out.println("表名不存在！");
            return columnList;
        }
        try {
            DatabaseMetaData data = connection.getMetaData();
            ResultSet resultSet = data.getColumns(DbConnectionImp.dbName, null, tableName, null);
            while (resultSet.next()) {
                Column persistent = new Column();
                String type = resultSet.getString("TYPE_NAME");
                persistent.setColumnName(resultSet.getString("COLUMN_NAME"));
                String sqlTypeToXml = BaseUtil.sqlTypeToXml(type);
                if("Object".equals(sqlTypeToXml)){
                    System.out.println(resultSet.getString("COLUMN_NAME")+"类型不匹配！");
                    return columnList;
                }
                persistent.setColumnType(sqlTypeToXml);
                persistent.setColumnDesc(resultSet.getString("REMARKS"));
                //默认第一项为主键
                if (columnList.size() == 0) {
                    persistent.setPrimary(Boolean.TRUE);
                    persistent.setColumnDesc("主键");
                }
                persistent.setModelName(BaseUtil.columnNameToJava(persistent.getColumnName()));
                persistent.setModelType(BaseUtil.sqlTypeToJava(type));
                switch (persistent.getColumnName()) {
                    case "createby":
                        persistent.setModelName("createBy");
                        break;
                    case "updateby":
                        persistent.setModelName("updateBy");
                        break;
                    case "delflag":
                        persistent.setModelName("delFlag");
                        break;
                    default:
                }

                columnList.add(persistent);
            }
        } catch (Exception e) {
            System.out.println(String.format("解析%s发生错误：", tableName) + e.getMessage());
            e.printStackTrace();
        }
        return columnList;
    }

    public static void main(String[] args) {

    }
}
