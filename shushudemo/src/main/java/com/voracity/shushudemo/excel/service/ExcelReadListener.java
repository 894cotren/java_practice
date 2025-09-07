package com.voracity.shushudemo.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.voracity.shushudemo.excel.model.PhoneNumbersImportDTO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel读取监听器
 * 用于处理Excel导入时的数据验证和批量处理
 */
@Slf4j
public class ExcelReadListener extends AnalysisEventListener<PhoneNumbersImportDTO> {

    private final PhoneNumbersService phoneNumbersService;

    /**
     * 构造函数注入PhoneNumbersService
     */
    public ExcelReadListener(PhoneNumbersService phoneNumbersService) {
        this.phoneNumbersService = phoneNumbersService;
    }

    /**
     * 批量插入的数组大小
     */
    private final int INSERT_BATCH_SIZE = 50;

    /**
     * 批量插入用数组
     */
    List<PhoneNumbers> dataList = new ArrayList<PhoneNumbers>(INSERT_BATCH_SIZE);
    
    /**
     * 错误数据列表
     */
    List<String> errorList = new ArrayList<>();
    
    /**
     * 成功处理的数据条数
     */
    private int successCount = 0;
    
    /**
     * 错误数据条数
     */
    private int errorCount = 0;

    @Override
    public void invoke(PhoneNumbersImportDTO importDTO, AnalysisContext analysisContext) {
        try {
//            // 数据清理
//            importDTO.cleanData();
//            // 数据验证，下面这个是校验逻辑，我们暂时不用校验，我们知道可以这么做就好。
//            String validationError = importDTO.validate();
//            if (validationError != null) {
//                int rowIndex = analysisContext.readRowHolder().getRowIndex() + 1;
//                String errorMsg = String.format("第%d行数据验证失败: %s", rowIndex, validationError);
//                errorList.add(errorMsg);
//                errorCount++;
//                log.warn(errorMsg);
//                return;
//            }
            // 将接收参数类对象 转换为实体对象方便后面插入数据库 （我们用的一个DTO对着接参的）
            PhoneNumbers phoneNumbers = importDTO.toEntity();
            dataList.add(phoneNumbers);
            successCount++;
            // 批量处理
            if (dataList.size() >= INSERT_BATCH_SIZE) {
                batchInsert();
            }
        } catch (Exception e) {
            //关于数据异常处理。
            int rowIndex = analysisContext.readRowHolder().getRowIndex() + 1;
            String errorMsg = String.format("第%d行数据处理异常: %s", rowIndex, e.getMessage());
            errorList.add(errorMsg);
            errorCount++;
            log.error(errorMsg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 处理剩余数据
        if (!dataList.isEmpty()) {
            batchInsert();
        }
        // 输出处理结果
        log.info("Excel导入完成 - 成功: {}, 失败: {}", successCount, errorCount);
        if (!errorList.isEmpty()) {
            log.warn("导入错误详情:");
            errorList.forEach(log::warn);
        }
    }
    
    /**
     * 批量插入数据
     * 这里可以根据实际需求调用相应的Service方法
     */
    private void batchInsert() {
        if (dataList.isEmpty()) {
            return;
        }
        try {
            // TODO: 这里应该调用PhoneNumbersService进行批量插入
             phoneNumbersService.saveBatch(dataList);
            log.info("批量插入 {} 条数据", dataList.size());
            // 清理内存
            dataList.clear();
        } catch (Exception e) {
            log.error("批量插入数据失败", e);
            // 清理内存
            dataList.clear();
        }
    }
    
    /**
     * 获取处理结果统计
     */
    public String getProcessResult() {
        return String.format("导入完成 - 成功: %d条, 失败: %d条", successCount, errorCount);
    }
    
    /**
     * 获取错误列表
     */
    public List<String> getErrorList() {
        return new ArrayList<>(errorList);
    }
}