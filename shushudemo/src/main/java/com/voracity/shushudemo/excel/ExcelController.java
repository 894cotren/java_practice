package com.voracity.shushudemo.excel;


import com.voracity.shushudemo.excel.service.ExcelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;


    @PostMapping("/import")
    public void importGoodsProdMeta(@RequestPart("file") MultipartFile file){
        //调用导入服务
        excelService.importExcelModel(file);
        return;
    }

}
