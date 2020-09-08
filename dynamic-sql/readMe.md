# 动态SQL

**简介** 主要是关于Mybatis动态SQL语句的实例demo

**动态SQL** 根据不同条件进行选择不同的SQL语句，这就是动态SQL语句

#### 1. 新建博客表

```sql

```

#### 2. 搭建环境

- 2.1 配置pom.xml文件

```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.Ryan</groupId>
    <artifactId>dynamic-sql</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>dynamic-sql</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

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

        <!-- lombok依赖 -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.12</version>
            <scope>provided</scope>
        </dependency>

        <!-- log4j依赖 -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

- 2.2 配置application.properties文件

```properties

#fuyi-server
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://47.112.240.174:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true
username=root
password=MyNewPass4!

```

- 2.3 编写mybatis-config.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis配置文件-->
<configuration>
    <properties resource="application.properties"></properties>

    <settings>
        <!--开启日志打印-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <!--驼峰命名自动映射-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <typeAliases >
        <!--定义别名，没有设置alias属性，默认是类名小写开头-->
        <package name="com.ryan.configuration.entity"/>
    </typeAliases>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC">
            </transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url"
                          value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
       <mapper resource="mapper/BlogMapper.xml"/>
    </mappers>
</configuration>
```

- 2.4 编写myBatisUtils工具类

```java

package com.ryan.dynamicsql.utils;

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

- 2.5 编写博客实体类

```java
package com.ryan.dynamicsql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Blog {

    private String id;

    private String title;

    private String author;

    private Date createTime;

    private int views;

}

```

- 2.6 编写博客映射Mapper文件

```java
package com.ryan.dynamicsql.mapper;

import com.ryan.dynamicsql.entity.Blog;

import java.util.List;
import java.util.Map;


public interface BlogMapper {

    int addBlog(Blog blog);
}

```

- 2.7 映射文件多对应的xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ryan.dynamicsql.mapper.BlogMapper">


    <insert id="addBlog">
        insert into mybatis.blog(id, title, author, create_time, views) value
        (#{id,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, #{author,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, #{views})
    </insert>


</mapper>
```

- 2.8 编写测试文件，插入数据

```java
package com.ryan.dynamicsql;

import com.ryan.dynamicsql.entity.Blog;
import com.ryan.dynamicsql.mapper.BlogMapper;
import com.ryan.dynamicsql.utils.IDUtils;
import com.ryan.dynamicsql.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogTest {


    @Test
    public void addBlogTest() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blog = new ArrayList<>();
        blog.add(new Blog(IDUtils.getID(), "centos安装redis", "小黑", new Date(), 100));
        blog.add(new Blog(IDUtils.getID(), "centos安装jdk", "小黑", new Date(), 10));
        blog.add(new Blog(IDUtils.getID(), "centos安装es", "小黑", new Date(), 10883));
        blog.add(new Blog(IDUtils.getID(), "centos安装mysql", "小黑", new Date(), 190));
        blog.add(new Blog(IDUtils.getID(), "centos安装hbase", "小黑", new Date(), 900));

        for (Blog b : blog) {
            mapper.addBlog(b);
        }

        sqlSession.commit();
        sqlSession.close();
    }
}

```

- 2.9 验证是否插入成功

```sql
select * from blog;
```

**以上完成了环境的搭建，并且往数据表里面插入数据**

#### 3. 常见的动态SQL的元素种类

- if
- choose(when,otherwise)
- trim (where,set)
- foreach



