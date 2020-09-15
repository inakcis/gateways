package com.spatiomap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 功能描述:Sprint boot启动入口
 *
 * @author iNakcis
 * @version 1.0
 * 创建时间： 2019年10月03日 下午9:06:05
 * 版本：  1.0
 */
@SpringBootApplication()
@EnableFeignClients
public class JtsGateayApp {
	public static void main(String[] args) {
		SpringApplication.run(JtsGateayApp.class, args);
	}	
}
