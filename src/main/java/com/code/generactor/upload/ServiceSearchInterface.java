package com.code.generactor.upload;

import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

/**
 * @author xiaoHai
 */
public class ServiceSearchInterface extends CommonUtil {

    /**
     * 生成service文件
     */
    public static void beanService() {
        sb = new StringBuilder();

        String serviceName = tableToBeanName + "SearchService";
        StringBuilder sb = new StringBuilder("");

        //import
        sb.append("package ").append(servicePackage).append(";").append(RN_DOUBLE);
/*        sb.append("import com.alibaba.fastjson.JSON;").append(RN);
        sb.append("import " + mapperPackage + "." + mapperSearchName + ";").append(RN);
        sb.append("import " + beanUrl + ";").append(RN);
        sb.append("import " + beanNameParamDtoUrl + ";").append(RN);
        sb.append("import org.apache.ibatis.session.RowBounds;").append(RN);
        sb.append("import org.springframework.stereotype.Service;").append(RN);
        sb.append(RN);
        sb.append("import javax.annotation.Resource;").append(RN);
        sb.append("import java.util.List;").append(RN);*/

        //类注解
        getClassDescription(serviceName);

        //主体程序
        sb.append("public interface ").append(serviceName);
        if (!BaseUtil.isEmpty(superService)) {
            sb.append(" extends " + superService);
        }
        sb.append(" {")
                .append(RN);


        sb.append(RN_SPACE);

        findByPrimaryKey(sb);

        findByParamDto(sb);

        queryPage(sb);

        queryList(sb);

        sb.append(RN_DOUBLE + "}");
        FileUtil.output(sb, serviceName, ".java",serviceFolderUrl);
    }


    public static void findByPrimaryKey(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 根据主键查询" + tableComment).append(RN_SPACE)
                .append(" * @param id").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public " + beanName + " get" + beanName + "(Integer id);");

    }

    public static void findByParamDto(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 查询单个对象" + tableComment).append(RN_SPACE)
                .append(" * @param param").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public " + beanName + " getByParamDto(" + beanNameParamDto + " param);");

    }

    public static void queryPage(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 标准分页查询").append(RN_SPACE)
                .append(" * @param param").append(RN_SPACE)
                .append(" * @param start").append(RN_SPACE)
                .append(" * @param pageSize").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public SearchResult<" + beanName + "> query" + beanName + "Page(" + beanNameParamDto + " param, int start, int pageSize);");

    }

    public static void queryList(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 标准查询").append(RN_SPACE)
                .append(" * @param param").append(RN_SPACE)
                .append(" * @param start").append(RN_SPACE)
                .append(" * @param pageSize").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public List<" + beanName + "> query" + beanName + "List(" + beanNameParamDto + " param, int start, int pageSize);");

    }
}
