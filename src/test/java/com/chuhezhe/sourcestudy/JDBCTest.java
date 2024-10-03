package com.chuhezhe.sourcestudy;

import com.chuhezhe.sourcestudy.entity.Account;
import com.chuhezhe.sourcestudy.service.impl.AccountServiceImpl;
import com.chuhezhe.sourcestudy.util.DBUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ClassName: JDBCTest
 * Package: com.chuhezhe.sourcestudy
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 16:30
 * @Version 1.0
 */
public class JDBCTest {
    public static Logger logger = LoggerFactory.getLogger(JDBCTest.class);

    @Test
    public void testJdbc() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Account> accounts = new ArrayList<>();

        try {
            // 1、获取一个连接
            connection = DBUtil.getConnection();

            if(connection == null) {
                return;
            }

            // 2、获取一个statement
            String sql = "select `id`, `username`, `money` from `account` where `username` = ?";
            preparedStatement = connection.prepareStatement(sql);

            // 3、占位符替换
            preparedStatement.setString(1, "王五");

            // 4、执行sql
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                int money = resultSet.getInt("money");
                Account account = new Account(id, username, money);
                accounts.add(account);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            DBUtil.closeAll(connection, preparedStatement, resultSet);
        }

        logger.info("accounts: {}", accounts);
    }

    @Test
    public void testTransfer() {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.transferAccount(1, 2, 200);
    }
}
