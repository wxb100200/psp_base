<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	response.setStatus(HttpServletResponse.SC_OK);
%>
<html>
<head>
    <title></title>
    <script type="text/javascript">
        function showErrorMessage(){
           document.getElementById('errorView').hidden=false;
        }
    </script>
</head>
<body>
<center>
<br /><br /><br /><br /><br /><br />
<div class="alert_conntent" ondblclick="showErrorMessage()">
<table>
	<tr>
		<td class="alert_error" style="background: url(images/Alert_01.png) no-repeat;" ></td>
		<td>
			<strong> <s:text name="tips.name" /></strong>
			很抱歉，系统内部错误，请与管理员联系。
		</td>
	</tr>
</table>
</div>
    <pre hidden="true" id="errorView">
        <%=exception.getMessage()%>
    </pre>
</center>

</body>
</html>
