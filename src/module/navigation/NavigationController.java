package module.navigation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import annotation.NonPrimary;

@Controller
@Scope("prototype")
public class NavigationController {
	
	private NavigationService navigationService;
	
	@Autowired
	public NavigationController(NavigationService navigationService) {
		this.navigationService = navigationService;
	}
	
	@NonPrimary
	public String navigate(Model model) {
		model.addAttribute("links", this.navigationService.getAllLinks());
		return "navigation/navigate";
	}
}
