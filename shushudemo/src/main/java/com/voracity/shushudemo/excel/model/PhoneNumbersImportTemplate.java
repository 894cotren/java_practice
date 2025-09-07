package com.voracity.shushudemo.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.enums.BooleanEnum;
import lombok.Data;

/**
 * PhoneNumbers导入模板
 * 用于生成标准的导入模板文件
 */
@Data
@HeadStyle(fillForegroundColor = 40) // 设置表头背景色
@HeadFontStyle(fontHeightInPoints = 12, bold = BooleanEnum.TRUE) // 设置表头字体
public class PhoneNumbersImportTemplate {
    
    @ExcelProperty(value = "姓名*", index = 0)
    @ColumnWidth(15)
    private String name;
    
    @ExcelProperty(value = "电话号码*", index = 1)
    @ColumnWidth(20)
    private String phoneNumber;
    
    @ExcelProperty(value = "状态*", index = 2)
    @ColumnWidth(15)
    private String status;
    
    @ExcelProperty(value = "邮箱", index = 3)
    @ColumnWidth(25)
    private String email;
    
    @ExcelProperty(value = "城市", index = 4)
    @ColumnWidth(15)
    private String city;
    
    @ExcelProperty(value = "创建时间", index = 5)
    @ColumnWidth(20)
    private String createdAt;
    
    /**
     * 创建示例数据
     */
    public static PhoneNumbersImportTemplate createSample() {
        PhoneNumbersImportTemplate template = new PhoneNumbersImportTemplate();
        template.setName("张三");
        template.setPhoneNumber("13800138000");
        template.setStatus("正常");
        template.setEmail("zhangsan@example.com");
        template.setCity("北京");
        template.setCreatedAt("2024-01-01 10:00:00");
        return template;
    }
}
