package com.spatiomap.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spatiomap.authority.modules.entity.User;

import cn.jts.framework.web.result.JtsResult;

@FeignClient(name= "zkxt-authority",url="http://localhost:80/")
//@FeignClient(name= "zkxt-authority")
public interface AuthorityFeign {
	@RequestMapping(value="xadmin/user/getUserByloginName")
	public JtsResult<User> getUserByloginName(@RequestParam("loginName") String loginName);
	
	@RequestMapping(value="xadmin/user/sso")
	public JtsResult<String> sso(@RequestParam("loginName") String loginName,@RequestParam("password") String password,@RequestParam("ip") String ip);

}
