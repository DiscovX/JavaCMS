package org.springframework.web.method;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.Resource;

import util.DBUtils;

public class MyHandlerMethod extends HandlerMethod {
	
	private Resource resource;
	
	private BeanFactory beanFactory;

	/*
	public MyHandlerMethod(Object bean, Method method) {
		super(bean, method);
	}*/
	
	public MyHandlerMethod(MyHandlerMethod handlerMethod, Object handler) {
		super(handler, handlerMethod.getMethod());
		this.resource = handlerMethod.getResource();
	}

	public MyHandlerMethod(String beanName, BeanFactory beanFactory,
			Method method) {
		super(beanName, beanFactory, method);
		this.beanFactory = beanFactory;
		setResource(method, beanFactory.getType(beanName));
	}

	@Override
	public HandlerMethod createWithResolvedBean() {
		Object handler = getBean();
		if (handler instanceof String) {
			String beanName = (String) handler;
			handler = this.beanFactory.getBean(beanName);
		}
		return new MyHandlerMethod(this, handler);
	}

	public Resource getResource() {
		return resource;
	}

	private void setResource(Method method, Class<?> handler) {
		this.resource = new Resource();
		setResourceUrlAndMethod(method, handler);
		setResourceFromDBByUrlAndMethod();
	}
	
	private void setResourceUrlAndMethod(Method method, Class<?> handler) {
		RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		RequestMapping classAnnotation = AnnotationUtils.findAnnotation(handler, RequestMapping.class);
		String[] methodUrls = methodAnnotation.value();	
		String[] classUrls = classAnnotation.value();
		resource.setUrl(classUrls[0] + methodUrls[0]);
		
		RequestMethod[] methods = methodAnnotation.method();
		switch (methods[0]) {
			case GET:
				resource.setMethod(0);
				break;
			case POST:
				resource.setMethod(1);
				break;
			case PUT:
				resource.setMethod(2);
				break;
			case DELETE:
				resource.setMethod(3);
				break;
		}
	}
	
	private void setResourceFromDBByUrlAndMethod() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "select id, module_id, is_private from resource where url=? and method=?";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, resource.getUrl());
			stmt.setInt(2, resource.getMethod());
			rs = stmt.executeQuery();
			if (rs.next()) {
				resource.setId(rs.getInt(1));
				resource.setModuleId(rs.getInt(2));
				int isPrivate = rs.getInt(3);
				if (isPrivate == 0)
					resource.setPrivate(false);
				else
					resource.setPrivate(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtils.close(conn, stmt, rs);
		}
	}
	
}
