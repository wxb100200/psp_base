package com.base.am.resources;

import com.avaje.ebean.Ebean;
import com.base.am.model.Account;
import com.base.am.model.BaseEntity;
import com.base.am.util.EbeanUtil;
import com.base.am.util.Logger;
import com.base.am.util.SessionHolder;
import com.sun.jersey.api.representation.Form;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面登录
 */
@Path("/login{uriOptions:(:[^/]+?)?}/")
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8","text/json;charset=utf-8" })
public class LoginResource {
    private static Logger log=Logger.getLogger(CompanyResource.class);

    @POST
    @Path("/passwordLogin")
    public PagableRecordsWrapper passwordLogin(Form form, @Context HttpServletRequest request){
        String loginName=form.getFirst("loginName");
        String password=form.getFirst("password");
        String captcha=form.getFirst("captcha");
        Account a= EbeanUtil.find(Account.class).where().eq("loginName",loginName).setMaxRows(1).findUnique();
        if(a==null){
            return PagableRecordsWrapper.error(500,"用户名或密码错误");
        }
        return checkLogin(a,password);
    }
    private PagableRecordsWrapper checkLogin(Account a,String password){
        Long lockTime=a.getLockTime();
        Long now=System.currentTimeMillis();
        Long longTime=(lockTime+ 60 * 60 * 3600)-now;
        //表示临时锁定
        if(lockTime==-1){
            return PagableRecordsWrapper.error(500,"临时锁定："+a.getLockReason());
        }
        if(lockTime!=0 && longTime>=0){
            return PagableRecordsWrapper.error(500,a.getLockReason()+"，请于"+longTime/3600/60+"分钟后再试");
        }
        return  checkPassword(a,password,now);
    }
    private PagableRecordsWrapper  checkPassword(Account a,String password,Long now){
        try {
            Ebean.beginTransaction();
            if(a.checkPassword(password)){
                //登录成功
                SessionHolder.setCurrentPerson(a.getPerson());

                a.setLockTime(0L);
                a.setLoginFailNumber(0);
                a.setLockReason(null);
                Ebean.save(a);
                Ebean.commitTransaction();
                return PagableRecordsWrapper.single(a);
            }
            //登录失败
            int failNumber=a.getLoginFailNumber();
            if(failNumber>=4){
                //失败次数太多，进行锁定
                a.setLoginFailNumber(failNumber+1);
                a.setLockTime(now);
                a.setLockReason("错误登录次数太多");
                Ebean.save(a);
                Ebean.commitTransaction();
                return PagableRecordsWrapper.error(500,"错误登录失败次数太多，请于一小时后再试！");
            }
            a.setLoginFailNumber(failNumber+1);
            Ebean.save(a);
            Ebean.commitTransaction();
            return PagableRecordsWrapper.error(500,"输入用户名或密码有误,还剩:"+(5-a.getLoginFailNumber())+"次机会！");
        }catch (Exception e){
            log.error("LoginResource passwordLogin fail",e);
            Ebean.rollbackTransaction();
            return PagableRecordsWrapper.error(500,"登录系统失败！");
        }finally {
            Ebean.endTransaction();
        }
    }
}














