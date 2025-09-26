package com.awc20.practice.stream;

// 商品类
@Data
@AllArgsConstructor
class Product {
    private Long id;          // 商品ID
    private String name;      // 商品名称
    private String category;  // 商品分类（如：电子产品、服装、食品）
    private BigDecimal price; // 商品单价
    private Integer stock;    // 库存数量
}
