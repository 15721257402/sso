package com.tritonsfs.cac.sso.remote.exception;

import com.tritonsfs.cac.frame.core.exception.BaseException;


public class PermissionException extends BaseException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8296968379872213007L;

	public PermissionException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }
    public PermissionException(String errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
    }
}
