package core;

import org.springframework.web.servlet.ModelAndView;

public class ModuleContent {
	
	private String module;
	
	private ModelAndView mv;
	
	private String content;
	
	public ModuleContent() {
	}
	
	public ModuleContent(String module, ModelAndView mv) {
		this.module = module;
		this.mv = mv;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public ModelAndView getMv() {
		return mv;
	}

	public void setMv(ModelAndView mv) {
		this.mv = mv;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
