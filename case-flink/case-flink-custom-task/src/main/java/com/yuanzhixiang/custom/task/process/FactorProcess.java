package com.yuanzhixiang.custom.task.process;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import com.yuanzhixiang.custom.task.datasource.Factor;


/**
 * @author ZhiXiang Yuan
 * @date 2022/01/31 12:01:35
 */
public class FactorProcess extends ProcessFunction<Factor, Integer> {

    @Override
    public void processElement(Factor value, ProcessFunction<Factor, Integer>.Context ctx, Collector<Integer> out)
        throws Exception {
        System.out.println(value);
    }
}
