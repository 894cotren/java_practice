package com.voracity.shushudemo.mapper;

import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author voracityrat
* @description 针对表【phone_numbers】的数据库操作Mapper
* @createDate 2025-09-07 16:20:04
* @Entity com.voracity.shushudemo.excel.model.PhoneNumbers
*/
public interface PhoneNumbersMapper extends BaseMapper<PhoneNumbers> {

    /**
     * 自定义批量插入电话号码数据
     * 使用MySQL的VALUES语法进行批量插入，性能比MyBatis-Plus的saveBatch更好
     * @param phoneNumbersList 数据列表
     * @return 插入成功的记录数
     */
    @Insert("<script>" +
            "INSERT INTO phone_numbers (name, phone_number, status, email, city, created_at) VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.name}, #{item.phoneNumber}, #{item.status}, #{item.email}, #{item.city}, #{item.createdAt})" +
            "</foreach>" +
            "</script>")
    int batchInsertCustom(@Param("list") List<PhoneNumbers> phoneNumbersList);
}




