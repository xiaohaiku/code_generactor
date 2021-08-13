package com.code.generactor.upload;

import com.code.generactor.model.Column;
import com.code.generactor.util.TableUtils;

import java.util.List;

/**
 * 生成文件的主方法
 *
 * @author xiaohai
 */
public class UploadFile {

    public static void upload() {
        List<Column> columnList = TableUtils.getColumnsInfo();
        if (columnList.size() == 0) {
            System.out.println("表信息不存在，请确认后再生成！");
            return;
        }

        BeanDto.uploadBean(columnList);
        BeanDto.uploadBeanParamDto(columnList);

        Mapper.uploadMapper();
        MapperSearch.uploadMapper();

        MapperXml.uploadXmlFile(columnList);
        MapperSearchXml.uploadXmlFile(columnList);

        //Service.beanService();
        //ServiceSearch.beanService();

        ServiceInterface.beanService();
        ServiceImpl.beanService();
        ServiceSearchInterface.beanService();
        ServiceSearchImpl.beanService();

    }

}
