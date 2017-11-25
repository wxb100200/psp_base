<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<html>
<head>
    <title>404错误</title>
    <script type="text/javascript">
        function showErrorMessage(){
            document.getElementById('errorView').innerHTML='<%=null==exception?"unknown":exception.getMessage()%>';
        }
    </script>
</head>
<body>
<center>
<br /><br /><br /><br /><br /><br />
<div class="alert_conntent"  id="errorView" ondblclick="showErrorMessage()">
<table>
	<tr>
		<td class="alert_error" style="background: url(images/Alert_01.png) no-repeat;" ></td>
		<td >
			<strong> <s:text name="tips.name" /></strong>
			很抱歉，您要访问的页面不存在。
		</td>
	</tr>
</table>
</div>
</center>

</body>
</html>
