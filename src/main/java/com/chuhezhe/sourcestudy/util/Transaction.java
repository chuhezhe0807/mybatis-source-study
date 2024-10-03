package com.chuhezhe.sourcestudy.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ClassName: Transaction
 * Package: com.chuhezhe.sourcestudy.util
 * Description: 事务
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 15:50
 * @Version 1.0
 */
public class Transaction {
    /**
     * 开启事务
     */
    public static void begin() {
        Connection connection = DBUtilBindThread.getConnection();

        try {
            // 开启事务的核心
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public static void commit() {
        Connection connection = DBUtilBindThread.getConnection();

        try {
            connection.commit();
            close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        Connection connection = DBUtilBindThread.getConnection();

        try {
            connection.rollback();
            close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭事务
     */
    public static void close() {
        Connection connection = DBUtilBindThread.getConnection();

        try {
            // 回复现场
            connection.setAutoCommit(true);
            // 原生的close会关闭连接，DataSource中的close是将连接归还到数据源
            connection.close();
            // 关闭连接之后在删除线程中的连接
            DBUtilBindThread.THREAD_LOCAL.remove();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
