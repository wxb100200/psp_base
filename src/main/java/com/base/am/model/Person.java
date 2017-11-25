package com.base.am.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 人员信息表
 */
@Entity
@Table(name = Constant.DB_PREFIX+"person")
public class Person extends BaseEntity{
    /**
     * 姓名
     */
    private  String name;

    /**
     * 邮箱
     */
    private  String email;

    /**
     * 手机号
     */
    private  String phoneNumber;

    /**
     * 账号
     */
    @OneToOne(mappedBy = "person")
    private Account account;

    /**
     * 公司
     */
    @ManyToOne
    private Company company;

    /**
     * 部门
     */
    @ManyToOne
    private Department department;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
