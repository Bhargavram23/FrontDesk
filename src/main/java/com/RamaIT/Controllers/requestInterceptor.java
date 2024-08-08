package com.RamaIT.Controllers;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class requestInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getRequestURI().contains("/logout") && request.getSession().getAttribute("loggedUser") != null) {
			request.getSession().setAttribute("loggedUser",null) ;
			response.sendRedirect("/home");
		}
		if ("/login".equals(request.getRequestURI()) || "/register".equals(request.getRequestURI())
				|| "/forgot".equals(request.getRequestURI()) || "/".equals(request.getRequestURI())
				|| "/home".equals(request.getRequestURI()) || request.getRequestURI().contains("/verify")
				|| request.getRequestURI().contains("h2")) {
			return true;
		} else {
			if (request.getSession().getAttribute("loggedUser") != null) {
				return true;
			}
		}

		return false;
	}
}
