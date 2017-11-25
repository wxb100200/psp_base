package com.base.am.resources;

import com.base.am.model.Account;
import com.base.am.util.EbeanUtil;
import com.base.am.util.Logger;
import com.sun.jersey.api.representation.Form;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/account{uriOptions:(:[^/]+?)?}/")
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8","text/json;charset=utf-8" })
public class AccountResource {
    private static Logger log=Logger.getLogger(CompanyResource.class);

    @POST
    @Path("/addAccount")
    public PagableRecordsWrapper addCompany(Form form, @Context HttpServletRequest request){
        String personName=form.getFirst("personName");
        String phoneNumber=form.getFirst("phoneNumber");
        String email=form.getFirst("email");
        String loginName=form.getFirst("loginName");
        int rowNum= EbeanUtil.find(Account.class).where().eq("loginName",loginName).findRowCount();
        if(rowNum>0){
            log.info("该账号已经注册loginName:"+loginName);
            return PagableRecordsWrapper.error(500,loginName+"账号已经被注册");
        }
        Account a=new Account();
        return  PagableRecordsWrapper.single(a);


    }

}
