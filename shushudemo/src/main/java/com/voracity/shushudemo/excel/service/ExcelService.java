package com.voracity.shushudemo.excel.service;

import com.voracity.shushudemo.excel.model.PhoneNumbersExportDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {
    void importExcelModel(MultipartFile file);
    
    /**
     * 导出PhoneNumbers数据到Excel
     * @param response HTTP响应
     * @param dataList 要导出的数据列表
     */
    void exportPhoneNumbers(HttpServletResponse response, List<PhoneNumbersExportDTO> dataList);
    
    /**
     * 生成测试数据
     * @param count 生成数据条数
     * @return 生成的测试数据列表
     */
    List<PhoneNumbersExportDTO> generateTestData(int count);
    
    /**
     * 下载导入模板
     * @param response HTTP响应
     */
    void downloadImportTemplate(HttpServletResponse response);
    
    /**
     * 多线程分页导出PhoneNumbers数据
     * @param response HTTP响应
     * @param count 导出数据条数
     */
    void exportPhoneNumbersMultiThread(HttpServletResponse response, int count);
}
