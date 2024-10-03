package com.chuhezhe.sourcestudy.service;

/**
 * ClassName: IAccountService
 * Package: com.chuhezhe.sourcestudy.service
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 18:36
 * @Version 1.0
 */
public interface IAccountService {
    Boolean transferAccount(int fromId, int toId, int money);
}
