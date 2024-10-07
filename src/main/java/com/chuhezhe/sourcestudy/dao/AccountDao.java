package com.chuhezhe.sourcestudy.dao;

import com.chuhezhe.sourcestudy.entity.Account;

import java.util.List;

/**
 * ClassName: AccountDao
 * Package: com.chuhezhe.sourcestudy.dao
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:42
 * @Version 1.0
 */
public interface AccountDao {
    int insert(Account account);

    List<Account> query();
}
