package com.base.am.web;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class RecordsWrapperJsonProvider extends AbstractProvider<RecordsWrapper> {

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return RecordsWrapper.class.isAssignableFrom(aClass);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return RecordsWrapper.class.isAssignableFrom(aClass);
    }

    @Override
    public void writeTo(RecordsWrapper recordsWrapper, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> stringObjectMultivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
        String str;
        try{
            str = recordsWrapper.toJson();
        }catch (Exception exp){
//            str = "{\"success\":false,\"message\":\"" + PagableRecordsWrapper.escape(IOUtil.getErrorStackTrace(exp)) + "\"}";
            str = "{\"success\":false,\"message\":\"错误（鉴于安全原因，仅简要列出错误类型，详细错误请查看系统日志）:" + exp.getClass().getName() + "\"}";
        }
        writer.write(str);
        writer.flush();
    }
}
