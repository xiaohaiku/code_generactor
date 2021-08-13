package com.code.generactor.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

public class ConfigUtil {

    /**
     * window  回车
     */
    protected static final String RN = "\r\n";
    protected static final String RN_DOUBLE = "\r\n\n";
    protected static final String SPACE = "    ";
    protected static final String DOUBLE_SPACE = "  ";
    protected static final String RN_SPACE = "\r\n    ";

    protected static String path = "";
    protected static String pathType = "1";//1.项目根目录，2.导出路径

    protected static String basePackage = "";
    protected static String modelPackage = "";
    protected static String modelFolderUrl = "";
    protected static String servicePackage = "";
    protected static String serviceImplPackage = "";
    protected static String serviceFolderUrl = "";
    protected static String serviceImplFolderUrl = "";
    protected static String controllerPackage = "";
    protected static String mapperPackage = "";
    protected static String mapperFolderUrl = "";
    protected static String mapperXmlFolderUrl = "";

    protected static String superModel = "";
    protected static String superController = "";
    protected static String superService = "";
    protected static String superMapper = "";

    protected static String ignoreColumns = "";

    /**
     * 表名
     */
    protected static String tableName = "";
    /**
     * 表名转beanName
     */
    protected static String tableToBeanName = "";
    /**
     * javaBean名称
     */
    protected static String beanName = "";
    /**
     * javaBean查询参数类名称
     */
    protected static String beanNameParamDto = "";
    /**
     * javaBean名称首字母小写
     */
    protected static String beanNameToLower = "";
    /**
     * javaBean地址
     */
    protected static String beanUrl = "";
    protected static String beanNameParamDtoUrl = "";
    /**
     * mapper文件名
     */
    protected static String mapperName = "";

    protected static String resultMap = "";
    /**
     * searchMapper文件名
     */
    protected static String mapperSearchName = "";
    /**
     * mapper文件名首字母小写
     */
    protected static String mapperNameToLower = "";
    /**
     * searchMapper文件名首字母小写
     */
    protected static String mapperSearchNameToLower = "";

    protected static String author = "";

    protected static String createDate = "";

    protected static String tableComment = "";


    protected static String moduleDto = "";
    protected static String moduleMapper = "";
    protected static String moduleService = "";


    /**
     * 开启缓存
     */
    protected static boolean openCache;

    protected static Properties properties = new Properties();


    public static void start(String config) {
        System.out.println("start...");

        try {
            InputStream inputStream = ConfigUtil.class.getResourceAsStream("/" + config + ".properties");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            properties.load(bf);
        } catch (Exception e) {
            System.out.println("config.properties文件加载失败！" + e.getMessage());
            e.printStackTrace();
        }

        path = properties.getProperty("project.path");//System.getProperty("user.dir")+"\\src\\com\\code\\generactor\\";
        if(BaseUtil.isEmpty(path)){
            path = properties.getProperty("base.path");
            pathType = "2";
        }
        author = properties.getProperty("author");
        tableComment = properties.getProperty("tableComment");
        basePackage = properties.getProperty("basepackage.name");
        if (!BaseUtil.isEmpty(basePackage)) {
            modelPackage = basePackage + "." + properties.getProperty("package.dto");
        } else {
            modelPackage = properties.getProperty("package.dto");
        }

        if (!BaseUtil.isEmpty(basePackage)) {
            servicePackage = basePackage + "." + properties.getProperty("package.service");
        } else {
            servicePackage = properties.getProperty("package.service");
        }
        serviceImplPackage = servicePackage +".impl";

        if (!BaseUtil.isEmpty(basePackage)) {
            controllerPackage = basePackage + "." + properties.getProperty("basepackage.controller");
        } else {
            controllerPackage = properties.getProperty("basepackage.controller");
        }

        if (!BaseUtil.isEmpty(basePackage)) {
            mapperPackage = basePackage + "." + properties.getProperty("package.mapper");
        } else {
            mapperPackage = properties.getProperty("package.mapper");
        }

        superModel = properties.getProperty("super.model");
        superController = properties.getProperty("super.controller");
        superService = properties.getProperty("super.service");
        superMapper = properties.getProperty("super.BaseMapper");

        ignoreColumns = properties.getProperty("ignore.bean.columns");


        tableName = properties.getProperty("table");
        tableToBeanName = BaseUtil.tableNameToJava(tableName);
        beanName = properties.getProperty("bean");
        beanNameToLower = BaseUtil.beanNameToLower(beanName);
        beanNameParamDto = beanName.substring(0, beanName.length() - 3) + "ParamDto";
        beanNameParamDtoUrl = modelPackage + "." + beanNameParamDto;
        beanUrl = modelPackage + "." + beanName;
        mapperName = BaseUtil.tableNameToJava(tableName) + "Mapper";
        mapperNameToLower = BaseUtil.beanNameToLower(mapperName);
        resultMap = BaseUtil.tableNameToJava(tableName) + "ResultMap";
        mapperSearchName = BaseUtil.tableNameToJava(tableName) + "SearchMapper";
        mapperSearchNameToLower = BaseUtil.beanNameToLower(mapperSearchName);

        moduleDto = properties.getProperty("module.dto");
        moduleMapper = properties.getProperty("module.mapper");
        moduleService = properties.getProperty("module.service");

        if(pathType.equals("1")){
            List<File> moduleList = FileUtil.getModuleList(path);
            for (File file : moduleList) {
                if(moduleService.equals(file.getName())){
                    File list1 = FileUtil.getList1(file, servicePackage.replaceAll("\\.",Matcher.quoteReplacement(File.separator)));
                    serviceFolderUrl = list1.getPath();
                    serviceImplFolderUrl = serviceFolderUrl+"/impl";
                    System.out.println(serviceFolderUrl);
                    System.out.println(serviceImplFolderUrl);
                }else if(moduleMapper.equals(file.getName())){
                    File list1 = FileUtil.getList1(file, mapperPackage.replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
                    mapperFolderUrl = list1.getPath();
                    System.out.println(mapperFolderUrl);
                    File list2 = FileUtil.getList1(file, properties.getProperty("package.mapperxml").replaceAll("\\.",Matcher.quoteReplacement(File.separator)));
                    mapperXmlFolderUrl = list2.getPath();
                    System.out.println(mapperXmlFolderUrl);
                }
                if(moduleDto.equals(file.getName())){
                    File list1 = FileUtil.getList1(file, modelPackage.replaceAll("\\.",Matcher.quoteReplacement(File.separator)));
                    modelFolderUrl = list1.getPath();
                    System.out.println(modelFolderUrl);
                }
            }
        }




        if ("true".equals(properties.getProperty("open.cache"))) {
            openCache = true;
        }


        createDate = DateUtil.format(new Date(), "yyyy/MM/dd HH:mm");
        System.out.println(createDate);
    }
}
