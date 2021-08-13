package com.code.generactor.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoHai
 */
public class FileUtil extends ConfigUtil {

    public static void output(StringBuilder sb,String fileName,String format) {
        System.out.println();
        System.out.println(String.format("开始导出%s%s文件", fileName,format));
        try {
            File file = new File(fileName + format);
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            System.out.println(String.format("%s文件导出成功", fileName));

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void outputB(StringBuilder sb,String fileName,String format) {
        System.out.println();
        System.out.println(String.format("开始导出%s%s文件", fileName,format));
        try {
            File file = new File(path + "/"+ tableName);
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(path + "/" + tableName + "/" + fileName + format);
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            System.out.println(String.format("%s文件导出成功", fileName));

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void output(StringBuilder sb,String fileName,String format,String fileUrl) {
        System.out.println();
        System.out.printf("开始导出%s%s文件%n", fileName,format);
        try {
            File file = null;
            if(BaseUtil.isEmpty(fileUrl)){
                file = new File(path + "/"+ tableName);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }else {
                file = new File(fileUrl +"/"+ fileName + format);
                if (file.exists()){
                    System.out.printf("%s文件已存在", fileName);
                    return;
                }
            }

            OutputStream out = null;
            if(BaseUtil.isEmpty(fileUrl)){
                out = new FileOutputStream(path + "/" + tableName + "/" + fileName + format);
            }else {
                out = new FileOutputStream(file);
            }
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            System.out.printf("%s文件导出成功%n", fileName);
        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void outputReplace(StringBuilder sb,String fileName,String format,String fileUrl) {
        System.out.println();
        System.out.printf("开始导出%s%s文件%n", fileName,format);
        try {
            File file = null;
            if(BaseUtil.isEmpty(fileUrl)){
                file = new File(path + "/"+ tableName);
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(path + "/" + tableName + "/" + fileName + format);
            }else {
                file = new File(fileUrl +"/"+ fileName + format);
            }

            if (file.exists()) {
                String fileContent = readFileContent(file);
                if(fileName.endsWith("SearchMapper")){
                    fileContent = MapperSearchXmlModify.getNewContent(sb, fileContent);
                }else if(fileName.endsWith("Mapper")){
                    fileContent = MapperXmlModify.getNewContent(sb, fileContent);
                }else {
                    fileContent = BeanModify.getNewContent(sb, fileContent);
                }
                sb = new StringBuilder();
                sb.append(fileContent);
            }
            OutputStream out = null;
            if(BaseUtil.isEmpty(fileUrl)){
                out = new FileOutputStream(path + "/" + tableName + "/" + fileName + format);
            }else {
                out = new FileOutputStream(file);
            }
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            if (file.exists()) {
                System.out.printf("%s文件修改成功%n", fileName);
            }else {
                System.out.printf("%s文件导出成功%n", fileName);
            }

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void outputMapperhXml(StringBuilder sb,String fileName,String format,String fileUrl) {
        System.out.println();
        System.out.printf("开始导出%s%s文件%n", fileName,format);
        try {
            File file = null;
            if(BaseUtil.isEmpty(fileUrl)){
                file = new File(path + "/"+ tableName);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }else {
                file = new File(fileUrl +"/"+ fileName + format);
                file = new File(path + "/" + tableName + "/" + fileName + format);
            }

            OutputStream out = null;
            if(BaseUtil.isEmpty(fileUrl)){
                out = new FileOutputStream(path + "/" + tableName + "/" + fileName + format);
            }else {
                out = new FileOutputStream(file);
            }
            PrintStream p = new PrintStream(out);

            if (file.exists()) {
                String fileContent = readFileContent(file);
                fileContent = MapperXmlModify.getNewContent(sb, fileContent);
                p.print(fileContent);
                System.out.printf("%s文件修改成功%n", fileName);
            }else {
                p.print(sb.toString());
                System.out.printf("%s文件导出成功%n", fileName);
            }
            p.close();
            out.close();

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void outputA(StringBuilder sb,String fileName,String format) {
        System.out.println();
        System.out.println(String.format("开始导出%s%s文件", fileName,format));
        try {
            File file = new File(path + tableName+"/impl/");
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(path + tableName + "/impl/" + fileName + format);
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            System.out.println(String.format("%s文件导出成功", fileName));

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void outputImpl(StringBuilder sb,String fileName,String format) {
        System.out.println();
        System.out.println(String.format("开始导出%s%s文件", fileName,format));
        try {
            File file = new File(path + tableName+"/impl");
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(path + tableName + "/impl/" + fileName + format);
            PrintStream p = new PrintStream(out);
            p.print(sb.toString());
            p.close();
            out.close();
            System.out.println(String.format("%s文件导出成功", fileName));

        } catch (Exception e) {
            System.out.println(String.format("%s文件导出异常", fileName) + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     * @param
     */
    public static String read(String fileUrl) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileUrl));
            String str ;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }

        } catch (IOException e) {
        }
        return sb.toString();
    }

    public static String readFileContent(File file) {
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                if(sbf.length()>0){
                    sbf.append("\r\n");
                }
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static void main(String[] args) {
        String url = "d:\\idea\\tool";
        List<File> moduleList = getModuleList(url);

        for (File file : moduleList) {
            if("tool-mbg".equals(file.getName())){
                File list1 = getList1(file, "com\\sea\\tool\\mbg\\dao");
                System.out.println(list1.getPath());
            }
        }

        //getList("d:\\idea\\tool\\tool-mbg");


        System.out.println("11");
    }

    /**
     * 获取项目的module
     * @param projectUrl 项目根目录地址
     * @return
     */
    public static List<File> getModuleList(String projectUrl){
        List<File> moduleList = new ArrayList<>();
        File[] ls = cn.hutool.core.io.FileUtil.ls(projectUrl);
        for (File file : ls) {
            if(file.isDirectory()){
                if(".idea".equals(file.getName())){
                    continue;
                }
                moduleList.add(file);
            }
        }
        return moduleList;
    }

    public static void getList(String patha){
        String path=patha;
        File file=new File(path);
        File[] tempList = file.listFiles();

        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {

                System.out.println("文     件："+tempList[i]);
            }
            if (tempList[i].isDirectory()) {
                System.out.println("文件夹："+tempList[i].getPath());

                //递归：
                getList(tempList[i].getPath());
            }
        }
    }

    public static File getList1(File file,String pageName){
        File[] tempList = file.listFiles();
        File resultFile = null;

        for (int i = 0; i < tempList.length; i++) {
            if(resultFile!=null){
                return resultFile;
            }
            File file1 = tempList[i];

            if (file1.isDirectory()) {
                if(file.getName().contains(".")||file.getName().equals("test")){
                    continue;
                }

                if(file1.getPath().endsWith(pageName)){
                     resultFile = file1;
                     break;
                }

               resultFile = getList1(file1,pageName);
            }
        }

        return resultFile;
    }



}
