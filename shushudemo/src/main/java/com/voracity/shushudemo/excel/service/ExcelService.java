package com.voracity.shushudemo.excel.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void importExcelModel(MultipartFile file);
}
