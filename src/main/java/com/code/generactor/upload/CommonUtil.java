package com.code.generactor.upload;

import com.code.generactor.util.ConfigUtil;

/**
 * @author xiaoHai
 * @className: Common
 * @description:
 * @createDate 2021/4/1 10:56
 */
public class CommonUtil extends ConfigUtil {

    public static StringBuilder sb = new StringBuilder();

    /**
     * 包名
     */
    /*public static void getPackage(String packageName) {
        sb.append("package ")
                .append(packageName)
                .append(";")
                .append(RN_DOUBLE);
    }*/

    /**
     * 类注解
     */
    public static void getClassDescription(String className) {
        sb.append(RN_DOUBLE);
        sb.append("/**").append(RN);
        sb.append(" * @author " + author).append(RN);
        sb.append(" * @className: " + className).append(RN);
        sb.append(" * @description: " + tableComment).append(RN);
        sb.append(" * @createDate " + createDate).append(RN);
        sb.append(" */").append(RN);
    }


}
