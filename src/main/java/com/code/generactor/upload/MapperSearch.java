package com.code.generactor.upload;

import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

/**
 * @author xiaoHai
 */
public class MapperSearch extends CommonUtil {
    /**
     * 导出mapper
     */
    public static void uploadMapper() {
        sb = new StringBuilder();

        sb.append("package ").append(mapperPackage).append(";").append(RN_DOUBLE);
        sb.append("import ").append(beanUrl).append(";").append(RN);
        sb.append("import ").append(beanNameParamDtoUrl).append(";").append(RN);
        sb.append("import org.apache.ibatis.session.RowBounds;").append(RN);
        sb.append("import org.springframework.dao.DataAccessException;").append(RN);
        sb.append(RN);
        sb.append("import java.util.List;");


        //类注解
        getClassDescription(mapperSearchName);

        //主体程序
        sb.append("public interface ")
                .append(mapperSearchName);
        if (!BaseUtil.isEmpty(superMapper)) {
            sb.append(" extends " + superMapper);
        }
        sb.append("{");
        sb.append(RN_SPACE);

        select(sb);

        selectOne(sb);

        listByExample(sb);

        countListByExample(sb);


        sb.append(RN_DOUBLE + "}");


        FileUtil.output(sb, mapperSearchName, ".java", mapperFolderUrl);
    }

    public static void select(StringBuilder sb) {
        sb.append(RN + RN_SPACE + beanName + " findByPrimaryKey(Integer id) throws DataAccessException;");
    }

    public static void selectOne(StringBuilder sb) {
        sb.append(RN + RN_SPACE + beanName + " findByParamDto(" + beanNameParamDto + " paramDto) throws DataAccessException;");
    }

    public static void listByExample(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "List<" + beanName + "> listByExample(" + beanNameParamDto + " paramDto, RowBounds rowBounds) throws DataAccessException;");
        sb.append(RN + RN_SPACE + "List<" + beanName + "> listByExample(" + beanNameParamDto + " paramDto) throws DataAccessException;");
    }

    public static void countListByExample(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "long countListByExample(" + beanNameParamDto + " paramDto) throws DataAccessException;");
    }


}
