package com.voracity.shushudemo.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * PhoneNumbers导出DTO
 * 用于Excel导出时的数据格式定义
 */
@Data
public class PhoneNumbersExportDTO {
    
    @ExcelProperty("姓名")
    private String name;
    
    @ExcelProperty("电话号码")
    private String phoneNumber;
    
    @ExcelProperty("状态")
    private String status;
    
    @ExcelProperty("邮箱")
    private String email;
    
    @ExcelProperty("城市")
    private String city;
    
    @ExcelProperty("创建时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    
    /**
     * 从PhoneNumbers实体转换为导出DTO
     */
    public static PhoneNumbersExportDTO fromEntity(PhoneNumbers phoneNumbers) {
        PhoneNumbersExportDTO dto = new PhoneNumbersExportDTO();
        dto.setName(phoneNumbers.getName());
        dto.setPhoneNumber(phoneNumbers.getPhoneNumber());
        dto.setStatus(phoneNumbers.getStatus());
        dto.setEmail(phoneNumbers.getEmail());
        dto.setCity(phoneNumbers.getCity());
        dto.setCreatedAt(phoneNumbers.getCreatedAt());
        return dto;
    }
}
