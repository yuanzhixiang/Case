package com.yuanzhixiang.clickhouse.ru.yandex;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 10:59:29
 */
public class QuickStart {

    public static void main(String[] args) throws Exception {
        Class.forName("com.clickhouse.jdbc.ClickHouseDriver");
        // http 端口 8123
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:9000");
    }
}
