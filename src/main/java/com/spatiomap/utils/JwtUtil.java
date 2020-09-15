package com.spatiomap.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.spatiomap.exception.PermissionException;

import cn.jts.framework.utils.StringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Token工具类* 
 *
 * @author iNakcis
 * @version 1.0
 * <br>创建时间： 2020年6月10日 上午9:20:49
 */
public class JwtUtil {
	public static final String SECRET = "qazwsxdfadfg2e589kj90jbsf089q0j3rmgagvyt3ca8";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_AUTH = "Authorization";
    
    public static String generateToken(String userId,String loginName,String nickName,String ip) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("clientId", UUID.randomUUID());
        map.put("userId", userId);
        map.put("loginName", loginName);
        map.put("nickName", nickName);
        map.put("ip", ip);
        String jwt = Jwts.builder()
			  .setClaims(map)
			  .setExpiration(generateExpirationDate())
			  .signWith(SignatureAlgorithm.HS512, SECRET)
			  .compact();
        String finalJwt = TOKEN_PREFIX + " " +jwt;
        return finalJwt;
    }
    
    public static Map<String,String> validateToken(String token) {
        if (token != null) {
			token=token.substring(TOKEN_PREFIX.length());
        	HashMap<String, String> map = new HashMap<String, String>();
        	map.put("isOk",  "false");
            Map<String,Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            String loginName = String.valueOf(body.get("loginName"));
            map.put("userId",  String.valueOf(body.get("userId")));
            map.put("clientId", String.valueOf(body.get("clientId")));
            map.put("loginName", loginName);
            map.put("nickName",  String.valueOf(body.get("nickName")));
            map.put("ip", String.valueOf(body.get("ip")));
            if(StringUtils.isEmpty(loginName)) {
				throw new PermissionException("token解析用户错误！");
            }else {
            	map.put("isOk",  "true");
            }
            return map;
        } else {
        	throw new PermissionException("token不能为空！");
        }
    }
    
    private static Date generateExpirationDate() {
    	//60*60*24*7=604800 7天有效期
        return new Date(System.currentTimeMillis() + 604800 * 1000);
    }
}
