<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<ul>
		<c:forEach items="${links}" var="link">
			<li><a href="<c:url value='${link.moduleLink.value}' />">${link.name }</a></li>
		</c:forEach>
	</ul>
</div>