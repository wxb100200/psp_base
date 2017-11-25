package com.base.am.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户角色
 */
@Entity
@Table(name = Constant.DB_PREFIX+"role")
public class Role extends BaseEntity{
    private enum RoleType{
        //普通用户
        user,
        //移动用户
        mobileUser,
        //供应商用户
        supplierUser,
        //公司管理员
        companyManager,
        //系统管理员
        systemManager
    }

    /**
     *角色名称
     */
    private String roleName;

    /**
     * 角色类型
     * 使用RoleType.name
     */
    private  String roleType;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
