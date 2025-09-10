package com.voracity.shushudemo.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.util.Date;

/**
 * PhoneNumbers导入DTO
 * 用于Excel导入时的数据格式定义和验证
 */
@Data
public class PhoneNumbersImportDTO {
    
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
     * 转换为PhoneNumbers实体
     */
    public PhoneNumbers toEntity() {
        PhoneNumbers phoneNumbers = new PhoneNumbers();
        phoneNumbers.setName(this.name);
        phoneNumbers.setPhoneNumber(this.phoneNumber);
        phoneNumbers.setStatus(this.status);
        phoneNumbers.setEmail(this.email);
        phoneNumbers.setCity(this.city);
        phoneNumbers.setCreatedAt(this.createdAt != null ? this.createdAt : new Date());
        return phoneNumbers;
    }
    
    /**
     * 从PhoneNumbers实体创建导入DTO
     */
    public static PhoneNumbersImportDTO fromEntity(PhoneNumbers phoneNumbers) {
        PhoneNumbersImportDTO dto = new PhoneNumbersImportDTO();
        dto.setName(phoneNumbers.getName());
        dto.setPhoneNumber(phoneNumbers.getPhoneNumber());
        dto.setStatus(phoneNumbers.getStatus());
        dto.setEmail(phoneNumbers.getEmail());
        dto.setCity(phoneNumbers.getCity());
        dto.setCreatedAt(phoneNumbers.getCreatedAt());
        return dto;
    }
    
//    /**
//     * 数据验证方法
//     * @return 验证结果信息，null表示验证通过
//     */
//    public String validate() {
//        if (name == null || name.trim().isEmpty()) {
//            return "姓名不能为空";
//        }
//
//        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
//            return "电话号码不能为空";
//        }
//
//        if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
//            return "电话号码格式不正确，请输入11位有效手机号";
//        }
//
//        if (status == null || status.trim().isEmpty()) {
//            return "状态不能为空";
//        }
//
//        if (email != null && !email.trim().isEmpty() && !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//            return "邮箱格式不正确";
//        }
//
//        return null; // 验证通过
//    }
//
//    /**
//     * 数据清理方法
//     * 去除前后空格，处理空值等
//     */
//    public void cleanData() {
//        if (name != null) {
//            name = name.trim();
//        }
//        if (phoneNumber != null) {
//            phoneNumber = phoneNumber.trim();
//        }
//        if (status != null) {
//            status = status.trim();
//        }
//        if (email != null) {
//            email = email.trim();
//            if (email.isEmpty()) {
//                email = null;
//            }
//        }
//        if (city != null) {
//            city = city.trim();
//            if (city.isEmpty()) {
//                city = null;
//            }
//        }
//    }
}

