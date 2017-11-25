package com.base.am.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 公司
 */
@Entity
@Table(name = Constant.DB_PREFIX+"company")
public class Company extends BaseEntity{
    public enum CompanyType{
        //供应商
        supplier,
        //中国移动
        chinaMobile,
        //设计公司
        design,
        //施工公司
        construction,
        //监理公司
        supervision,
        //审计公司
        audit,
        //其他
        other
    }

    /**
     * 公司类型
     * 使用CompanyType.name
     */
    private String type;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司曾用名
     */
    private String formerName;

    /**
     * 公司地址
     */
    private String address;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormerName() {
        return formerName;
    }

    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
