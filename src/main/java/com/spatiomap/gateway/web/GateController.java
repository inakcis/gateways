package com.spatiomap.gateway.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinitionRouteLocator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spatiomap.authority.modules.entity.User;
import com.spatiomap.gateway.feign.AuthorityFeign;
import com.spatiomap.utils.JwtUtil;
import com.spatiomap.utils.ServletUtil;

import cn.jts.framework.web.result.JtsResult;
import cn.jts.framework.web.result.JtsResultUtil;

@RestController
public class GateController{
	@Autowired
	AuthorityFeign authorityFeign;
	
	@Autowired
    private RouteDefinitionRouteLocator locator;
		
	@RequestMapping(value = "/")
	public String getList(ServerHttpRequest request, ServerHttpResponse response) {
		String result = "成功访问到了gateway";
		return result;
	}	
	
	@RequestMapping(value="/getUserByloginName")
	public JtsResult<User> getUserByloginName(ServerHttpRequest request,String loginName){
		//打印Token header
		HttpHeaders headers=request.getHeaders();
		String token=headers.getFirst(JwtUtil.HEADER_AUTH);
		System.out.println("网关中获取Token:"+token);
		
		JtsResult<User> result=authorityFeign.getUserByloginName(loginName);
		return result;
	}
	
	@RequestMapping(value="/sso")
	public JtsResult<String> sso(ServerHttpRequest request,ServerHttpResponse response,@RequestBody User user){
		String loginName,password;
		if(user!=null&&user.getLoginName()!=null){
			loginName=user.getLoginName();
			password=user.getPassword();
		}else {
			return JtsResultUtil.failure("用户名和秘密都不能为空！");
		}
		
		String ip=ServletUtil.getIpAddress(request);
		JtsResult<String> result=authorityFeign.sso(loginName,password,ip);		
		return result;
	}
	
	@RequestMapping(value="/getRoute")
	public JtsResult<List<String>> getRoute(ServerHttpRequest request,ServerHttpResponse response){
		Iterator<Route> routes=locator.getRoutes().toIterable().iterator();
		List<String> copy = new ArrayList<String>();
	    while (routes.hasNext())
	        copy.add(routes.next().toString());		
		
		JtsResult<List<String>> result=JtsResultUtil.success(copy);	
		return result;
	}	
}
