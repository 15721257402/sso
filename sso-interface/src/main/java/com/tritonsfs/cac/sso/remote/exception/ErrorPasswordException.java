package com.tritonsfs.cac.sso.remote.exception;

import com.tritonsfs.cac.frame.core.exception.BaseException;

/**
 * @Time 2018/4/13
 * @Author 佛祖保佑后的最
 */
public class ErrorPasswordException extends BaseException {

    public ErrorPasswordException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public ErrorPasswordException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }
}
