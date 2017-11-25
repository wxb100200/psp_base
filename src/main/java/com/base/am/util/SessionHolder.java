package com.base.am.util;

import com.base.am.model.Person;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxb on 2017/3/20.
 */
public class SessionHolder {

    final static String COOKIE_HEAD="_BASE_";
    final static String PERSON_ID = "PID";  //用户ID的cookie key
    final static String ORIGINAL_PERSON_ID = "OPID"; // 如果是模拟用户，本key指定了模拟前原来的用户，被模拟的用户存在PID中。

    private static ThreadLocal<Person> person = new ThreadLocal<Person>();
    private static ThreadLocal<Person> originalPerson = new ThreadLocal<Person>();
    private static ThreadLocal<Map<String,String>> cookieMap=new ThreadLocal<Map<String, String>>();

    private SessionHolder(){}
    public static void init(HttpServletRequest request){
        Map<String,String> map=parseRequest(request);
        cookieMap.set(map);
        person.remove();
        originalPerson.remove();
    }
    private static Map<String,String> parseRequest(HttpServletRequest request){
        Map<String,String> map=new HashMap<String, String>();
        Cookie[] cookies=request.getCookies();
        for(Cookie c:cookies){
            String cookieName=c.getName();
            if(!cookieName.startsWith(COOKIE_HEAD)) continue;
            String name=cookieName.substring(COOKIE_HEAD.length());
            String value=c.getValue();
            map.put(name,value);
        }
        return map;
    }

    public static void setCurrentPerson(Person p){
        person.set(p);
        Map<String,String> map=getOrCreateCookieMap();
        if(p==null){
            map.remove(PERSON_ID);
        }else{
            map.put(PERSON_ID,""+p.getId());
        }

    }
    private static Map<String,String> getOrCreateCookieMap(){
        Map<String,String> map=cookieMap.get();
        if(map==null || map.size()<=0){
            map=new HashMap<String, String>();
            cookieMap.set(map);
            return map;
        }else{
            return map;
        }
    }
}
