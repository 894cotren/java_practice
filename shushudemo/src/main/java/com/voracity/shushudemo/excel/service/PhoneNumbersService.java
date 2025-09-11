package com.voracity.shushudemo.excel.service;

import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author voracityrat
* @description 针对表【phone_numbers】的数据库操作Service
* @createDate 2025-09-07 16:20:04
*/
public interface PhoneNumbersService extends IService<PhoneNumbers> {

    /**
     * 自定义批量插入电话号码数据
     * 使用MySQL的VALUES语法进行批量插入，性能比MyBatis-Plus的saveBatch更好
     * @param entityList 数据列表
     * @return 是否插入成功
     */
    boolean saveBatchCustom(List<PhoneNumbers> entityList);

    /**
     * 使用游标分页查询电话号码数据
     * 避免MySQL不支持LIMIT & IN子查询的问题
     * @param lastId 上一页最后一条记录的ID
     * @param pageSize 每页大小
     * @return 分页数据列表
     */
    List<PhoneNumbers> selectByCursorPaging(int lastId, int pageSize);
}
