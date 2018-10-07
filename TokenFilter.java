package com.example.demo.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Token;
import com.example.demo.service.TokenService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TokenFilter implements Filter{

	@Autowired
	TokenService tokenService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String requestURI = req.getRequestURI();
//		String requestPath = req.getServletPath();
		String authHeader  = req.getHeader("Authorization");
		String requestMethod = req.getMethod();
		TokenService tokenService = new TokenService();
		String json = "src/main/resources/token.json";
		Gson gson = new Gson();
		BufferedReader br;

			br = new BufferedReader(new FileReader(json));
			Type type = new TypeToken<List<Token>>(){}.getType();
			List<Token> models = gson.fromJson(br, type);
			Token to; 
//		if (authHeader.isEmpty() || authHeader == null) {
//				
//		}else {
//			to = tokenService.getInfoToken(models, authHeader);
//			if (to == null) {
//				
//			}else {
//				
//			}
//		}
			
			to = tokenService.getInfoToken(models, authHeader);
			if (tokenService.isMethod(to, requestMethod)) {
				System.out.println(requestMethod);
				System.out.println("co method");
			}
			if (tokenService.isPath(to, requestURI)) {
				System.out.println(requestURI);
				System.out.println("co url");
			}
			if(tokenService.isMethod(to, requestMethod) && tokenService.isPath(to, requestURI)) {
				chain.doFilter(request, response);
			}
//			if(to == null) {
//				
//			}else {
//				if(tokenService.isMethod(to, requestMethod) && tokenService.isPath(to, requestURI)) {
//					chain.doFilter(request, response);
//				}
//			}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
