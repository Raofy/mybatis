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

        sqlSession.close();
    }

    @Test
    public void getAllTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = mapper.getAll();

        for (Blog b : blogs) {
            System.out.println(b);
        }

        sqlSession.close();
    }

    @Test
    public void fuzzyQueryTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = mapper.fuzzyQuery("小黑", "my");
        for (Blog b : blogs) {
            System.out.println(b);
        }
        sqlSession.close();
    }

    @Test
    public void fuzzyQueryProTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = mapper.fuzzyQueryPro("小黑", "my", 2);
        for (Blog b : blogs) {
            System.out.println(b);
        }
        sqlSession.close();
    }


    @Test
    public void chooseQueryTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blogs = mapper.chooseQuery(null, null, -2);
        for (Blog b : blogs) {
            System.out.println(b);
        }
        sqlSession.close();
    }

    @Test
    public void updateTest() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        int blogs = mapper.update("d35f37c8d3d4412ea18d06f4ec5d858e", "小白", null);
        System.out.println(blogs);
        sqlSession.close();
    }


    @Test
    public void bulkAddTest() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> blog = new ArrayList<>();
        blog.add(new Blog(IDUtils.getID(), "redis教程", "小蓝", new Date(), 180));
        blog.add(new Blog(IDUtils.getID(), "centos教程", "小黄", new Date(), 510));
        blog.add(new Blog(IDUtils.getID(), "es教程", "小紫", new Date(), 1883));
        blog.add(new Blog(IDUtils.getID(), "mysql教程", "小红", new Date(), 1990));
        blog.add(new Blog(IDUtils.getID(), "hbase教程", "小青", new Date(), 90078));

        mapper.bulkAdd(blog);
        sqlSession.close();
    }

}
