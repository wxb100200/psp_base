package com.base.am.resources;

import com.base.am.model.Account;
import com.base.am.web.RecordsWrapperJsonProvider;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Resources的注册器
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig  extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<Class<?>>();
        result.add(TestResource.class);
        //Begin to register the restful servicer classes.
        result.add(RecordsWrapperJsonProvider.class);
        result.add(EntityResource.class);
        result.add(CompanyResource.class);
        result.add(AccountResource.class);
        result.add(LoginResource.class);
        return result;
    }
}
