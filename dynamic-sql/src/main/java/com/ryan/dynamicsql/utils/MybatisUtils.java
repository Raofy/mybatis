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
        return factory.openSession(true);
    }
}
