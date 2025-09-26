-- ========================================
-- Phone Numbers 表索引优化脚本
-- 目标：提高分页查询和导出效率
-- ========================================

-- 1. 检查当前索引情况
SHOW INDEX FROM phone_numbers;

-- 2. 分析表结构和数据分布
SELECT 
    COUNT(*) as total_records,
    MIN(id) as min_id,
    MAX(id) as max_id,
    COUNT(DISTINCT status) as status_count,
    COUNT(DISTINCT city) as city_count
FROM phone_numbers;

-- 3. 创建核心索引（游标分页查询优化）
-- 这是最重要的索引，直接影响导出性能
CREATE INDEX idx_id_asc ON phone_numbers (id ASC);

-- 4. 创建业务查询索引
-- 根据实际业务需求选择创建

-- 状态查询索引
CREATE INDEX idx_status ON phone_numbers (status);

-- 城市查询索引  
CREATE INDEX idx_city ON phone_numbers (city);

-- 创建时间查询索引
CREATE INDEX idx_created_at ON phone_numbers (created_at);

-- 电话号码查询索引（如果经常按电话号码搜索）
CREATE INDEX idx_phone_number ON phone_numbers (phone_number);

-- 5. 创建复合索引（根据实际查询模式）
-- 状态+创建时间复合索引
CREATE INDEX idx_status_created_at ON phone_numbers (status, created_at);

-- 城市+状态复合索引
CREATE INDEX idx_city_status ON phone_numbers (city, status);

-- 6. 创建覆盖索引（如果查询字段固定）
-- 包含所有导出字段的覆盖索引，避免回表查询
CREATE INDEX idx_export_cover ON phone_numbers (id, name, phone_number, status, email, city, created_at);

-- 7. 分析索引使用情况
-- 执行查询后使用此语句分析索引效果
EXPLAIN SELECT id, name, phone_number, status, email, city, created_at 
FROM phone_numbers 
WHERE id > 0 
ORDER BY id ASC 
LIMIT 50000;

-- 8. 监控索引使用情况
SELECT 
    TABLE_NAME,
    INDEX_NAME,
    CARDINALITY,
    SUB_PART,
    PACKED,
    NULLABLE,
    INDEX_TYPE
FROM information_schema.STATISTICS 
WHERE TABLE_SCHEMA = DATABASE() 
AND TABLE_NAME = 'phone_numbers'
ORDER BY INDEX_NAME, SEQ_IN_INDEX;
