package module.admin;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.ModuleManager;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ServletContext servletContext;
	
	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public String loadModule() {
		String path = this.getClass().getClassLoader().getResource("module/user/module.xml").getFile();
		System.out.println(path);
		ModuleManager.registerModule(path, servletContext);
		return "loadModule";
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
