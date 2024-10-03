package com.chuhezhe.sourcestudy.dao;

import com.chuhezhe.sourcestudy.entity.User;

import java.util.List;

/**
 * ClassName: UserDao
 * Package: com.chuhezhe.sourcestudy.dao
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:52
 * @Version 1.0
 */
public interface UserDao {
    List<User> query();
}
