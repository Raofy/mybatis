# MyBatis官网
https://mybatis.org/mybatis-3/zh/index.html

# 简介
- MyBatis 是一款优秀的持久层框架，
- 它支持自定义 SQL、存储过程以及高级映射。
- MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
- MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

 # Mybatis获取
 - maven
```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.5</version>
</dependency>
```
- Github [点击下载](https://github.com/mybatis/mybatis-3)

# 持久化

就是将数据写入到硬盘上

# 持久层

就是完成持久化工作的代码块

# 第一个Mybatis程序

**整体思路**

搭建环境 -->  导入Mybatis --> 编写代码 --> 测试用例

### 搭建环境
- 首先创建一个mybatis数据（建议使用SQL语句去完成）

```SQL
create database mybatis;
```
- 创建数据表，并插入数据

```SQL
use mybatis;

CREATE TABLE `USER`(
   `id` INT(20) NOT NULL PRIMARY KEY,
   `name` VARCHAR(20) DEFAULT null,
   `pwd` VARCHAR(20) default null
)ENGINE=INNODB default charset=utf8;
insert into `user` (id , name ,pwd ) values (1,"小黑","123456"),
(2,"小虎","111111")
```

- 查询数据

```SQL
use mybatis;
select * from `user`; 
```

### 导入mybatis

- maven引入
```xml
<dependencys>
<!--Mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.46</version>
        </dependency>

        <!--mybatis依赖-->
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>
        </dependency>


        <!--juint依赖-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
    </dependencies>
```

- 创建模块
mybatis配置

1.方法一
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis配置文件-->
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
            </transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url"
                          value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;
autoReconnect=true"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
       <mapper resource="mapper/UserMapper.xml"/>
    </mappers>
</configuration>
```

2.方法二

```java

```


mybatis工具类

```java
package com.ryan.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//创建一个工厂，通过工厂创建一个SqlSession会话进行执行相关的sql语句
public class MybatisUtils {

    private static SqlSessionFactory factory;

    static {
        try {
            String configure = "mybatis-config.xml";
            InputStream resourceAsStream = Resources.getResourceAsStream(configure);
            factory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个sqlSession
     *
     * @return
     */
    public static SqlSession getSqlSession() {
        return factory.openSession();
    }
}

```

测试类
```java
package com.ryan.mybatis;

import com.ryan.mybatis.entity.User;
import com.ryan.mybatis.mapper.UserMapper;
import com.ryan.mybatis.utils.MybatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

@Slf4j
public class UserMapperTest {

    @Test
    public void run() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        for (User u : userList) {
            log.info(u.toString());
            System.out.println();
        }
    }
}
```

此时这里会出现异常
```java
Caused by: javax.net.ssl.SSLHandshakeException: java.security.cert.CertPathValidatorException: Path does not chain with any of the trust anchors
	at sun.security.ssl.Alert.createSSLException(Alert.java:131)
	at sun.security.ssl.TransportContext.fatal(TransportContext.java:327)
	at sun.security.ssl.TransportContext.fatal(TransportContext.java:270)
	at sun.security.ssl.TransportContext.fatal(TransportContext.java:265)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.checkServerCerts(CertificateMessage.java:646)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.onCertificate(CertificateMessage.java:465)
	at sun.security.ssl.CertificateMessage$T12CertificateConsumer.consume(CertificateMessage.java:361)
	at sun.security.ssl.SSLHandshake.consume(SSLHandshake.java:376)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:451)
	at sun.security.ssl.HandshakeContext.dispatch(HandshakeContext.java:428)
	at sun.security.ssl.TransportContext.dispatch(TransportContext.java:184)
	at sun.security.ssl.SSLTransport.decode(SSLTransport.java:154)
	at sun.security.ssl.SSLSocketImpl.decode(SSLSocketImpl.java:1198)
	at sun.security.ssl.SSLSocketImpl.readHandshakeRecord(SSLSocketImpl.java:1107)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:400)
	at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:372)
	at com.mysql.jdbc.ExportControlled.transformSocketToSSLSocket(ExportControlled.java:186)
	... 61 more

```

**解决办法**：将mybatis-config.xml文件的url的useSSL参数配置为false。


运行结果
```java
18:22:43.977 [main] DEBUG org.apache.ibatis.logging.LogFactory - Logging initialized using 'class org.apache.ibatis.logging.slf4j.Slf4jImpl' adapter.
18:22:44.006 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - PooledDataSource forcefully closed/removed all connections.
18:22:44.006 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - PooledDataSource forcefully closed/removed all connections.
18:22:44.006 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - PooledDataSource forcefully closed/removed all connections.
18:22:44.007 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - PooledDataSource forcefully closed/removed all connections.
18:22:44.103 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
18:22:44.318 [main] DEBUG org.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 1527430292.
18:22:44.318 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@5b0abc94]
18:22:44.323 [main] DEBUG com.ryan.mybatis.mapper.UserMapper.getUserList - ==>  Preparing: select * from user
18:22:44.353 [main] DEBUG com.ryan.mybatis.mapper.UserMapper.getUserList - ==> Parameters: 
18:22:44.378 [main] DEBUG com.ryan.mybatis.mapper.UserMapper.getUserList - <==      Total: 2
18:22:44.379 [main] INFO com.ryan.mybatis.UserMapperTest - User(id=1, name=小黑, pwd=123456)

18:22:44.379 [main] INFO com.ryan.mybatis.UserMapperTest - User(id=2, name=小虎, pwd=111111)

Process finished with exit code 0
```

