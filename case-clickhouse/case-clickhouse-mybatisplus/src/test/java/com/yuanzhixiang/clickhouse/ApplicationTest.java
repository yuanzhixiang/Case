package com.yuanzhixiang.clickhouse;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.BadSqlGrammarException;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuanzhixiang.clickhouse.mapper.QuantStockMinute5QuotationMapper;
import com.yuanzhixiang.clickhouse.po.QuantStockMinute5Quotation;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 22:46:43
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ApplicationTest {

    @Autowired
    private QuantStockMinute5QuotationMapper quantStockMinute5QuotationMapper;

    @Order(10)
    @Test
    public void testCreateTable() {
        quantStockMinute5QuotationMapper.createDatabase();
        quantStockMinute5QuotationMapper.dropTable();
        quantStockMinute5QuotationMapper.createTable();
    }

    @Order(20)
    @Test
    public void testInsert() {
        LocalDateTime date = LocalDateTime.of(2002, 12, 31, 19, 3, 30);

        LambdaQueryWrapper<QuantStockMinute5Quotation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuantStockMinute5Quotation::getDate, date);

        // 未插入数据前查询不出数据
        List<QuantStockMinute5Quotation> quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(0, quotationList.size());

        // 插入数据
        QuantStockMinute5Quotation quotation = new QuantStockMinute5Quotation();
        quotation.setSymbol("symbol");
        quotation.setDate(date);
        quantStockMinute5QuotationMapper.insert(quotation);

        // 插入数据后可查询出数据
        quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(1, quotationList.size());

        // 插入第二条数据
        quantStockMinute5QuotationMapper.insert(quotation);

        // 插入第二条数据后可查出两条
        quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(2, quotationList.size());
    }

    @Test
    public void testDelete() {
        LocalDateTime date = LocalDateTime.of(2003, 12, 31, 19, 3, 30);

        LambdaQueryWrapper<QuantStockMinute5Quotation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuantStockMinute5Quotation::getDate, date);

        // 未插入数据前查询不出数据
        List<QuantStockMinute5Quotation> quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(0, quotationList.size());

        // 插入数据
        QuantStockMinute5Quotation quotation = new QuantStockMinute5Quotation();
        quotation.setSymbol("symbol");
        quotation.setDate(date);
        quantStockMinute5QuotationMapper.insert(quotation);

        // 插入数据后可查询出数据
        quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(1, quotationList.size());

        // 删除该数据
        quantStockMinute5QuotationMapper.delete(wrapper);

        // 删除后数据无法查出
        quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(0, quotationList.size());
    }

    @Test
    public void testUpdate() {
        LocalDateTime date = LocalDateTime.of(2003, 12, 31, 19, 3, 30);

        LambdaQueryWrapper<QuantStockMinute5Quotation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QuantStockMinute5Quotation::getDate, date);

        // 未插入数据前查询不出数据
        List<QuantStockMinute5Quotation> quotationList = quantStockMinute5QuotationMapper.selectList(wrapper);
        Assertions.assertEquals(0, quotationList.size());

        // 插入数据
        QuantStockMinute5Quotation quotation = new QuantStockMinute5Quotation();
        quotation.setSymbol("symbol");
        quotation.setDate(date);
        quantStockMinute5QuotationMapper.insert(quotation);

        // 插入数据后可查询出数据
        quotation = quantStockMinute5QuotationMapper.selectOne(wrapper);
        Assertions.assertEquals(0, quotation.getAdjustment());

        // Clickhouse 禁止更新数据，所以这里检测抛出异常
        QuantStockMinute5Quotation finalQuotation = quotation;
        Assertions.assertThrows(BadSqlGrammarException.class, () -> {
            finalQuotation.setAdjustment(1.2);
            quantStockMinute5QuotationMapper.update(finalQuotation, wrapper);
        });
    }
}
