package com.voracity.shushudemo.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.voracity.shushudemo.excel.ExcelModel;
import com.voracity.shushudemo.excel.model.PhoneNumbers;
import com.voracity.shushudemo.excel.model.PhoneNumbersExportDTO;
import com.voracity.shushudemo.excel.model.PhoneNumbersImportDTO;
import com.voracity.shushudemo.excel.model.PhoneNumbersImportTemplate;
import com.voracity.shushudemo.excel.service.ExcelReadListener;
import com.voracity.shushudemo.excel.service.ExcelService;
import com.voracity.shushudemo.excel.service.PhoneNumbersService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private PhoneNumbersService phoneNumbersService;

    @Resource
    private ThreadPoolTaskExecutor exportTaskExecutor;

    @Resource
    private ThreadPoolTaskExecutor excelTaskExecutor;

    @Override
    public void importExcelModel(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            // 使用导入DTO读取Excel，通过构造函数注入PhoneNumbersService
            ExcelReadListener listener = new ExcelReadListener(phoneNumbersService,excelTaskExecutor);
            EasyExcel.read(inputStream, PhoneNumbersImportDTO.class, listener)
                    .sheet()
                    .doRead();
            
            // 输出处理结果
            System.out.println(listener.getProcessResult());
            if (!listener.getErrorList().isEmpty()) {
                System.out.println("错误详情:");
                listener.getErrorList().forEach(System.out::println);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("excel导入失败");
        }
    }

    @Override
    public void exportPhoneNumbers(HttpServletResponse response, List<PhoneNumbersExportDTO> dataList) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            
            // 设置文件名
            String fileName = URLEncoder.encode("电话号码数据_" + System.currentTimeMillis(), "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            // 写入Excel
            EasyExcel.write(response.getOutputStream(), PhoneNumbersExportDTO.class)
                    .sheet("电话号码数据")
                    .doWrite(dataList);
                    
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel导出失败");
        }
    }

    @Override
    public List<PhoneNumbersExportDTO> generateTestData(int count) {
        List<PhoneNumbersExportDTO> dataList = new ArrayList<>();
        Random random = new Random();
        
        // 预定义的测试数据
        String[] names = {
            "张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十",
            "郑十一", "王十二", "冯十三", "陈十四", "褚十五", "卫十六", "蒋十七", "沈十八",
            "韩十九", "杨二十", "朱二一", "秦二二", "尤二三", "许二四", "何二五", "吕二六",
            "施二七", "张二八", "孔二九", "曹三十", "严三一", "华三二", "金三三", "魏三四",
            "陶三五", "姜三六", "戚三七", "谢三八", "邹三九", "喻四十", "柏四一", "水四二",
            "窦四三", "章四四", "云四五", "苏四六", "潘四七", "葛四八", "奚四九", "范五十",
            "彭五一", "郎五二", "鲁五三", "韦五四", "昌五五", "马五六", "苗五七", "凤五八",
            "花五九", "方六十", "俞六一", "任六二", "袁六三", "柳六四", "酆六五", "鲍六六",
            "史六七", "唐六八", "费六九", "廉七十", "岑七一", "薛七二", "雷七三", "贺七四",
            "倪七五", "汤七六", "滕七七", "殷七八", "罗七九", "毕八十", "郝八一", "邬八二",
            "安八三", "常八四", "乐八五", "于八六", "时八七", "傅八八", "皮八九", "卞九十",
            "齐九一", "康九二", "伍九三", "余九四", "元九五", "卜九六", "顾九七", "孟九八",
            "平九九", "黄一百"
        };
        
        String[] cities = {
            "北京", "上海", "广州", "深圳", "杭州", "南京", "苏州", "成都",
            "重庆", "武汉", "西安", "天津", "青岛", "大连", "宁波", "厦门",
            "福州", "济南", "长沙", "郑州", "沈阳", "哈尔滨", "长春", "石家庄",
            "太原", "呼和浩特", "兰州", "西宁", "银川", "乌鲁木齐", "拉萨", "昆明",
            "贵阳", "南宁", "海口", "三亚", "合肥", "南昌", "福州", "石家庄"
        };
        
        String[] statuses = {"正常", "停机", "欠费", "注销", "暂停"};
        String[] emailDomains = {
            "gmail.com", "qq.com", "163.com", "126.com", "sina.com", 
            "hotmail.com", "outlook.com", "foxmail.com", "sohu.com", "yahoo.com"
        };
        
        for (int i = 0; i < count; i++) {
            PhoneNumbersExportDTO dto = new PhoneNumbersExportDTO();
            
            // 生成姓名
            dto.setName(names[i % names.length] + (i >= names.length ? "_" + (i + 1) : ""));
            
            // 生成电话号码 (11位手机号)
            dto.setPhoneNumber(generatePhoneNumber(random));
            
            // 生成状态
            dto.setStatus(statuses[random.nextInt(statuses.length)]);
            
            // 生成邮箱
            dto.setEmail(generateEmail(dto.getName(), random, emailDomains));
            
            // 生成城市
            dto.setCity(cities[random.nextInt(cities.length)]);

//            // 生成创建时间
//            dto.setCreatedAt(generateCreateTime(random));
            
            dataList.add(dto);
        }
        
        return dataList;
    }
    
    /**
     * 生成11位手机号
     */
    private String generatePhoneNumber(Random random) {
        StringBuilder phone = new StringBuilder("1");
        int[] prefixes = {3, 4, 5, 6, 7, 8, 9};
        phone.append(prefixes[random.nextInt(prefixes.length)]);
        
        for (int i = 0; i < 9; i++) {
            phone.append(random.nextInt(10));
        }
        
        return phone.toString();
    }
    
    /**
     * 生成邮箱
     */
    private String generateEmail(String name, Random random, String[] emailDomains) {
        String domain = emailDomains[random.nextInt(emailDomains.length)];
        return name.toLowerCase() + random.nextInt(1000) + "@" + domain;
    }
    
    /**
     * 生成创建时间
     */
    private Date generateCreateTime(Random random) {
        // 生成最近30天内的随机时间
        long now = System.currentTimeMillis();
        long thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000);
        long randomTime = thirtyDaysAgo + (long) (random.nextDouble() * (now - thirtyDaysAgo));
        
        return new Date(randomTime);
    }
    
    @Override
    public void downloadImportTemplate(HttpServletResponse response) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            
            // 设置文件名
            String fileName = URLEncoder.encode("电话号码导入模板", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            // 创建示例数据
            List<PhoneNumbersImportTemplate> templateData = new ArrayList<>();
            templateData.add(PhoneNumbersImportTemplate.createSample());
            
            // 写入Excel模板
            EasyExcel.write(response.getOutputStream(), PhoneNumbersImportTemplate.class)
                    .sheet("导入模板")
                    .doWrite(templateData);
                    
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("模板下载失败");
        }
    }

    @Override
    public void exportPhoneNumbersMultiThread(HttpServletResponse response, int count) {
        try {
            long startTime = System.currentTimeMillis();
            
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("电话号码数据_" + System.currentTimeMillis(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            // 计算分页参数
            int pageSize = 10000; // 每页1万条数据
            int totalPages = (count + pageSize - 1) / pageSize;
            
            System.out.println("开始多线程导出，总数据量: " + count + "，分页数: " + totalPages + "，每页: " + pageSize);
            
            // 使用线程池并行查询所有页面
            List<CompletableFuture<List<PhoneNumbersExportDTO>>> futures = new ArrayList<>();
            
            for (int i = 0; i < totalPages; i++) {
                final int pageNum = i;
                CompletableFuture<List<PhoneNumbersExportDTO>> future = CompletableFuture.supplyAsync(() -> {
                    try {
                        // 分页查询数据
                        Page<PhoneNumbers> page = new Page<>(pageNum + 1, pageSize);
                        List<PhoneNumbers> data = phoneNumbersService.page(page).getRecords();
                        
                        // 转换为DTO
                        List<PhoneNumbersExportDTO> dtoList = data.stream()
                                .map(PhoneNumbersExportDTO::fromEntity)
                                .collect(Collectors.toList());
                        
                        System.out.println("线程 " + Thread.currentThread().getName() + " 完成第 " + (pageNum + 1) + " 页查询，数据量: " + dtoList.size());
                        return dtoList;
                    } catch (Exception e) {
                        System.err.println("第 " + (pageNum + 1) + " 页查询失败: " + e.getMessage());
                        return new ArrayList<>();
                    }
                }, exportTaskExecutor);
                futures.add(future);
            }
            
            // 等待所有查询完成并合并结果
            List<PhoneNumbersExportDTO> allData = new ArrayList<>();
            for (int i = 0; i < futures.size(); i++) {
                try {
                    List<PhoneNumbersExportDTO> pageData = futures.get(i).get();
                    allData.addAll(pageData);
                    System.out.println("已合并第 " + (i + 1) + " 页数据，当前总数据量: " + allData.size());
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("获取第 " + (i + 1) + " 页数据失败: " + e.getMessage());
                }
            }
            
            long queryEndTime = System.currentTimeMillis();
            System.out.println("数据查询完成，耗时: " + (queryEndTime - startTime) + "ms，实际数据量: " + allData.size());
            
            // 写入Excel
            EasyExcel.write(response.getOutputStream(), PhoneNumbersExportDTO.class)
                    .sheet("电话号码数据")
                    .doWrite(allData);
            
            long endTime = System.currentTimeMillis();
            System.out.println("Excel导出完成，总耗时: " + (endTime - startTime) + "ms");
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel导出失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出过程中发生错误: " + e.getMessage());
        }
    }

    @Override
    public void exportPhoneNumbersPaged(HttpServletResponse response, int count) {
        try {
            long startTime = System.currentTimeMillis();
            
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("电话号码数据_" + System.currentTimeMillis(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            
            // 限制最大导出数量，避免Excel行数限制
            int maxRows = 1000000; // Excel最大支持1048576行，我们限制为100万行
            if (count > maxRows) {
                count = maxRows;
                System.out.println("数据量超过限制，调整为最大导出: " + maxRows + " 条");
            }
            
            // 分页参数
            int pageSize = 10000; // 每页1万条数据
            int totalPages = (count + pageSize - 1) / pageSize;
            
            System.out.println("开始分页导出，总数据量: " + count + "，分页数: " + totalPages + "，每页: " + pageSize);
            
            // 使用分批写入的方式
            try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), PhoneNumbersExportDTO.class).build()) {
                WriteSheet writeSheet = EasyExcel.writerSheet("电话号码数据").build();
                
                for (int i = 0; i < totalPages; i++) {
                    long pageStartTime = System.currentTimeMillis();
                    
                    // 使用LIMIT分页查询，确保分页生效
                    int offset = i * pageSize;
                    List<PhoneNumbers> data = phoneNumbersService.list(
                        new QueryWrapper<PhoneNumbers>()
                            .last("LIMIT " + offset + ", " + pageSize)
                    );
                    
                    // 转换为DTO
                    List<PhoneNumbersExportDTO> dtoList = data.stream()
                            .map(PhoneNumbersExportDTO::fromEntity)
                            .collect(Collectors.toList());
                    
                    // 写入Excel
                    excelWriter.write(dtoList, writeSheet);
                    
                    long pageEndTime = System.currentTimeMillis();
                    System.out.println("第 " + (i + 1) + " 页处理完成，数据量: " + dtoList.size() + 
                                     "，耗时: " + (pageEndTime - pageStartTime) + "ms");
                    
                    // 清理内存
                    dtoList.clear();
                    data.clear();
                }
            }
            
            long endTime = System.currentTimeMillis();
            System.out.println("分页导出完成，总耗时: " + (endTime - startTime) + "ms");
            
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel导出失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出过程中发生错误: " + e.getMessage());
        }
    }
}
