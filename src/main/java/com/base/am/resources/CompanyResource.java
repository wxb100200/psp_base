package com.base.am.resources;

import com.avaje.ebean.Ebean;
import com.base.am.model.Account;
import com.base.am.model.Person;
import com.base.am.model.RecordStatus;
import com.base.am.util.StringUtil;
import com.sun.jersey.api.representation.Form;
import com.base.am.model.Company;
import com.base.am.util.EbeanUtil;
import com.base.am.util.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/company{uriOptions:(:[^/]+?)?}/")
@Produces({ MediaType.APPLICATION_JSON + ";charset=utf-8","text/json;charset=utf-8" })
public class CompanyResource {
    private static Logger log=Logger.getLogger(CompanyResource.class);

    public static void main(String[] args){
        Account a=EbeanUtil.find(Account.class,1l);
        System.out.println(a.getLoginName());
    }

    @DELETE
    @Path("/deleteCompany/{companyId}")
    public PagableRecordsWrapper deleteCompany(@PathParam("companyId") String companyId){
        Company company=EbeanUtil.find(Company.class,companyId);
        if(company==null){
            log.info("没有找到该公司companyId："+companyId);
            return PagableRecordsWrapper.error(500,"没有找到该公司companyId:"+companyId);
        }else {
            int rowNum=EbeanUtil.find(Person.class).where().eq("company.id",companyId).findRowCount();
            if(rowNum>0){
                log.info(company.getName()+"下有人员，不能删除");
                return PagableRecordsWrapper.error(500,company.getName()+"下有人员，不能删除");
            }
            try{
                Ebean.beginTransaction();
                company.setRecordStatus(RecordStatus.Invalid);
                Ebean.save(company);
                Ebean.commitTransaction();
                return PagableRecordsWrapper.single(company);
            } catch (Exception e) {
                Ebean.rollbackTransaction();
                return PagableRecordsWrapper.error(500, e);
            } finally {
                Ebean.endTransaction();
            }
        }

    }

    @POST
    @Path("/addOrModifyCompany")
    public PagableRecordsWrapper addCompany(Form form, @Context HttpServletRequest request){
        String companyId=form.getFirst("companyId");
        String companyName=form.getFirst("companyName");
        String companyType=form.getFirst("companyType");
        String companyAddress=form.getFirst("companyAddress");
        Company temp=new Company();
        temp.setName(companyName);
        temp.setType(companyType);
        temp.setAddress(companyAddress);
        if(StringUtil.isEmpty(companyId)){
            //新增公司
            return addCompany(temp);
        }else{
            temp.setId(Long.valueOf(companyId));
            //修改公司
            return modifyCompany(temp);
        }
    }
    private static PagableRecordsWrapper addCompany(Company temp){
        int rowCount=EbeanUtil.find(Company.class).where().eq("name",temp.getName()).findRowCount();
        if(rowCount>0){
            log.info("已经存在公司："+temp.getName());
            return PagableRecordsWrapper.error(500,"已存在公司:"+temp.getName());
        }
        try{
            Ebean.beginTransaction();
            Company company=new Company();
            company.setName(temp.getName());
            company.setType(temp.getType());
            company.setAddress(temp.getAddress());
            Ebean.save(company);
            Ebean.commitTransaction();
            return PagableRecordsWrapper.single(company);
        } catch (Exception e) {
            Ebean.rollbackTransaction();
            return PagableRecordsWrapper.error(500, e);
        } finally {
            Ebean.endTransaction();
        }
    }
    private static PagableRecordsWrapper modifyCompany(Company temp){
        Company company=EbeanUtil.find(Company.class,temp.getId());
        if(company==null){
            log.info("没有找到要修改的公司 companyId："+temp.getId());
            return PagableRecordsWrapper.error(500,"没有找到要修改的公司:"+temp.getName());
        }
        try{
            Ebean.beginTransaction();
            company.setType(temp.getType());
            company.setAddress(temp.getAddress());
            Ebean.update(company);
            Ebean.commitTransaction();
            return PagableRecordsWrapper.single(company);
        } catch (Exception e) {
            Ebean.rollbackTransaction();
            return PagableRecordsWrapper.error(500, e);
        } finally {
            Ebean.endTransaction();
        }
    }
}
