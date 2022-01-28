# 项目由来

该项目是 flink 官网教程项目，地址 https://nightlies.apache.org/flink/flink-docs-master/zh/docs/try-flink/datastream/，通过下面命令进行创建

```shell
mvn archetype:generate \
    -DarchetypeGroupId=org.apache.flink \
    -DarchetypeArtifactId=flink-walkthrough-datastream-java \
    -DarchetypeVersion=1.15-SNAPSHOT \
    -DgroupId=frauddetection \
    -DartifactId=frauddetection \
    -Dversion=0.1 \
    -Dpackage=spendreport \
    -DinteractiveMode=false
```

上面的命令如果报错可能是仓库没配置好，需要在仓库中增加下面的依赖

```xml
        <repository>
          <id>apache-snapshots</id>
          <url>https://repository.apache.org/content/repositories/snapshots/</url>
        </repository>
```
