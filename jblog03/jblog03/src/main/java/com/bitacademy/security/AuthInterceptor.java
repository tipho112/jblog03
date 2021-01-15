package com.bitacademy.security;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bitacademy.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (handler instanceof HandlerMethod == false) {
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		if (auth == null) {
			auth = handler.getClass().getAnnotation(Auth.class);
		}

		if (auth == null) {
			return true;
		}

		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}

		@SuppressWarnings("unchecked")
		final Map<String, String> variableValueMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

		if(!authUser.getId().equals(variableValueMap.get("id"))) {
			return false;
		}
		
		String role = auth.value();
		System.out.println(role);

		if ("USER".equals(role)) {
			return true;
		}

		return true;
	}

	public boolean hello(@PathVariable String id) {
		boolean isOwner = false;
		System.out.println(id);
		System.out.println(id);
		System.out.println(id);
		System.out.println(id);
		return isOwner;
	}
}
