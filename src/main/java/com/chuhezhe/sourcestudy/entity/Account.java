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
    private Integer id;
    private String username;
    private Integer money;

    public static final long serialVersionUID = 102340009887654L;

    public Account(Integer id, String username, Integer money) {
        this.id = id;
        this.username = username;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
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
