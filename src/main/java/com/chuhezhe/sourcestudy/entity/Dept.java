package com.chuhezhe.sourcestudy.entity;

import java.io.Serializable;

/**
 * ClassName: Dept
 * Package: com.chuhezhe.sourcestudy.entity
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:51
 * @Version 1.0
 */
public class Dept implements Serializable {
    private Integer id;
    private String name;

    public static final long serialVersionUID = 1020009123777123L;

    public Dept(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
