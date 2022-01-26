# ClickHouse 安装流程

## Macos 安装流程

1. 下载文件：`curl -O 'https://builds.clickhouse.com/master/macos/clickhouse' && chmod a+x ./clickhouse`
2. 从 https://github.com/ClickHouse/ClickHouse/tree/master/programs/server 文件夹下，下载 `config.xml` 和 `users.xml` 文件，和 clickhouse 放到同一级目录
3. 使用命令启动服务：`(./clickhouse server >/dev/null 2>&1 &)`

参考文档：https://clickhouse.com/docs/zh/getting-started/install/
