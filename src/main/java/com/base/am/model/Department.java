package com.base.am.model;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门
 */
@Entity
@Table(name = Constant.DB_PREFIX+"department")
public class Department extends BaseEntity{
    /**
     * 部门名称
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
