package com.voracity.shushudemo.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// 订单类
@Data
@AllArgsConstructor
class Order {
    private Long id;                // 订单ID
    private Long userId;            // 关联的用户ID
    private List<Long> productIds;  // 订单包含的商品ID列表
    private LocalDateTime orderDate;// 下单时间
    private BigDecimal totalAmount; // 订单总金额
}
