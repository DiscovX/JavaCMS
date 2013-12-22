package core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.HandlerMethodSelector;

import annotation.NonPrimary;

public class NonPrimaryContentProvider implements InitializingBean, BeanFactoryAware {
	
	private Map<String, ArrayList<NonPrimaryModuleHandler>> regionHandlers;
	
	private PageTemplate pageTemplate;
	
	private BeanFactory beanFactory;

	public Map<String, ArrayList<NonPrimaryModuleHandler>> getRegionHandlers() {
		return regionHandlers;
	}

	public void setRegionHandlers(
			Map<String, ArrayList<NonPrimaryModuleHandler>> regionHandlers) {
		this.regionHandlers = regionHandlers;
	}

	public PageTemplate getPageTemplate() {
		return pageTemplate;
	}

	public void setPageTemplate(PageTemplate pageTemplate) {
		this.pageTemplate = pageTemplate;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		regionHandlers = new HashMap<String, ArrayList<NonPrimaryModuleHandler>>();
		ArrayList<NonPrimaryModuleHandler> handlers = null;
		Region region = pageTemplate.getMainRegion();
		handlers = getRegionHandlers(region);
		regionHandlers.put(region.getName(), handlers);
		for (Region r : pageTemplate.getOtherRegions()) {
			handlers = getRegionHandlers(r);
			regionHandlers.put(r.getName(), handlers);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	} 
	
	private ArrayList<NonPrimaryModuleHandler> getRegionHandlers(Region region) {
		ArrayList<Module> modules = region.getModules();
		ArrayList<NonPrimaryModuleHandler> handlers = new ArrayList<NonPrimaryModuleHandler>();
		NonPrimaryModuleHandler handler = null;
		for (Module module : modules) {
			String controllerName = module.getControllerName();
			try {
				Class<?> controller = Class.forName(controllerName);
				Set<Method> methods = HandlerMethodSelector.selectMethods(controller, new MethodFilter(){

					@Override
					public boolean matches(Method method) {
						NonPrimary methodAnnotation = AnnotationUtils.findAnnotation(method, NonPrimary.class);
						if (methodAnnotation != null) 
							return true;
						else
							return false;
					}
					
				});
				if (methods.size() != 1) {
					System.out.println(methods.size());
					throw new RuntimeException("module handler should only have one nonprimary method");
				}
				for (Method method : methods) {
					handler = new NonPrimaryModuleHandler();
					handlers.add(handler);
					handler.setModule(module.getName());
					handler.setHandlerMethod(new HandlerMethod(beanFactory.getBean(controller), method));
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return handlers;
	}
	
}
