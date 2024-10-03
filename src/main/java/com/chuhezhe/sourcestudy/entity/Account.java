package com.chuhezhe.sourcestudy.entity;

import java.io.Serializable;

/**
 * ClassName: Account
 * Package: com.chuhezhe.sourcestudy.entity
 * Description: 账户实体类
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 16:38
 * @Version 1.0
 */
public class Account implements Serializable {
    private int id;
    private String username;
    private int money;

    public static final long serialVersionUID = 102340009887654L;

    public Account(int id, String username, int money) {
        this.id = id;
        this.username = username;
        this.money = money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", money=" + money +
                '}';
    }
}
