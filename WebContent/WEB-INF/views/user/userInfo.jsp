<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<div class="container">
	<div class="span-12 last">	
		<form:form modelAttribute="user" action="${user.id}" method="GET">
		  	<fieldset>		
				<legend>用户信息</legend>
				<p>
					<form:label	for="username" path="username" cssErrorClass="error">用户名</form:label><br/>
					<form:input path="username" readonly="true" /> 		
				</p>
				<p>	
					<form:label for="email" path="email" cssErrorClass="error">邮箱</form:label><br/>
					<form:input path="email" readonly="true" />
				</p>
			</fieldset>
		</form:form>
	</div>	
</div>