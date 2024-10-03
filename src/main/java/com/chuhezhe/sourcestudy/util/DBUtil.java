package com.chuhezhe.sourcestudy.util;

import java.sql.*;

/**
 * ClassName: DBUtil
 * Package: com.chuhezhe.sourcestudy.util
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 15:50
 * @Version 1.0
 */
public class DBUtil {
    /**
     * 获取连接
     */
    public static Connection getConnection() {
        Connection conn = null;

        try {
            // 1、数据库连接的4个基本要素
            String url = "jdbc:mysql://localhost:3306/mybatissourcestudy?useUnicode=true&serverTimezone=UTC";
            String user = "root";
            String password = "root";
            String driverName = "com.mysql.cj.jdbc.Driver";

            // 2、实例化Driver，由于java spi机制可以自动完成实例化驱动和注册驱动，所以可省略
            // 使用反射创建 Driver 实例可以防止我们的程序与mysql的程序发生耦合
//            Driver driver = Class.forName(driverName).getConstructor().newInstance();

            // 3、注册驱动，可省略
//            DriverManager.registerDriver(driver);

            conn = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * 关闭所有
     *
     * @param connection    连接
     * @param statement     sql语句的封装，可以执行封装的sql语句，获得结果
     * @param resultSet     结果集 statement获得的结果会被封装到resultSet中
     */
    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if(connection != null) {
                connection.close();
            }

            if(statement != null) {
                statement.close();
            }

            if(resultSet != null) {
                resultSet.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
