package com.khj.exam.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.khj.exam.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NeedLoginInterceptor implements HandlerInterceptor {
	@Autowired
	private Rq rq;
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		

		if (!rq.isLogined() ) {
			String afterLoginUri = rq.getLoginUri();
			rq.printReplaceJs("로그인 후 이용해 주세요.", afterLoginUri);
			return false;
		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}
}
