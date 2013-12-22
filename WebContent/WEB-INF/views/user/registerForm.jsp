<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>  
<div class="container">
	<h1>
		注册
	</h1>
	<div class="span-12 last">	

		<form:form modelAttribute="user" action="register" method="post">
		  	<fieldset>		
				<legend>Account Fields</legend>
				<p>
					<spring:hasBindErrors name="user">
			            <c:forEach items="${errors.globalErrors}" var="errorMessage">
			                <div id="errors" class="errors">
			                        <c:out value="${errorMessage.defaultMessage}" />
			                </div>
			            </c:forEach>
					</spring:hasBindErrors>
				</p>
				<p>
					<form:label	for="username" path="username" cssErrorClass="error">用户名</form:label><br/>
					<form:input path="username" /> <form:errors path="username" />			
				</p>
				<p>	
					<form:label for="password" path="password" cssErrorClass="error">密码</form:label><br/>
					<form:password path="password" /> <form:errors path="password" />
				</p>
				<p>
					<form:label for="rePassword" path="rePassword" cssErrorClass="error">确认密码</form:label><br/>
					<form:password path="rePassword" /> <form:errors path="rePassword" />
				</p>
				<p>	
					<form:label for="email" path="email" cssErrorClass="error">邮箱</form:label><br/>
					<form:input path="email" /> <form:errors path="email" />
				</p>
				<p>	
					<input type="submit" value="确定" />
				</p>
			</fieldset>
		</form:form>
	</div>	
</div>