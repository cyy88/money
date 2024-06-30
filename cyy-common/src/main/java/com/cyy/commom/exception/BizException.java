package com.cyy.commom.exception;

import com.cyy.commom.constant.ApiResponseCode;

/**
 * 短信验证码业务处理异常
 */
public class BizException extends BaseException {

    private static final long serialVersionUID = 628904681759624791L;

    public BizException(String message) {
        super(ApiResponseCode.BUSINESS_ERROR.getCode(), message);
    }

    public BizException(int code, String message) {
        super(code, message);
    }

    public BizException(String message, Throwable t) {
        super(message, t);
    }
}
