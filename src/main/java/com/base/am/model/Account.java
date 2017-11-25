package com.base.am.model;

import cn.hillwind.common.util.RandomUtil;
import com.base.am.util.EncryptUtil;
import com.base.am.util.Logger;
import com.base.am.util.StringUtil;

import javax.persistence.*;
import java.util.List;

/**
 * 账号
 */
@Entity
@Table(name = Constant.DB_PREFIX+"account")
public class Account extends BaseEntity{

    /**
     * 登录账号
     * 不可重复，不能为中文，大于4个字符
     */
    private String loginName;

    /**
     * 加密后的密码
     */
    private String password;
    /**
     * 随机码
     */
    private String salt;

    /**
     * 是否第一次登录标记
     */
    private Boolean firstLogin;

    /**
     *锁定时间
     */
    private Long lockTime= 0L;

    /**
     * 锁定原因
     */
    private String lockReason;

    /**
     *登录失败次数
     */
    private Integer loginFailNumber;

    /**
     * 拥有的角色权限
     */
    @ManyToMany
    @JoinTable(name = Constant.DB_PREFIX+"account_role",
        joinColumns = @JoinColumn(name="account_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private List<Role> roleList;

    /**
     *人员
     */
    @OneToOne
    private Person person;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    public String getLockReason() {
        return lockReason;
    }

    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    public Integer getLoginFailNumber() {
        return loginFailNumber;
    }

    public void setLoginFailNumber(Integer loginFailNumber) {
        this.loginFailNumber = loginFailNumber;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 检查密码是否正确
     */
    public boolean checkPassword(String password){
        if(StringUtil.isEmpty(this.getSalt())){
            return password.equals(this.getPassword());
        }else{
            return checkPasswordSalt(password);
        }
    }
    private boolean checkPasswordSalt(String password){
        try {
            return this.password.equals(EncryptUtil.md5(password));
        }catch (Exception e){
            log.error("密码检查错误，password:"+password+",salt:"+salt,e);
        }
        return false;
    }

    /**
     * 新密码或重置密码
     */
/*    public Boolean resetPassword(String newPassword){
        try{
            this.setChangeTime(System.currentTimeMillis());
            this.setSalt(RandomUtil.getString(20)); // 重置密码时，需要重新生产salt.
            this.password =new String( BASE64Codec.encode(md5.digest((this.salt + newPassword).getBytes("utf-8"))) );
            this.setSalt(RandomUtil.getString(20));
            return true;
        }catch (Exception e){

        }
        return false;
    }*/

    /**
     * 判断密码是否正确
     */
}
