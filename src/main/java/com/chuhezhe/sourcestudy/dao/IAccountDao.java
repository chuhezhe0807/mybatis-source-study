package com.chuhezhe.sourcestudy.dao;

/**
 * ClassName: IAccountDao
 * Package: com.chuhezhe.sourcestudy.dao
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 18:29
 * @Version 1.0
 */
public interface IAccountDao {
    Boolean updateMoney(Integer id, Integer amount);
}
