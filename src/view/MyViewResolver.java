package view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class MyViewResolver implements ViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {
		MyView view = new MyView();
		view.setContentType("text/html;charset=utf-8");
		view.setMainBlock(viewName);
		view.setTemplate("/template/template.jsp");
		return view;
	}
}
