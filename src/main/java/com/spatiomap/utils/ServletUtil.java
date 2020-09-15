package com.spatiomap.utils;

import java.net.InetSocketAddress;

import org.springframework.http.server.reactive.ServerHttpRequest;

public class ServletUtil {
	/**
	 * 获取用户真实IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            InetSocketAddress address=request.getRemoteAddress();
            ip=address.getHostString()+":"+String.valueOf(address.getPort());
        }
        return ip;
	}
}
