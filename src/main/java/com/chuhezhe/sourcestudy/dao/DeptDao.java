package com.chuhezhe.sourcestudy.dao;

import com.chuhezhe.sourcestudy.entity.Dept;

import java.util.List;

/**
 * ClassName: DeptDao
 * Package: com.chuhezhe.sourcestudy.dao.impl
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:50
 * @Version 1.0
 */
public interface DeptDao {
    List<Dept> query();
}
