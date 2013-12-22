package core;

import org.springframework.web.method.HandlerMethod;

public class NonPrimaryModuleHandler {
	
	private String module;
	
	private HandlerMethod handlerMethod;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public HandlerMethod getHandlerMethod() {
		return handlerMethod;
	}

	public void setHandlerMethod(HandlerMethod handlerMethod) {
		this.handlerMethod = handlerMethod;
	}
}
