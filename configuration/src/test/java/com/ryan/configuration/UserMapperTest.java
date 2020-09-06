package com.ryan.configuration;

import com.ryan.configuration.entity.User;
import com.ryan.configuration.mapper.UserMapper;
import com.ryan.configuration.utils.MybatisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        sqlSession.close();
    }

    @Test
    public void testGetUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User userById = mapper.getUserById(1);
        System.out.printf(userById.toString());
        sqlSession.close();
    }

    @Test
    public void testAddUser() {
        User u = new User(3, "小黄", "9999999");
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.addUser(u);
        if (i != 0) {
            //对于增删改，都要提交事务
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    public void testUpdateUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.updateUserById(1, "小白", "8888888");
        if (i != 0) {
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    public void testDeleteUserById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int i = mapper.deleteUserById(3);
        if (i != 0) {
            sqlSession.commit();
        }
        sqlSession.close();
    }

    @Test
    public void testUpdateById() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("userId",2);
        map.put("userName","小猪");
        map.put("newPassword","22222222");
        int i = mapper.updateById(map);
        if (i != 0) {
            sqlSession.commit();
            this.getAll();
        }
        sqlSession.close();
    }

    @Test
    public void testGetUserLike() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        List<User> userList = mapper.getUserLike("%猪%");
        List<User> userList = mapper.getUserLike("猪");
        for (User user : userList) {
            System.out.println(user.toString());
        }
        sqlSession.close();
    }

    public void getAll() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        for (User u : userList) {
            System.out.println(u.toString());
        }
    }

}
