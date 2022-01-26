package com.yuanzhixiang.clickhouse.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuanzhixiang.clickhouse.po.QuantStockMinute5Quotation;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 22:45:37
 */
@Mapper
public interface QuantStockMinute5QuotationMapper extends BaseMapper<QuantStockMinute5Quotation> {

    void createDatabase();

    void dropTable();

    void createTable();

}
