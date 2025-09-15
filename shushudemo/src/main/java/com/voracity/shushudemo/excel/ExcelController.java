package com.voracity.shushudemo.excel;


import com.voracity.shushudemo.excel.model.PhoneNumbersExportDTO;
import com.voracity.shushudemo.excel.model.PhoneNumbersImportTemplate;
import com.voracity.shushudemo.excel.service.ExcelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private ExcelService excelService;
    
    // 全局锁：保证同一张表的导入导出操作不能同时进行
    private final ReentrantLock globalLock = new ReentrantLock();


    @PostMapping("/import")
    public void importGoodsProdMeta(@RequestPart("file") MultipartFile file){
        // 尝试获取全局锁，如果获取不到说明有其他操作正在进行
        if (!globalLock.tryLock()) {
            System.err.println("系统繁忙，当前有其他导入或导出任务正在进行，请稍后重试");
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        
        try {
            System.out.println("=== Excel导入开始 ===");
            System.out.println("文件名: " + file.getOriginalFilename());
            System.out.println("文件大小: " + file.getSize() + " 字节 (" + (file.getSize() / 1024.0 / 1024.0) + " MB)");
            System.out.println("开始时间: " + System.currentTimeMillis());
            
            //调用导入服务
            excelService.importExcelModel(file);
            
            System.out.println("=== Excel导入完成 ===");
            System.out.println("完成时间: " + System.currentTimeMillis());
            
        } catch (Exception e) {
            System.err.println("=== Excel导入失败 ===");
            System.err.println("错误信息: " + e.getMessage());
            throw e;
        } finally {
            // 释放全局锁
            globalLock.unlock();
            System.out.println("全局锁已释放，系统可接受新的导入导出请求");
        }
    }

    /**
     * 导出PhoneNumbers数据（分页读写，内存友好）
     * @param response HTTP响应
     * @param count 导出数据条数，默认100万条
     */
    @GetMapping("/export")
    public void exportPhoneNumbers(HttpServletResponse response, 
                                  @RequestParam(defaultValue = "1000000") int count) {
        // 尝试获取全局锁，如果获取不到说明有其他操作正在进行
        if (!globalLock.tryLock()) {
            System.err.println("系统繁忙，当前有其他导入或导出任务正在进行，请稍后重试");
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        
        try {
            System.out.println("=== Excel导出开始 ===");
            System.out.println("导出数据量: " + count + " 条");
            System.out.println("开始时间: " + System.currentTimeMillis());
            
//            excelService.exportPhoneNumbersPaged(response, count);
            excelService.exportPhoneNumbersMultiThreadQuery(response, count);
            
            System.out.println("=== Excel导出完成 ===");
            System.out.println("完成时间: " + System.currentTimeMillis());
            
        } catch (Exception e) {
            System.err.println("=== Excel导出失败 ===");
            System.err.println("错误信息: " + e.getMessage());
            throw e;
        } finally {
            // 释放全局锁
            globalLock.unlock();
            System.out.println("全局锁已释放，系统可接受新的导入导出请求");
        }
    }

    /**
     * 多线程查询导出PhoneNumbers数据
     * @param response HTTP响应
     * @param count 导出数据条数，默认100万条
     */
    @GetMapping("/exportMultiThread")
    public void exportPhoneNumbersMultiThread(HttpServletResponse response, 
                                             @RequestParam(defaultValue = "1000000") int count) {
        // 尝试获取全局锁
        if (!globalLock.tryLock()) {
            System.err.println("系统繁忙，当前有其他导入或导出任务正在进行，请稍后重试");
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        
        try {
            System.out.println("=== 多线程导出开始 ===");
            System.out.println("导出数据量: " + count + " 条");
            excelService.exportPhoneNumbersMultiThreadQuery(response, count);
            System.out.println("=== 多线程导出完成 ===");
        } catch (Exception e) {
            System.err.println("=== 多线程导出失败 ===");
            throw e;
        } finally {
            globalLock.unlock();
            System.out.println("全局锁已释放");
        }
    }

    /**
     * 多线程查询导出PhoneNumbers数据（使用submit方式）
     * @param response HTTP响应
     * @param count 导出数据条数，默认100万条
     */
    @GetMapping("/exportMultiThreadSubmit")
    public void exportPhoneNumbersMultiThreadSubmit(HttpServletResponse response, 
                                                   @RequestParam(defaultValue = "1000000") int count) {
        // 尝试获取全局锁
        if (!globalLock.tryLock()) {
            System.err.println("系统繁忙，当前有其他导入或导出任务正在进行，请稍后重试");
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        
        try {
            System.out.println("=== Submit方式导出开始 ===");
            System.out.println("导出数据量: " + count + " 条");
            excelService.exportPhoneNumbersMultiThreadQueryWithSubmit(response, count);
            System.out.println("=== Submit方式导出完成 ===");
        } catch (Exception e) {
            System.err.println("=== Submit方式导出失败 ===");
            throw e;
        } finally {
            globalLock.unlock();
            System.out.println("全局锁已释放");
        }
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

    /**
     * 优化的分批导出接口
     * 每页2万条数据，每批5次分页查询，使用子查询优化SQL
     * 避免内存溢出，提高导出效率
     * @param response HTTP响应
     * @param count 导出数据条数，默认100万条
     */
    @GetMapping("/exportOptimized")
    public void exportPhoneNumbersOptimized(HttpServletResponse response, 
                                          @RequestParam(defaultValue = "1000000") int count) {
        // 尝试获取全局锁
        if (!globalLock.tryLock()) {
            System.err.println("系统繁忙，当前有其他导入或导出任务正在进行，请稍后重试");
            throw new RuntimeException("系统繁忙，请稍后重试");
        }
        
        try {
            System.out.println("=== 优化分批导出开始 ===");
            System.out.println("导出数据量: " + count + " 条");
            System.out.println("开始时间: " + System.currentTimeMillis());
            
            excelService.exportPhoneNumbersOptimized(response, count);
            
            System.out.println("=== 优化分批导出完成 ===");
            System.out.println("完成时间: " + System.currentTimeMillis());
            
        } catch (Exception e) {
            System.err.println("=== 优化分批导出失败 ===");
            System.err.println("错误信息: " + e.getMessage());
            throw e;
        } finally {
            globalLock.unlock();
            System.out.println("全局锁已释放");
        }
    }

    /**
     * 获取系统状态
     * 查看当前全局锁的状态
     */
    @GetMapping("/status")
    public String getSystemStatus() {
        StringBuilder status = new StringBuilder();
        status.append("=== 系统状态 ===\n");
        status.append("当前时间: ").append(System.currentTimeMillis()).append("\n");
        status.append("全局锁状态:\n");
        status.append("  - 是否被锁定: ").append(globalLock.isLocked()).append("\n");
        status.append("  - 等待队列长度: ").append(globalLock.getQueueLength()).append("\n");
        status.append("  - 当前持有锁的线程: ").append(globalLock.isHeldByCurrentThread() ? "当前线程" : "其他线程").append("\n");
        status.append("系统资源:\n");
        status.append("  - CPU核心数: ").append(Runtime.getRuntime().availableProcessors()).append("\n");
        status.append("  - 最大内存: ").append(Runtime.getRuntime().maxMemory() / 1024 / 1024).append(" MB\n");
        status.append("  - 已用内存: ").append((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024).append(" MB\n");
        status.append("  - 可用内存: ").append(Runtime.getRuntime().freeMemory() / 1024 / 1024).append(" MB\n");
        return status.toString();
    }

}
