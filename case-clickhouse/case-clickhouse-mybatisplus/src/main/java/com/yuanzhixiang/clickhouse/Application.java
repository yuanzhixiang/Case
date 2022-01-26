package com.yuanzhixiang.clickhouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ZhiXiang Yuan
 * @date 2022/01/25 22:40:07
 */
@SpringBootApplication
@MapperScan("com.yuanzhixiang.clickhouse.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
