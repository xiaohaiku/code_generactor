package com.code.generactor.upload;

import com.code.generactor.util.BaseUtil;
import com.code.generactor.util.FileUtil;

/**
 * @author xiaoHai
 */
public class ServiceInterface extends CommonUtil {

    /**
     * 生成service文件
     */
    public static void beanService() {
        sb = new StringBuilder();

        String serviceName = tableToBeanName + "Service";

        //import
        sb.append("package ").append(servicePackage).append(";").append(RN_DOUBLE);
        /*sb.append("import ").append(mapperPackage).append(".").append(mapperName).append(";").append(RN);
        sb.append("import org.springframework.stereotype.Service;")
                .append(RN)
                .append("import javax.annotation.Resource;").append(RN_DOUBLE)
                .append("import ").append(beanUrl).append(";")
                .append(RN_DOUBLE)
                .append("import java.util.List;")
                .append(RN_DOUBLE);*/

        //类注解
        getClassDescription(serviceName);


        //主体程序
        sb.append("public interface ").append(serviceName);
        if (!BaseUtil.isEmpty(superService)) {
            sb.append(" extends " + superService);
        }
        sb.append("{")
                .append(RN);


        sb.append(RN_SPACE);

        add(sb);

        update(sb);

        sb.append(RN_DOUBLE + "}");

        FileUtil.output(sb, serviceName, ".java",serviceFolderUrl);
    }

    public static void add(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 添加" + tableComment).append(RN_SPACE)
                .append(" * @param dto").append(RN_SPACE)
                .append(" * @return").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public Integer add" + beanName + "(" + beanName + " dto);");

    }

    public static void update(StringBuilder sb) {
        sb.append(RN)
                .append(RN_SPACE)
                .append("/**").append(RN_SPACE)
                .append(" * 更新" + tableComment).append(RN_SPACE)
                .append(" * @param dto").append(RN_SPACE)
                .append(" */").append(RN_SPACE)
                .append("public void update" + beanName + "(" + beanName + " dto);");


    }

}
