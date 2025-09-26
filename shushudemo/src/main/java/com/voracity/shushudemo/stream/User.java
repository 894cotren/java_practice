package com.voracity.shushudemo.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

// 用户类
@Data
@AllArgsConstructor
class User {
    private Long id;          // 用户ID
    private String name;      // 姓名
    private Integer age;      // 年龄
    private String gender;    // 性别（男/女）
    private String city;      // 所在城市
    private BigDecimal salary;// 月薪


}
