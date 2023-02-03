package com.ums.eproject.utils;

/**
 * 业务异常，message信息可以作为页面的错误提示信息
 * @author shijianquan
 *
 */
public class BusiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7355985165855609053L;

    private final String message;
    private final Throwable throwable;
    
    public BusiException(String message) {
    	this.message = message;
    	this.throwable = null;
    }
    
    public BusiException(String message, Throwable throwable) {
    	this.message = message;
    	this.throwable = throwable;
    }
    
    /**
     * 返回错误信息 <br>
     * <b>作者： shijq</b> <br>
     * 创建时间：2012-2-27 上午10:19:44
     * 
     * @since 1.0
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 返回异常 <br>
     * <b>作者： shijq</b> <br>
     * 创建时间：2012-2-27 上午10:19:44
     * 
     * @since 1.0
     * @return 异常
     */
	public Throwable getThrowable() {
		return throwable;
	}

}
