package com.awc20.practice.stream;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

// 初始化测试数据（直接使用即可）
public class StreamPracticeData {
    // 1. 用户集合
    public static List<User> userList = Arrays.asList(
        new User(1L, "张三", 25, "男", "北京", new BigDecimal("15000")),
        new User(2L, "李四", 32, "男", "上海", new BigDecimal("25000")),
        new User(3L, "王五", 28, "女", "北京", new BigDecimal("18000")),
        new User(4L, "赵六", 45, "男", "广州", new BigDecimal("35000")),
        new User(5L, "孙七", 22, "女", "深圳", new BigDecimal("12000")),
        new User(6L, "周八", 38, "女", "上海", new BigDecimal("28000")),
        new User(7L, "吴九", 25, "男", "北京", new BigDecimal("16000")),
        new User(8L, "郑十", 30, "女", "深圳", new BigDecimal("22000"))
    );

    // 2. 商品集合
    public static List<Product> productList = Arrays.asList(
        new Product(101L, "iPhone 15", "电子产品", new BigDecimal("6999"), 50),
        new Product(102L, "华为Mate 60", "电子产品", new BigDecimal("5999"), 30),
        new Product(103L, "纯棉T恤", "服装", new BigDecimal("99"), 200),
        new Product(104L, "牛仔裤", "服装", new BigDecimal("199"), 150),
        new Product(105L, "进口牛奶", "食品", new BigDecimal("68"), 100),
        new Product(106L, "巧克力", "食品", new BigDecimal("35"), 300),
        new Product(107L, "笔记本电脑", "电子产品", new BigDecimal("4999"), 20),
        new Product(108L, "运动鞋", "服装", new BigDecimal("399"), 80)
    );

    // 3. 订单集合
    public static List<Order> orderList = Arrays.asList(
        new Order(1001L, 1L, Arrays.asList(101L, 103L), LocalDateTime.of(2024, 3, 15, 10, 30), new BigDecimal("7098")),
        new Order(1002L, 2L, Arrays.asList(107L, 104L), LocalDateTime.of(2024, 4, 2, 14, 15), new BigDecimal("5198")),
        new Order(1003L, 3L, Arrays.asList(105L, 106L), LocalDateTime.of(2024, 3, 28, 9, 40), new BigDecimal("103")),
        new Order(1004L, 5L, Arrays.asList(102L), LocalDateTime.of(2024, 4, 10, 16, 20), new BigDecimal("5999")),
        new Order(1005L, 8L, Arrays.asList(108L, 103L), LocalDateTime.of(2024, 3, 5, 11, 5), new BigDecimal("498")),
        new Order(1006L, 4L, Arrays.asList(101L, 107L), LocalDateTime.of(2024, 4, 15, 18, 30), new BigDecimal("11998")),
        new Order(1007L, 6L, Arrays.asList(104L, 106L), LocalDateTime.of(2024, 3, 20, 15, 10), new BigDecimal("234")),
        new Order(1008L, 7L, Arrays.asList(103L, 105L), LocalDateTime.of(2024, 4, 8, 12, 25), new BigDecimal("167"))
    );
}
