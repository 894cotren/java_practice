package com.voracity.shushudemo.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.voracity.shushudemo.excel.model.PhoneNumbers;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Excel数据生成器
 * 用于生成电话号码测试数据
 */
public class ExcelDataGenerator {
    
    @Data
    public static class PhoneData {
        @ExcelProperty("姓名")
        private String name;
        
        @ExcelProperty("电话号码")
        private String phoneNumber;
        
        @ExcelProperty("状态")
        private String status;
        
        @ExcelProperty("邮箱")
        private String email;
        
        @ExcelProperty("城市")
        private String city;
        
        @ExcelProperty("创建时间")
        private String createdAt;
    }
    
    private static final String[] NAMES = {
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
    
    private static final String[] CITIES = {
        "北京", "上海", "广州", "深圳", "杭州", "南京", "苏州", "成都",
        "重庆", "武汉", "西安", "天津", "青岛", "大连", "宁波", "厦门",
        "福州", "济南", "长沙", "郑州", "沈阳", "哈尔滨", "长春", "石家庄",
        "太原", "呼和浩特", "兰州", "西宁", "银川", "乌鲁木齐", "拉萨", "昆明",
        "贵阳", "南宁", "海口", "三亚", "合肥", "南昌", "福州", "石家庄"
    };
    
    private static final String[] STATUSES = {
        "正常", "停机", "欠费", "注销", "暂停"
    };
    
    private static final String[] EMAIL_DOMAINS = {
        "gmail.com", "qq.com", "163.com", "126.com", "sina.com", 
        "hotmail.com", "outlook.com", "foxmail.com", "sohu.com", "yahoo.com"
    };
    
    public static void main(String[] args) {
        generatePhoneDataExcel();
    }
    
    public static void generatePhoneDataExcel() {
        List<PhoneData> dataList = generatePhoneData(100);
        
        String fileName = "src/main/resources/phone_numbers_data.xlsx";
        EasyExcel.write(fileName, PhoneData.class)
                .sheet("电话号码数据")
                .doWrite(dataList);
        
        System.out.println("Excel文件生成成功: " + fileName);
        System.out.println("共生成 " + dataList.size() + " 条数据");
    }
    
    private static List<PhoneData> generatePhoneData(int count) {
        List<PhoneData> dataList = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < count; i++) {
            PhoneData data = new PhoneData();
            
            // 生成姓名
            data.setName(NAMES[i % NAMES.length] + (i > NAMES.length - 1 ? "_" + (i + 1) : ""));
            
            // 生成电话号码 (11位手机号)
            data.setPhoneNumber(generatePhoneNumber(random));
            
            // 生成状态
            data.setStatus(STATUSES[random.nextInt(STATUSES.length)]);
            
            // 生成邮箱
            data.setEmail(generateEmail(data.getName(), random));
            
            // 生成城市
            data.setCity(CITIES[random.nextInt(CITIES.length)]);
            
            // 生成创建时间
            data.setCreatedAt(generateCreateTime(random));
            
            dataList.add(data);
        }
        
        return dataList;
    }
    
    private static String generatePhoneNumber(Random random) {
        // 生成11位手机号，以1开头
        StringBuilder phone = new StringBuilder("1");
        int[] prefixes = {3, 4, 5, 6, 7, 8, 9};
        phone.append(prefixes[random.nextInt(prefixes.length)]);
        
        for (int i = 0; i < 9; i++) {
            phone.append(random.nextInt(10));
        }
        
        return phone.toString();
    }
    
    private static String generateEmail(String name, Random random) {
        String domain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
        return name.toLowerCase() + random.nextInt(1000) + "@" + domain;
    }
    
    private static String generateCreateTime(Random random) {
        // 生成最近30天内的随机时间
        long now = System.currentTimeMillis();
        long thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000);
        long randomTime = thirtyDaysAgo + (long) (random.nextDouble() * (now - thirtyDaysAgo));
        
        Date date = new Date(randomTime);
        return String.format("%tF %tT", date, date);
    }
}

