package com.voracity.shushudemo.mapper;

import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 使用游标分页查询电话号码数据
     * 避免MySQL不支持LIMIT & IN子查询的问题
     * @param lastId 上一页最后一条记录的ID
     * @param pageSize 每页大小
     * @return 分页数据列表
     */
    @Select("SELECT id, name, phone_number, status, email, city, created_at " +
            "FROM phone_numbers " +
            "WHERE id > #{lastId} " +
            "ORDER BY id ASC " +
            "LIMIT #{pageSize}")
    List<PhoneNumbers> selectByCursorPaging(@Param("lastId") int lastId, @Param("pageSize") int pageSize);
}




