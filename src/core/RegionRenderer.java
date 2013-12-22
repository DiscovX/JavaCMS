package core;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class RegionRenderer extends SimpleTagSupport{

	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		PageContext pageContext = (PageContext) this.getJspContext();
		JspWriter out = this.getJspContext().getOut();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		try {
			ResponseWrapper responseWrapper = new ResponseWrapper(response);
			request.getRequestDispatcher("/test.jsp").include(request, responseWrapper);
			out.write(responseWrapper.getContent());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		//WebApplicationContext applicationContext = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		
		ArrayList<String> blocks = RegionBlocksMetaInfoReader.read(this.name, servletContext);
		if (blocks != null && blocks.size() != 0) {
			for (int i = 0;i < blocks.size();i++) {
				BlockRender br = (BlockRender) applicationContext.getBean(blocks.get(i));
				out.write(br.render());
			}
		}
	}
	
}
