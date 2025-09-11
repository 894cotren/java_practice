package com.voracity.shushudemo.excel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.voracity.shushudemo.excel.service.PhoneNumbersService;
import com.voracity.shushudemo.mapper.PhoneNumbersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author voracityrat
* @description 针对表【phone_numbers】的数据库操作Service实现
* @createDate 2025-09-07 16:20:04
*/
@Slf4j
@Service
public class PhoneNumbersServiceImpl extends ServiceImpl<PhoneNumbersMapper, PhoneNumbers>
    implements PhoneNumbersService{

    @Override
    public boolean saveBatchCustom(List<PhoneNumbers> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return true;
        }
        
        try {
            // 直接执行批量插入，不需要再次分批（调用方已经分批了）
            int result = baseMapper.batchInsertCustom(entityList);
            log.info("批量插入 {} 条数据，实际插入 {} 条", entityList.size(), result);
            return result > 0;
        } catch (Exception e) {
            log.error("自定义批量插入失败", e);
            return false;
        }
    }

    @Override
    public List<PhoneNumbers> selectByCursorPaging(int lastId, int pageSize) {
        try {
            return baseMapper.selectByCursorPaging(lastId, pageSize);
        } catch (Exception e) {
            log.error("游标分页查询失败，lastId: {}, pageSize: {}", lastId, pageSize, e);
            return new ArrayList<>();
        }
    }
}




