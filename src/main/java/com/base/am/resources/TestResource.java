package com.base.am.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by wxb on 2017/1/10.
 */
@Path("/test")
public class TestResource {
    @GET
    @Path("/now")
    public  String findDate(){
        return new java.util.Date().toString();
    }
}
