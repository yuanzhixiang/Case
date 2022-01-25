package com.yuanzhixiang.clickhouse.mybatisplus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuanzhixiang.clickhouse.mybatisplus.mapper.QuantStockMinute5QuotationMapper;
import com.yuanzhixiang.clickhouse.mybatisplus.po.QuantStockMinute5Quotation;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 22:46:43
 */
@SpringBootTest
class ApplicationTest {

    @Autowired
    private QuantStockMinute5QuotationMapper quantStockMinute5QuotationMapper;

    @Test
    public void testSelect() {
        LambdaQueryWrapper<QuantStockMinute5Quotation> wrapper = new LambdaQueryWrapper<>();
        ZonedDateTime time = ZonedDateTime.of(LocalDateTime.of(2003, 12, 31, 19, 3, 30), ZoneId.systemDefault());
        wrapper.eq(QuantStockMinute5Quotation::getDate, time);
        List<QuantStockMinute5Quotation> quantStockMinute5Quotations = quantStockMinute5QuotationMapper.selectList(wrapper);
        System.out.println();
    }
}
