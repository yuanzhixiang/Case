package com.yuanzhixiang.custom.task;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import com.yuanzhixiang.custom.task.datasource.Factor;
import com.yuanzhixiang.custom.task.datasource.FactorDataSource;
import com.yuanzhixiang.custom.task.process.FactorProcess;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/31 15:45:59
 */
public class MainTask {

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
