package com.yuanzhixiang.clickhouse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * 三方驱动快速入门
 *
 * @author ZhiXiang Yuan
 * @date 2022/01/24 20:01:41
 */
public class QuickStart {

    public static void main(String[] args) throws Exception {
        Class.forName("com.github.housepower.jdbc.ClickHouseDriver");
        // tcp 端口 9000
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

        // 插入数据
        String sql = "INSERT INTO tutorial.quant_stock_minute5_quotation "
            + "(symbol, date, adjustment, open, close, low, high, volume) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        LocalDateTime time = LocalDateTime.of(2001, 1, 1, 0, 0, 0, 0);
        BigDecimal defaultDecimal = BigDecimal.valueOf(1.1);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 循环十遍写入
        for (int index = 0; index < 10; index++) {

            // 写入一千万数据
            long start = System.currentTimeMillis();
            for (int i = 0; i < 10000000; i++) {
                time = time.plusSeconds(1);

                ZonedDateTime zonedDateTime = ZonedDateTime.of(time, ZoneId.systemDefault());
                preparedStatement.setString(1, "000001");
                preparedStatement.setObject(2, zonedDateTime);
                preparedStatement.setBigDecimal(3, defaultDecimal);
                preparedStatement.setBigDecimal(4, defaultDecimal);
                preparedStatement.setBigDecimal(5, defaultDecimal);
                preparedStatement.setBigDecimal(6, defaultDecimal);
                preparedStatement.setBigDecimal(7, defaultDecimal);
                preparedStatement.setBigDecimal(8, defaultDecimal);

                preparedStatement.addBatch();
            }
            long end1 = System.currentTimeMillis();
            System.out.println("数据准备时间：" + (end1 - start) + "ms");

            preparedStatement.executeBatch();
            long end2 = System.currentTimeMillis();
            System.out.println("数据写入时间：" + (end2 - end1) + "ms");
        }
    }
}
