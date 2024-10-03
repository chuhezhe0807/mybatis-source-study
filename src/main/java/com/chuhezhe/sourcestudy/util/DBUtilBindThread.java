package com.chuhezhe.sourcestudy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ClassName: DBUtilBindThread
 * Package: com.chuhezhe.sourcestudy.util
 * Description: 将事务与线程绑定 针对DBUtil中的方法使用线程绑定Connection，确保连接是同一个，可以测试事务
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 15:50
 * @Version 1.0
 */
public class DBUtilBindThread {
    // 使用ThreadLocal绑定connection和当前线程
    public static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();

    public static Connection getConnection() {
        // 首先从threadLocal中获取
        Connection conn = THREAD_LOCAL.get();
        // 如果没有就创建
        if(conn == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/mybatissourcestudy?useUnicode=true&serverTimezone=UTC";
                String user = "root";
                String password = "root";
                conn = DriverManager.getConnection(url, user, password);

                if(conn == null) {
                    throw new RuntimeException("连接获取异常!");
                }

                // 创建完成后，加入threadLocal
                THREAD_LOCAL.set(conn);
            }
            catch(SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("连接过去异常!");
            }
        }

        return conn;
    }
}
