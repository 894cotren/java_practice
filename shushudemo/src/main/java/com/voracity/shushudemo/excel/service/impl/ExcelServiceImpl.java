package com.voracity.shushudemo.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.voracity.shushudemo.excel.ExcelModel;
import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.voracity.shushudemo.excel.service.ExcelReadListener;
import com.voracity.shushudemo.excel.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public void importExcelModel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            // 读取 Excel
            EasyExcel.read(inputStream, PhoneNumbers.class, new ExcelReadListener())
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("excel导入失败");
        }

    }
}
