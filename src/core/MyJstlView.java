package core;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.JstlView;

public class MyJstlView extends JstlView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Determine which request handle to expose to the RequestDispatcher.
		HttpServletRequest requestToExpose = getRequestToExpose(request);

		// Expose the model object as request attributes.
		exposeModelAsRequestAttributes(model, requestToExpose);

		// Expose helpers as request attributes, if any.
		exposeHelpers(requestToExpose);

		// Determine the path for the request dispatcher.
		String dispatcherPath = prepareForRendering(requestToExpose, response);

		// Obtain a RequestDispatcher for the target resource (typically a JSP).
		RequestDispatcher rd = getRequestDispatcher(requestToExpose, dispatcherPath);
		if (rd == null) {
			throw new ServletException("Could not get RequestDispatcher for [" + getUrl() +
					"]: Check that the corresponding file exists within your web application archive!");
		}

		rd.include(requestToExpose, response);
	}

}
