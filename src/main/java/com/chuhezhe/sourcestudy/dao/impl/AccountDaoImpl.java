package com.chuhezhe.sourcestudy.dao.impl;

import com.chuhezhe.sourcestudy.dao.IAccountDao;
import com.chuhezhe.sourcestudy.util.DBUtilBindThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ClassName: AccountDaoImpl
 * Package: com.chuhezhe.sourcestudy.dao.impl
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 18:31
 * @Version 1.0
 */
public class AccountDaoImpl implements IAccountDao {
    @Override
    public Boolean updateMoney(Integer id, Integer amount) {
        Connection connection = DBUtilBindThread.getConnection();
        String sql = "update account set money = money + ? where id = ?";
        int rows = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, id);
            rows = preparedStatement.executeUpdate(); // 返回执行成功的行数
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return rows > 0;
    }
}
