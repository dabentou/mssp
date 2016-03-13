package com.mmd.mssp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mmd.mssp.comm.Constants;


/**
 * @ClassName: MsspFilter
 * @Package com.mmd.mssp.filter
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-12-24
 * @version V1.1 
 */
public class MsspFilter  implements Filter{
	
	private static String[] noFilteUris={"js","css","/user/login","/login.jsp","jpg","gif","png"};

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession  session = request.getSession();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String servletPath = request.getServletPath();
		StringBuffer sbURL = request.getRequestURL();
		
		//System.out.println("----servletPath----"+servletPath);
		//System.out.println("----sbURL----"+sbURL);
		String param = request.getParameter("operate");
		if (!isSessionvalidate(request, response, sbURL.toString(),param,servletPath)) {
			if(null==session.getAttribute(Constants.USER_KEY)){
				request.setAttribute("tips","请求超时，请重新登录!");
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
				return;
			}
		}
		//ajax请求响应application/json
		if(isAjaxRequest(request)){
			response.setContentType("application/json");
		} else {
			response.setContentType("text/html");
		}
		chain.doFilter(request, response);
	}

	private boolean isSessionvalidate(HttpServletRequest request,
			HttpServletResponse response,String sbURL,String param,String servletPath) {
		if(servletPath.equals("/ueditor/jsp/controller.jsp")){
			return true;
		}
		if(servletPath.equals("/login.jsp")||servletPath.equals("/user/login")||servletPath.equals("//WEB-INF/jsp/login.jsp")){
			return true;
		}else{
			String suffix = sbURL.substring(sbURL.lastIndexOf(".")+1, sbURL.length());
			for (int i = 0; i < noFilteUris.length; i++) {
				if(noFilteUris[i].equals(suffix)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
