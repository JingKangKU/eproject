package com.chinaums.common.utils.exception;

public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Application context is null.
     */
    public static final String APPLICATION_CONTEXT_IS_NULL = "Application context is null";

    /**
     * Constructor of GlobalException.
     *
     * @param errorMessage the description of this exception.
     */
    public GlobalException(String errorMessage) {
        super(errorMessage);
    }
}