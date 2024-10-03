package com.chuhezhe.sourcestudy.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * ClassName: User
 * Package: com.chuhezhe.sourcestudy.entity
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:53
 * @Version 1.0
 */
public class User implements Serializable {
    private Integer userId;
    private String userName;
    private String nickName;
    private String email;
    private char sex;
    private String avatar;
    private String password;
    private String loginIp;
    private Date loginDate;
    private String text;

    public static final long serialVersionUID = 11239123231777123L;

    public User(Integer userId, String userName, String nickName, String email, char sex, String avatar, String password, String loginIp, Date loginDate, String text) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.sex = sex;
        this.avatar = avatar;
        this.password = password;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.text = text;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate=" + loginDate +
                ", text='" + text + '\'' +
                '}';
    }
}
