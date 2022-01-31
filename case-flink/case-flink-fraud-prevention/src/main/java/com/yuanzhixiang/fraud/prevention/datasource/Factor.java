package com.yuanzhixiang.fraud.prevention.datasource;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/31 12:05:51
 */
public class Factor {

    private String symbol;

    private Double open;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Factor{" +
            "symbol='" + symbol + '\'' +
            ", open=" + open +
            '}';
    }
}
