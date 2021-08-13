package com.code.generactor.upload;

import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

/**
 * @author xiaoHai
 */
public class Mapper extends CommonUtil {

    /**
     * 导出mapper
     */
    public static void uploadMapper() {
        sb = new StringBuilder();

        sb.append("package ").append(mapperPackage).append(";").append(RN_DOUBLE);
        sb.append("import ").append(beanUrl).append(";").append(RN);
        sb.append("import org.apache.ibatis.annotations.Param;").append(RN);
        sb.append("import org.springframework.dao.DataAccessException;").append(RN);
        sb.append(RN);
        sb.append("import java.util.List;");

        //类注解
        getClassDescription(mapperName);

        //主体程序
        sb.append("public interface ").append(mapperName);
        if (!BaseUtil.isEmpty(superMapper)) {
            sb.append(" extends ").append(superMapper);
        }
        sb.append("{");
        sb.append(RN_SPACE);

        insert(sb);

        insertBatch(sb);

        delete(sb);

        update(sb);

        sb.append(RN_DOUBLE + "}");



        FileUtil.output(sb, mapperName, ".java", mapperFolderUrl);
    }

    public static void insert(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "Integer insert(").append(beanName).append(" ").append(beanNameToLower).append(") throws DataAccessException;");
    }

    public static void insertBatch(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "Integer insertBatch(@Param(\"list\") List<").append(beanName).append("> list) throws DataAccessException;");
    }

    public static void update(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "Integer update(").append(beanName).append(" ").append(beanNameToLower).append(") throws DataAccessException;");
    }

    public static void delete(StringBuilder sb) {
        sb.append(RN + RN_SPACE + "Integer delete(Integer id) throws DataAccessException;");
    }


}
