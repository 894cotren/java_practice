package com.awc20.draft0421;


public enum MyTestEnum {
    PARSE_FAILED("PARSE_FAILED", "解析失败"),
    BOOKKEEPING_FAILED("BOOKKEEPING_FAILED", "记账失败"),
    BOOKKEEPING_SUCCESS("BOOKKEEPING_SUCCESS", "记账成功");

    // 状态码（对应数据库存储的值）
    private final String code;
    // 中文描述（展示给前端）
    private final String desc;

    // 构造函数（枚举的构造函数默认为 private）
    MyTestEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 根据 code 获取枚举实例（核心方法）
    public static MyTestEnum getByCode(String code) {
        for (MyTestEnum type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null; // 或抛出 IllegalArgumentException
    }

    // Getter 方法
    public String getCode() { return code; }
    public String getDesc() { return desc; }


    public static void main(String[] args) {
        MyTestEnum parseFailed = MyTestEnum.getByCode("PARSE_FAILED");
        System.out.println(parseFailed.getDesc());
    }
}
