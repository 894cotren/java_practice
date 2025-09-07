package com.voracity.shushudemo.excel.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.voracity.shushudemo.excel.model.PhoneNumbers;

import java.util.ArrayList;
import java.util.List;

public class ExcelReadListener extends AnalysisEventListener<PhoneNumbers> {

    /**
     * 批量插入的数组大小
     */
    private final int INSERT_BATCH_SIZE = 50;

    /**
     * 批量插入用数组
     */
    List<PhoneNumbers> dataList = new ArrayList<PhoneNumbers>(INSERT_BATCH_SIZE);



    @Override
    public void invoke(PhoneNumbers phoneNumbers, AnalysisContext analysisContext) {
        dataList.add(phoneNumbers);
        if (dataList.size()>=INSERT_BATCH_SIZE){
            //批量插入
        }
        //清理内存
        dataList.clear();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //批量插入
        //清理内存；
    }
}