package com.voracity.shushudemo.excel;


import com.voracity.shushudemo.excel.model.PhoneNumbersExportDTO;
import com.voracity.shushudemo.excel.model.PhoneNumbersImportTemplate;
import com.voracity.shushudemo.excel.service.ExcelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;


    @PostMapping("/import")
    public void importGoodsProdMeta(@RequestPart("file") MultipartFile file){
        //调用导入服务
        excelService.importExcelModel(file);
    }

    /**
     * 导出PhoneNumbers数据（分页读写，内存友好）
     * @param response HTTP响应
     * @param count 导出数据条数，默认100万条
     */
    @GetMapping("/export")
    public void exportPhoneNumbers(HttpServletResponse response, 
                                  @RequestParam(defaultValue = "1000000") int count) {
        excelService.exportPhoneNumbersPaged(response, count);
    }

    /**
     * 导出PhoneNumbers数据到Excel
     * @param response HTTP响应
     * @param count 生成数据条数，默认100条
     */
    @GetMapping("/exportTextData")
    public void exportPhoneNumbersTextData(HttpServletResponse response,
                                  @RequestParam(defaultValue = "100") int count) {
        // 生成测试数据
        List<PhoneNumbersExportDTO> dataList = excelService.generateTestData(count);
        // 导出Excel
        excelService.exportPhoneNumbers(response, dataList);
    }

    /**
     * 生成测试数据（不导出，仅返回数据）
     * @param count 生成数据条数，默认100条
     * @return 生成的测试数据
     */
    @GetMapping("/generate")
    public List<PhoneNumbersExportDTO> generateTestData(@RequestParam(defaultValue = "100") int count) {
        return excelService.generateTestData(count);
    }

    /**
     * 下载导入模板
     * @param response HTTP响应
     */
    @GetMapping("/template")
    public void downloadImportTemplate(HttpServletResponse response) {
        excelService.downloadImportTemplate(response);
    }

}
