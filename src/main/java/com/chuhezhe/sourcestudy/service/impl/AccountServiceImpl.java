package com.chuhezhe.sourcestudy.service.impl;

import com.chuhezhe.sourcestudy.dao.IAccountDao;
import com.chuhezhe.sourcestudy.dao.impl.AccountDaoImpl;
import com.chuhezhe.sourcestudy.service.IAccountService;
import com.chuhezhe.sourcestudy.util.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName: AccountServiceImpl
 * Package: com.chuhezhe.sourcestudy.service.impl
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 18:38
 * @Version 1.0
 */
public class AccountServiceImpl implements IAccountService {
    private final IAccountDao iAccountDao = new AccountDaoImpl();

    @Override
    public Boolean transferAccount(int fromId, int toId, int money) {
        // 开启事务
        Transaction.begin();

        try {
            // 需要保证一下两个updateMoney方法中用到的Connection是同一个，将他们包裹在同一个事务当中
            iAccountDao.updateMoney(fromId, -money);
            // 测试事务是否成功回滚
            // int i = 1 / 0;
            iAccountDao.updateMoney(toId, money);
            Transaction.commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            Transaction.rollback();
            return false;
        }

        return true;
    }
}
