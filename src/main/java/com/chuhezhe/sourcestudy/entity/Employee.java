package com.chuhezhe.sourcestudy.entity;

import java.io.Serializable;

/**
 * ClassName: Employee
 * Package: com.chuhezhe.sourcestudy.entity
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2024/10/3 22:52
 * @Version 1.0
 */
public class Employee implements Serializable {
    private Integer id;
    private String name;
    private Integer deptId;

    public static final long serialVersionUID = 202203009123773L;

    public Employee(Integer id, String name, Integer deptId) {
        this.id = id;
        this.name = name;
        this.deptId = deptId;
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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}
