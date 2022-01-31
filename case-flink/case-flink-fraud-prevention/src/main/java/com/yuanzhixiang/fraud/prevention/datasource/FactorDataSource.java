package com.yuanzhixiang.fraud.prevention.datasource;

import static java.math.BigDecimal.valueOf;

import java.io.Serializable;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.flink.streaming.api.functions.source.FromIteratorFunction;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/31 11:53:17
 */
public class FactorDataSource extends FromIteratorFunction<Factor> {

    public FactorDataSource() {
        super(new FactorIterator());
    }

    private static class FactorIterator implements Iterator<Factor>, Serializable {

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Factor next() {

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Factor factor = new Factor();
            factor.setSymbol(UUID.randomUUID().toString());
            factor.setOpen(valueOf(Math.random()).multiply(valueOf(100)).doubleValue());
            return factor;
        }
    }
}
