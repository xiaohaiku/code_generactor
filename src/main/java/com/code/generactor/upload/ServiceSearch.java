package com.code.generactor.upload;

import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

/**
 * @author xiaoHai
 */
public class ServiceSearch extends CommonUtil {

    /**
     * 生成service文件
     */
    public static void beanService() {
        sb = new StringBuilder();

        String serviceName = tableToBeanName + "SearchService";
        StringBuilder sb = new StringBuilder("");

        //import
        sb.append("package ").append(servicePackage).append(";").append(RN_DOUBLE);
        /*sb.append("import com.alibaba.fastjson.JSON;").append(RN);
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
        sb.append("@Service").append(RN);
        sb.append("public class ").append(serviceName);
        if (!BaseUtil.isEmpty(superService)) {
            sb.append(" extends " + superService);
        }
        sb.append(" {")
                .append(RN);


        sb.append(RN_SPACE)
                .append("@Resource")
                .append(RN_SPACE)
                .append("private " + mapperSearchName + " " + mapperSearchNameToLower + ";")
                .append(RN_SPACE);
        if (openCache) {
            sb.append("@Resource")
                    .append(RN_SPACE)
                    .append("private CacheUtil cacheUtil;");
        }
        ;

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
                .append("public " + beanName + " get" + beanName + "(Integer id) {");
        if (openCache) {
            sb.append(RN_SPACE + SPACE)
                    .append("String object = cacheUtil.get(Cache." + tableName + ", id + \"\");")
                    .append(RN_SPACE + SPACE)
                    .append("if (object == null) {")
                    .append(RN_SPACE + SPACE + SPACE)
                    .append(beanName + " " + beanNameToLower + " = " + mapperSearchNameToLower + ".findByPrimaryKey(id);")
                    .append(RN_SPACE + SPACE + SPACE)
                    .append("if (" + beanNameToLower + " == null) {")
                    .append(RN_SPACE + SPACE + SPACE)
                    .append("}")
                    .append(RN_SPACE + SPACE + SPACE)
                    .append("cacheUtil.set(Cache." + tableName + ", id + \"\", JSON.toJSONString(" + beanNameToLower + "));")
                    .append(RN_SPACE + SPACE + SPACE)
                    .append("return " + beanNameToLower + ";")
                    .append(RN_SPACE + SPACE)
                    .append("}")
                    .append(RN_SPACE + SPACE)
                    .append("return JSON.parseObject(object, " + beanName + ".class);");
        }


        sb.append(RN_SPACE + SPACE)
                .append("return " + mapperSearchNameToLower + ".findByPrimaryKey(id);")
                .append(RN_SPACE)
                .append("}");
    }

    public static void findByParamDto(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 查询单个对象" + tableComment).append(RN_SPACE)
                .append(" * @param param").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public " + beanName + " getByParamDto(" + beanNameParamDto + " param) {")
                .append(RN_SPACE + SPACE)
                .append("return " + mapperSearchNameToLower + ".findByParamDto(param);")
                .append(RN_SPACE)
                .append("}");
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
                .append("public SearchResult<" + beanName + "> query" + beanName + "Page(" + beanNameParamDto + " param, int start, int pageSize) {")
                .append(RN_SPACE + SPACE)
                .append("SearchResult<" + beanName + "> result = new SearchResult<>();")
                .append(RN_SPACE + SPACE)
                .append("long count = " + mapperSearchNameToLower + ".countListByExample(param);")
                .append(RN_SPACE + SPACE)
                .append("result.setTotal(count);")
                .append(RN_SPACE + SPACE)
                .append("if (count > 0) {")
                .append(RN_SPACE + SPACE + SPACE)
                .append("if (pageSize == 0) {")
                .append(RN_SPACE + SPACE + SPACE)
                .append("    pageSize = Integer.parseInt(count + \"\");")
                .append(RN_SPACE + SPACE + SPACE)
                .append("}")
                .append(RN_SPACE + SPACE + SPACE)
                .append("List<" + beanName + "> list = " + mapperSearchNameToLower + ".listByExample(param, new RowBounds(start, pageSize));")
                .append(RN_SPACE + SPACE + SPACE)
                .append("result.setList(list);")
                .append(RN_SPACE + SPACE)
                .append("}")
                .append(RN_SPACE + SPACE)
                .append("return result;")
                .append(RN_SPACE)
                .append("}");
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
                .append("public List<" + beanName + "> query" + beanName + "List(" + beanNameParamDto + " param, int start, int pageSize) {")
                .append(RN_SPACE + SPACE)
                .append("if (pageSize == 0) {")
                .append(RN_SPACE + SPACE + SPACE)
                .append("    return " + mapperSearchNameToLower + ".listByExample(param);")
                .append(RN_SPACE + SPACE)
                .append("}")
                .append(RN_SPACE + SPACE)
                .append("return " + mapperSearchNameToLower + ".listByExample(param, new RowBounds(start, pageSize));")
                .append(RN_SPACE)
                .append("}");
    }
}
