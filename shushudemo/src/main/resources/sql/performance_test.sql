-- ========================================
-- 性能测试脚本
-- 测试索引优化前后的查询性能
-- ========================================

-- 1. 测试游标分页查询性能
-- 测试不同起始ID的查询时间

-- 测试1：从开始位置查询
SET @start_time = NOW(6);
SELECT id, name, phone_number, status, email, city, created_at 
FROM phone_numbers 
WHERE id > 0 
ORDER BY id ASC 
LIMIT 50000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 测试2：从中间位置查询
SET @start_time = NOW(6);
SELECT id, name, phone_number, status, email, city, created_at 
FROM phone_numbers 
WHERE id > 500000 
ORDER BY id ASC 
LIMIT 50000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 测试3：从末尾位置查询
SET @start_time = NOW(6);
SELECT id, name, phone_number, status, email, city, created_at 
FROM phone_numbers 
WHERE id > 950000 
ORDER BY id ASC 
LIMIT 50000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 2. 分析查询执行计划
EXPLAIN FORMAT=JSON 
SELECT id, name, phone_number, status, email, city, created_at 
FROM phone_numbers 
WHERE id > 0 
ORDER BY id ASC 
LIMIT 50000;

-- 3. 测试不同批次大小的性能
-- 测试1000条
SET @start_time = NOW(6);
SELECT COUNT(*) FROM phone_numbers WHERE id > 0 ORDER BY id ASC LIMIT 1000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 测试10000条
SET @start_time = NOW(6);
SELECT COUNT(*) FROM phone_numbers WHERE id > 0 ORDER BY id ASC LIMIT 10000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 测试50000条
SET @start_time = NOW(6);
SELECT COUNT(*) FROM phone_numbers WHERE id > 0 ORDER BY id ASC LIMIT 50000;
SELECT TIMESTAMPDIFF(MICROSECOND, @start_time, NOW(6)) as query_time_microseconds;

-- 4. 监控索引使用情况
SELECT 
    OBJECT_SCHEMA,
    OBJECT_NAME,
    INDEX_NAME,
    COUNT_FETCH,
    COUNT_INSERT,
    COUNT_UPDATE,
    COUNT_DELETE
FROM performance_schema.table_io_waits_summary_by_index_usage 
WHERE OBJECT_SCHEMA = DATABASE() 
AND OBJECT_NAME = 'phone_numbers';
