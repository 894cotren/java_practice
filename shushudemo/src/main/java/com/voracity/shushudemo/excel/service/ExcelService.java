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
    
    /**
     * 分页读写导出PhoneNumbers数据（内存友好）
     * @param response HTTP响应
     * @param count 导出数据条数
     */
    void exportPhoneNumbersPaged(HttpServletResponse response, int count);

    /**
     * 多线程分页查询 + 单线程写入导出
     * 使用多线程并发查询数据，单线程顺序写入Excel
     * @param response HTTP响应
     * @param count 导出数据量
     */
    void exportPhoneNumbersMultiThreadQuery(HttpServletResponse response, int count);

    /**
     * 多线程分页查询 + 单线程写入导出（使用submit方式）
     * 使用线程池submit方法提交任务，适合熟悉Future接口的开发者
     * @param response HTTP响应
     * @param count 导出数据量
     */
    void exportPhoneNumbersMultiThreadQueryWithSubmit(HttpServletResponse response, int count);

    /**
     * 优化的分批导出方法
     * 每页2万条数据，每批5次分页查询，使用子查询优化SQL
     * 避免内存溢出，提高导出效率
     * @param response HTTP响应
     * @param count 导出数据量
     */
    void exportPhoneNumbersOptimized(HttpServletResponse response, int count);
}
