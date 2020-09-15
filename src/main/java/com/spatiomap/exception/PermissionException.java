package com.spatiomap.exception;

/**
 * 
 * 权限异常类
 *
 * @author iNakcis
 * @version 1.0
 * 创建时间： 2020年6月10日 上午9:17:09 
 */
public class PermissionException extends RuntimeException {	
	private static final long serialVersionUID = 18686863204737716L;
	public PermissionException(String msg) {
        super(msg);
    }
}
