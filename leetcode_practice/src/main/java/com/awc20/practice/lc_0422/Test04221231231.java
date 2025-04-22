package com.awc20.practice.lc_0422;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;

public class Test04221231231 {
    public static void main(String[] args) {
        String createDateStart ="2023-03-32";
        String createDateEnd = "2023-04-11";
        checkDateFormat(createDateStart,createDateEnd);
    }
    public static void checkDateFormat(String createDateStart, String createDateEnd) {
        //非空
        if (StringUtils.isAnyBlank(createDateStart,createDateEnd)){
            throw new IllegalArgumentException("查询日期为空");
        }
        //格式正确 yyyy-MM-dd
        String dateRegex="^\\d{4}-\\d{2}-\\d{2}$";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("uuuu-MM-dd")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        if (!createDateStart.matches(dateRegex)|| !createDateEnd.matches(dateRegex)){
            throw new IllegalArgumentException("查询日期格式错误");
        }
        //日期合理  pass 2.31
        //起始日期与结束日期之间小于60天
        try {
            LocalDate startDate = LocalDate.parse(createDateStart, formatter);
            LocalDate endDate = LocalDate.parse(createDateEnd, formatter);
            long gapDays = ChronoUnit.DAYS.between(startDate, endDate);
            if (startDate.isAfter(endDate)||gapDays>60){
                throw new IllegalArgumentException();
            }
        }catch (DateTimeParseException | IllegalArgumentException e){
            throw new IllegalArgumentException("查询日期不符合规范");
        }


    }
}
