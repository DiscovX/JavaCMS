<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE>
<html>
	<head>
		<link rel="stylesheet" href="<c:url value='/resources/template/css/style.css' />" type="text/css" media="screen">
	</head>
	<body>
		<div id="header">
			${requestScope.header}
		</div>
		<div id="content">
			<div id="left">${requestScope.left}</div>
			<div id="middle">${requestScope.mainRegion}</div>
			<div id="right">${requestScope.right}</div>
		</div>
		<div id="footer">
			${requestScope.footer}
		</div>
	</body>
</html>