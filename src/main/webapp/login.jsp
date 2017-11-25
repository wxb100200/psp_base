<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <title>用户登录</title>
        <script type="text/javascript">

        </script>
        <link type="text/css" rel="stylesheet" media="all" href="styles/global.css" />
        <link type="text/css" rel="stylesheet" media="all" href="styles/global_color.css" />
    </head>
    <script src="javascripts/plugins/jquery-1.4.3.js" type="text/javascript"></script>
    <script type="text/javascript">
        <%
            String httpPort=":"+request.getLocalPort();
            String indexPage="/v2_src/index.jsp#indexpanel";
        %>
        function login(){
            var loginName=$("#loginName").val();
            var password=$("#password").val();
            var captcha=$("#captcha").val();
            if(!loginName || !password){
                alert("用户名或密码不能为空!");
                return false;
            }
            if(!captcha){
                alert("请输入验证码!");
                return false;
            }
            $.post(
                "service/login/passwordLogin",
                {"loginName":loginName,"password":password,"captcha":captcha},
                function(data){
                    if(data.success){
                        <%--alert("登录成功！");--%>
                        window.location.href="http://<%=request.getServerName()+httpPort+request.getContextPath()%><%=indexPage%>?_="+(new Date().getTime())
                    }else{
                        alert("登录失败："+data.message);
                    }
                }
            );
        }
    </script>

    <body >
        <div id="login_top"><img src="images/banner_top.gif" width="644" height="93"/></div>
        <div class="login_box">
            <table>
                <tr>
                <td class="login_info">用户名：</td>
                <td colspan="2"><input id="loginName" name="loginName" type="text" class="width150" /></td>
                <td class="login_error_info"><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                <td class="login_info">密&nbsp;码：</td>
                <td colspan="2"><input id="password" name="password" type="password" class="width150" /></td>
                <td><span class="required">30长度的字母、数字和下划线</span></td>
                </tr>
                <tr>
                <td class="login_info">验证码：</td>
                <td class="width70"><input id="captcha" name="captcha" type="text" class="width150" /></td>
                <td><img src="images/valicode.jpg" alt="验证码" title="点击更换" /></td>
                <td><span class="required">验证码错误</span></td>
                </tr>
                <tr>
                <td></td>
                <td class="login_button" colspan="2">
                <a href="index.html"><img src="images/login_btn.png" onclick="login();return false;"/></a>
                </td>
                <td><span class="required">用户名或密码错误，请重试</span></td>
                </tr>
            </table>
        </div>
    </body>
</html>