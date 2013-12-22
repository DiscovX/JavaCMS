package view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class MyView implements View {
	
	private String mainBlock;
	
	private String contentType;
	
	private String template;
	
	public void setMainBlock(String mainBlock) {
		this.mainBlock = mainBlock;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("mainBlock", this.mainBlock);
		request.getRequestDispatcher(this.template).forward(request, response);
	}

}
