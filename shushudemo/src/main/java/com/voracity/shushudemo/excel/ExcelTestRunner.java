package com.voracity.shushudemo.excel;

import com.alibaba.excel.EasyExcel;
import com.voracity.shushudemo.excel.model.PhoneNumbersExportDTO;
import com.voracity.shushudemo.excel.service.impl.ExcelServiceImpl;

import java.util.List;

/**
 * Excel功能测试类
 * 用于生成测试数据并保存到本地文件
 */
public class ExcelTestRunner {
    
    public static void main(String[] args) {
        ExcelServiceImpl excelService = new ExcelServiceImpl();
        
        // 生成100条测试数据
        List<PhoneNumbersExportDTO> dataList = excelService.generateTestData(100);
        
        // 保存到本地文件
        String fileName = "src/main/resources/phone_numbers_test_data.xlsx";
        EasyExcel.write(fileName, PhoneNumbersExportDTO.class)
                .sheet("电话号码测试数据")
                .doWrite(dataList);
        
        System.out.println("测试数据生成成功！");
        System.out.println("文件路径: " + fileName);
        System.out.println("数据条数: " + dataList.size());
        
        // 打印前5条数据作为示例
        System.out.println("\n前5条数据示例:");
        for (int i = 0; i < Math.min(5, dataList.size()); i++) {
            PhoneNumbersExportDTO data = dataList.get(i);
            System.out.println((i + 1) + ". " + data.getName() + " - " + data.getPhoneNumber() + 
                             " - " + data.getStatus() + " - " + data.getCity());
        }
    }
}


