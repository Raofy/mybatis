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
