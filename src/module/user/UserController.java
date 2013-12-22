package module.user;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import annotation.NonPrimary;

@Controller
@Scope("prototype")
@RequestMapping(value = "/user")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model) {
		model.addAttribute(new User());
		return "user/loginForm";
	}	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, BindingResult result, HttpSession session) {
		user = this.userService.check(user);
		if (user != null) {
			//session add user
			session.setAttribute("user", user);
			return "redirect:/user/" + user.getId();
		} else {
			//output errors
			result.reject("usernameOrPasswordWrong", "用户名或密码错误");
			return "user/loginForm";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "logout";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@NonPrimary
	public String registerForm(Model model) {
		model.addAttribute(new User());
		return "user/registerForm";
	}	
	
	//validate
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return "user/registerForm";
		}
		try {
			this.userService.register(user);
		} catch (UserNameExistedException e) {
//			ObjectError error = new ObjectError("user.", );
//			FieldError er = new FI
			//result.rejectValue("usernameExisted", null, "用户名已存在");
			//output errors
			//result.addError(new ObjectError("usernameExisted", "用户名已存在"));
			result.reject("usernameExisted", "用户名已存在");
			return "user/registerForm";
		}
		//此处使用了重定向， 需要修改response状态，否则会抛出异常
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable Long id, Model model) {
		User user = this.userService.getUser(id);
		model.addAttribute(user);
		return "/user/userInfo";
	}
}
