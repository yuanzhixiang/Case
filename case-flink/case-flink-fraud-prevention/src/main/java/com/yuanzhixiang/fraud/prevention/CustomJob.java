package com.yuanzhixiang.fraud.prevention;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.yuanzhixiang.fraud.prevention.datasource.Factor;
import com.yuanzhixiang.fraud.prevention.datasource.FactorDataSource;
import com.yuanzhixiang.fraud.prevention.process.FactorProcess;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/31 11:49:52
 */
public class CustomJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置数据源
        DataStream<Factor> numberDataSource = env.addSource(new FactorDataSource()).name("Factor data source");

        // 设置任务
        numberDataSource.process(new FactorProcess()).name("Factor process");

        // 执行任务
        env.execute("Custom job name");
    }
}
