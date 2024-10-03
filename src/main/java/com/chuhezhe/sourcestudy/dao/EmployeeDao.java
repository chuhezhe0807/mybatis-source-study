package com.chuhezhe.sourcestudy.dao;

import com.chuhezhe.sourcestudy.entity.Employee;

import java.util.List;

/**
 * ClassName: EmployeeDao
 * Package: com.chuhezhe.sourcestudy.dao
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:51
 * @Version 1.0
 */
public interface EmployeeDao {
    List<Employee> query();
}
