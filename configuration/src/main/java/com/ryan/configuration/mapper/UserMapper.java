package com.ryan.configuration.mapper;

import com.ryan.configuration.entity.User;

import java.util.List;
import java.util.Map;


public interface UserMapper {
    List<User> getUserList();

    User getUserById(int id);

    int addUser(User user);

    int updateUserById(int id, String name, String psw);

    int deleteUserById(int id);

    int updateById(Map map);

    List<User> getUserLike(String value);
}
