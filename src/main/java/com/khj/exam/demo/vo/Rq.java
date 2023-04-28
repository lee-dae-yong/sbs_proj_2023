package com.khj.exam.demo.vo;

import java.io.IOException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.khj.exam.demo.service.MemberService;
import com.khj.exam.demo.utill.Ut;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;
		
		this.session = req.getSession();
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if ( session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = memberService.getMemberById(loginedMemberId);
		
		this.req.setAttribute("rq", this);
	}
	
	public boolean isNotLogined() {
		return !isLogined;
	}
	
	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html;charset=UTF-8");
		print(Ut.jsHistoryBack(msg));
	}
	public void printJsReplace(String msg, String uri) {
		resp.setContentType("text/html;charset=UTF-8");
		print(Ut.jsReplace(msg, uri));
	}
	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void println(String str) {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	
	public String historyBackJsOnview(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}
	public String jsReplace(String msg,String uri) {
		return Ut.jsReplace(msg,uri);
	}

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();
		if(queryString != null && queryString.length()>0) {
			currentUri+="?"+queryString;
		}
		
		return currentUri;
	}
	
	public String getEncodedCurrentUri() {
		return Ut.getUriEncode(getCurrentUri());
	}
	
	public void initBeforeActionInterceptor() {}

	
}
