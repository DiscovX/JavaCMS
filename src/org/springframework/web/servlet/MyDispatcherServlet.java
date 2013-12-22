package org.springframework.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import module.user.User;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.method.MyHandlerMethod;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.NestedServletException;
import org.springframework.web.util.WebUtils;

import core.Authenticator;
import core.ModuleContent;
import core.NonPrimaryContentProvider;
import core.NonPrimaryModuleHandler;
import core.PageContent;
import core.PageTemplate;
import core.Region;
import core.RegionContent;
import core.ResponseWrapper;

public class MyDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = -8553493396078626794L;

	@Override
	protected void doDispatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationContext ctx = this.getWebApplicationContext();
		PageTemplate pageTemplate = (PageTemplate) ctx.getBean("pageTemplate");
		NonPrimaryContentProvider npcp = (NonPrimaryContentProvider) ctx.getBean("nonPrimaryContentProvider");
		PageContent pageContent = null;
		RegionContent regionContent = null;
		ModuleContent moduleContent = null;

		HttpServletRequest processedRequest = request;
		HttpSession session = request.getSession();
		HandlerExecutionChain mappedHandler = null;
		boolean multipartRequestParsed = false;

		WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

		try {
			ModelAndView mv = null;
			Exception dispatchException = null;
			boolean requestResource = false;

			try {
				processedRequest = checkMultipart(request);
				multipartRequestParsed = processedRequest != request;

				// Determine handler for the current request.
				mappedHandler = getHandler(processedRequest, false);
				if (mappedHandler == null || mappedHandler.getHandler() == null) {
					noHandlerFound(processedRequest, response);
					return;
				}
				
				Object springHandler = mappedHandler.getHandler();
				// Determine handler adapter for the current request.
				HandlerAdapter ha = getHandlerAdapter(springHandler);
				/*
				// Process last-modified header, if supported by the handler.
				String method = request.getMethod();
				boolean isGet = "GET".equals(method);
				if (isGet || "HEAD".equals(method)) {
					long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
					if (logger.isDebugEnabled()) {
						String requestUri = urlPathHelper.getRequestUri(request);
						logger.debug("Last-Modified value for [" + requestUri + "] is: " + lastModified);
					}
					if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
						return;
					}
				}
				*/
				if (!mappedHandler.applyPreHandle(processedRequest, response)) {
					return;
				}
				
				requestResource = springHandler instanceof ResourceHttpRequestHandler;
				try {
					if (!requestResource) {  //Resourse must exist
						MyHandlerMethod myHandler = (MyHandlerMethod) springHandler;
						WebApplicationContext context = getWebApplicationContext();
						
						//authentication
						Authenticator authenticator = (Authenticator) context.getBean("authenticator");
						User user = (User) session.getAttribute("user");
						if (!authenticator.authenticate(myHandler.getResource(), user)) {
							// output no privilege
						}
						
						// Actually invoke the handler.
						mv = ha.handle(processedRequest, response, myHandler);
						
						//invoke other module handler
						pageContent = new PageContent();
						Map<String, RegionContent> regions = new HashMap<String, RegionContent>();
						pageContent.setRegions(regions);
						for (Map.Entry<String, ArrayList<NonPrimaryModuleHandler>> entry : npcp.getRegionHandlers().entrySet()) {
							regionContent = new RegionContent();
							ArrayList<ModuleContent> modules = new ArrayList<ModuleContent>();
							regionContent.setModules(modules);
							regions.put(entry.getKey(), regionContent);
							for (NonPrimaryModuleHandler handler : entry.getValue()) {
								moduleContent = new ModuleContent();
								modules.add(moduleContent);
								moduleContent.setModule(handler.getModule());
								moduleContent.setMv(ha.handle(processedRequest, response, handler.getHandlerMethod()));
							}
						}
						
						//add main module content to the main region
						Region mainRegion = pageTemplate.getMainRegion();
						regions.get(mainRegion.getName()).getModules().add(new ModuleContent("user", mv));
					} else { //Resourse may not exist
						mv = ha.handle(processedRequest, response, springHandler);
					}
				}
				finally {
					if (asyncManager.isConcurrentHandlingStarted()) {
						return;
					}
				}
				applyDefaultViewName(request, mv);
				mappedHandler.applyPostHandle(processedRequest, response, mv);
				
				if (requestResource)
					processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
				else
					processDispatchResult(pageContent, pageTemplate, processedRequest, response);				
			}
			catch (Exception ex) {
				dispatchException = ex;
				ex.printStackTrace();
			}
		}
		catch (Exception ex) {
			triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
		}
		catch (Error err) {
			triggerAfterCompletionWithError(processedRequest, response, mappedHandler, err);
		}
		finally {
			if (asyncManager.isConcurrentHandlingStarted()) {
				// Instead of postHandle and afterCompletion
				mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
				return;
			}
			// Clean up any resources used by a multipart request.
			if (multipartRequestParsed) {
				cleanupMultipart(processedRequest);
			}
		}
	}
	
	/**
	 * Handle the result of handler selection and handler invocation, which is
	 * either a ModelAndView or an Exception to be resolved to a ModelAndView.
	 */
	private void processDispatchResult(PageContent pageContent, PageTemplate pageTemplate, HttpServletRequest request, HttpServletResponse response) {
		//render(mv, request, response);
		RegionContent region;
		ResponseWrapper responseWrapper = null;
		Map<String, RegionContent> regions = pageContent.getRegions();
		System.out.println("start render");
		for (Map.Entry<String, RegionContent> entry : regions.entrySet()) {
			region = entry.getValue();
			for (ModuleContent module : region.getModules()) {
				responseWrapper = new ResponseWrapper(response);
				responseWrapper.setContentType("text/html;charset=utf-8");
				try {
					render(module.getMv(), request, responseWrapper);
					String content = responseWrapper.getContent();
					module.setContent(content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			StringBuilder sb = new StringBuilder();
			for (ModuleContent module : region.getModules()) {
				sb.append(module.getContent());
			}
			region.setContent(sb.toString());
			request.setAttribute(entry.getKey(), region.getContent());
		}
		String template = "/WEB-INF/template/" + pageTemplate.getName() + ".jsp";
		try {
			request.getRequestDispatcher(template).forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end render");
	}
	
	private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
			HandlerExecutionChain mappedHandler, ModelAndView mv, Exception exception) throws Exception {

		boolean errorView = false;

		if (exception != null) {
			if (exception instanceof ModelAndViewDefiningException) {
				logger.debug("ModelAndViewDefiningException encountered", exception);
				mv = ((ModelAndViewDefiningException) exception).getModelAndView();
			}
			else {
				Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
				mv = processHandlerException(request, response, handler, exception);
				errorView = (mv != null);
			}
		}

		// Did the handler return a view to render?
		if (mv != null && !mv.wasCleared()) {
			render(mv, request, response);
			if (errorView) {
				WebUtils.clearErrorRequestAttributes(request);
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Null ModelAndView returned to DispatcherServlet with name '" + getServletName() +
						"': assuming HandlerAdapter completed request handling");
			}
		}

		if (WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
			// Concurrent handling started during a forward
			return;
		}

		if (mappedHandler != null) {
			mappedHandler.triggerAfterCompletion(request, response, null);
		}
	}	
	
	@Override
	protected void render(ModelAndView mv, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Determine locale for request and apply it to the response.
		/*
		Locale locale = this.localeResolver.resolveLocale(request);
		response.setLocale(locale);
		*/
		View view;
		if (mv.isReference()) {
			// We need to resolve the view name.
			view = resolveViewName(mv.getViewName(), mv.getModel(), null, request);
			if (view == null) {
				throw new ServletException(
						"Could not resolve view with name '" + mv.getViewName() + "' in servlet with name '" +
								getServletName() + "'");
			}
		}
		else {
			// No need to lookup: the ModelAndView object contains the actual View object.
			view = mv.getView();
			if (view == null) {
				throw new ServletException("ModelAndView [" + mv + "] neither contains a view name nor a " +
						"View object in servlet with name '" + getServletName() + "'");
			}
		}

		// Delegate to the View object for rendering.
		if (logger.isDebugEnabled()) {
			logger.debug("Rendering view [" + view + "] in DispatcherServlet with name '" + getServletName() + "'");
		}
		view.render(mv.getModel(), request, response);
	}

	private void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response,
			HandlerExecutionChain mappedHandler, Exception ex) throws Exception {

		if (mappedHandler != null) {
			//mappedHandler.triggerAfterCompletion(request, response, ex);
		}
		throw ex;
	}

	private void triggerAfterCompletionWithError(HttpServletRequest request, HttpServletResponse response,
			HandlerExecutionChain mappedHandler, Error error) throws Exception, ServletException {

		ServletException ex = new NestedServletException("Handler processing failed", error);
		if (mappedHandler != null) {
			//mappedHandler.triggerAfterCompletion(request, response, ex);
		}
		throw ex;
	}
	
	private void applyDefaultViewName(HttpServletRequest request, ModelAndView mv) throws Exception {
		if (mv != null && !mv.hasView()) {
			mv.setViewName(getDefaultViewName(request));
		}
	}

}
