<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css' />" type="text/css" media="screen">
<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.9.1.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
</head>
<body>
	welcome you!
	<a class="btn btn-primary" href="<c:url value='/user/login' />">登陆</a>
	<a class="btn btn-primary" href="<c:url value='/user/register' />">注册</a>
</body>
</html>