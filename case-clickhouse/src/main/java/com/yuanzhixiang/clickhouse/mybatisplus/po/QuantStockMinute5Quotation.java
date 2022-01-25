package com.yuanzhixiang.clickhouse.mybatisplus.po;


import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 22:41:48
 */
@Data
public class QuantStockMinute5Quotation {

    private String symbol;

    private LocalDateTime date;

    private Double adjustment;

    private Double open;

    private Double close;

    private Double low;

    private Double high;

    private Integer volume;

}
