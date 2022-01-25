package com.yuanzhixiang.clickhouse.com.github;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 三方驱动快速入门
 *
 * @author ZhiXiang Yuan
 * @date 2022/01/24 20:01:41
 */
public class QuickStart {

    public static void main(String[] args) throws Exception {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        Connection connection = DriverManager.getConnection("jdbc:clickhouse://127.0.0.1:9000");

        Statement statement = connection.createStatement();

        // 创建数据库
        boolean createDatabase = statement.execute("CREATE DATABASE IF NOT EXISTS tutorial;");
        if (createDatabase) {
            System.out.println("创建数据库成功");
        }

        // 切换到刚刚数据库
        boolean useDatabase = statement.execute("use tutorial;");
        if (useDatabase) {
            System.out.println("切换到数据库成功");
        }

        // 展示数据库中的所有表
        ResultSet resultSet = statement.executeQuery("show tables;");
        System.out.println("以下为数据库中的数据表");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }

}
