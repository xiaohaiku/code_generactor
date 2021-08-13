package com.code.generactor.upload;

import com.code.generactor.model.Column;
import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoHai
 */
public class BeanDto extends CommonUtil {

    /**
     * 生成java文件
     *
     * @param columnList
     */
    public static void uploadBean(List<Column> columnList) {
        //sss(columnList);
        sb = new StringBuilder();

        //判断有没有忽略的字段
        if (!BaseUtil.isEmpty(ignoreColumns)) {
            String[] ignoreList = ignoreColumns.split(",");
            for (int i = 0; i < columnList.size(); i++) {
                if (BaseUtil.hasItem(ignoreList, columnList.get(i).getColumnName())) {
                    columnList.remove(columnList.get(i));
                    i--;
                }
            }
        }

        //import
        sb.append("package ").append(modelPackage).append(";")
                .append(RN)
                .append("import lombok.Getter;").append(RN)
                .append("import lombok.Setter;").append(RN)
                .append("import java.util.Date;")
                .append(RN_DOUBLE);

        //类注解
        getClassDescription(beanName);

        sb.append("@Getter");
        sb.append(RN);
        sb.append("@Setter");
        sb.append(RN);
        sb.append("public class ").append(beanName);
        if (!BaseUtil.isEmpty(superModel)) {
            sb.append(" extends " + superModel);
        }
        sb.append(" implements Serializable");

        sb.append(" {").append(RN_DOUBLE);
        sb.append("     private static final long serialVersionUID = 1L;").append(RN_DOUBLE);



        //插入属性名称
        for (Column column : columnList) {
            sb.append(RN_SPACE)
                    .append("/**")
                    .append(RN_SPACE)
                    .append(" * ")
                    .append(column.getColumnDesc())
                    .append(RN_SPACE)
                    .append(" */")
                    .append(RN_SPACE)
                    .append("private ")
                    .append(column.getModelType())
                    .append(" ")
                    .append(column.getModelName())
                    .append(";")
                    .append(RN);
        }

        sb.append("}").append(RN);


        FileUtil.outputReplace(sb, beanName, ".java",modelFolderUrl);
    }

    public static void uploadBeanParamDto(List<Column> columnList) {
        sb = new StringBuilder();

        //判断有没有忽略的字段
        if (!BaseUtil.isEmpty(ignoreColumns)) {
            String[] ignoreList = ignoreColumns.split(",");
            for (int i = 0; i < columnList.size(); i++) {
                if (BaseUtil.hasItem(ignoreList, columnList.get(i).getColumnName())) {
                    columnList.remove(columnList.get(i));
                    i--;
                }
            }
        }

        //import
        sb.append("package ").append(modelPackage).append(";")
                .append(RN)
                .append("import lombok.Getter;").append(RN)
                .append("import lombok.Setter;").append(RN)
                .append("import java.util.Date;")
                .append(RN_DOUBLE);


        //类注解
        getClassDescription(beanName);

        sb.append("@Getter");
        sb.append(RN);
        sb.append("@Setter");
        sb.append(RN);
        sb.append("public class ").append(beanNameParamDto).append(" extends ").append(beanName);



        sb.append(" {").append(RN);


        List<Column> columnList1 = new ArrayList<>();
        Column a = new Column();
        a.setColumnDesc("排序字段");
        a.setModelType("String");
        a.setModelName("sidx");
        columnList1.add(a);
        Column b = new Column();
        b.setColumnDesc("排序方式");
        b.setModelType("String");
        b.setModelName("sord");
        columnList1.add(b);
        Column c = new Column();
        c.setColumnDesc("多个排序字段 eg: id desc,create_date desc");
        c.setModelType("String");
        c.setModelName("sidxs");
        columnList1.add(c);
        Column d = new Column();
        d.setColumnDesc("批量查询");
        d.setModelType("Integer");
        d.setModelName("ids");
        columnList1.add(d);

        //插入属性名称
//        for (Column column : columnList) {
//            sb.append(RN_SPACE)
//                    .append("/**")
//                    .append(RN_SPACE)
//                    .append(" * ")
//                    .append(column.getColumnDesc())
//                    .append(RN_SPACE)
//                    .append(" */")
//                    .append(RN_SPACE)
//                    .append("private ");
//            if ("ids".equals(column.getModelName())) {
//                sb.append("List<" + column.getModelType() + ">");
//            } else {
//                sb.append(column.getModelType());
//            }
//
//            sb.append(" ")
//                    .append(column.getModelName())
//                    .append(";")
//                    .append(RN);
//        }
        for (Column column : columnList1) {
            sb.append(RN_SPACE)
                    .append("/**")
                    .append(RN_SPACE)
                    .append(" * ")
                    .append(column.getColumnDesc())
                    .append(RN_SPACE)
                    .append(" */")
                    .append(RN_SPACE)
                    .append("private ");
            if ("ids".equals(column.getModelName())) {
                sb.append("List<" + column.getModelType() + ">");
            } else {
                sb.append(column.getModelType());
            }

            sb.append(" ")
                    .append(column.getModelName())
                    .append(";")
                    .append(RN);
        }

        sb.append("}").append(RN);


        FileUtil.output(sb, beanNameParamDto, ".java",modelFolderUrl);
    }


}
