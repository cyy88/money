package com.cyy.commom.dto;
import com.cyy.commom.constant.ApiResponseCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiResponse 类用于封装 API 请求的响应信息。
 * @param <T> 响应数据的类型
 */
@Data
public class ApiResponse<T> {
    private T data; // 响应的数据
    private Integer code = 0; // 响应状态码
    private String codeMessage; // 状态码对应的消息
    private Map<String, String> errorMessage; // 错误消息集合
    private Boolean success = true; // 响应是否成功的标志

    /**
     * 生成一个表示成功响应的对象。
     * @return ApiResponse<T> 成功响应对象
     */
    public static <T> ApiResponse<T> success() {
        return success(null);
    }

    /**
     * 生成一个包含数据的成功响应对象。
     * @param data 成功时返回的数据
     * @return ApiResponse<T> 包含数据的成功响应对象
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(data);
        response.setCode(ApiResponseCode.SUCCESS.getCode());
        response.setCodeMessage(ApiResponseCode.SUCCESS.getMessage());
        response.setSuccess(true);
        return response;
    }

    /**
     * 生成一个表示错误响应的对象，可以包含多个错误消息。
     * @param errors 错误消息集合
     * @return ApiResponse<T> 错误响应对象
     */
    public static <T> ApiResponse<T> error(Map<String, String> errors) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setData(null);
        response.setCode(ApiResponseCode.SERVICE_ERROR.getCode());
        response.setCodeMessage(ApiResponseCode.SERVICE_ERROR.getMessage());
        response.setErrorMessage(errors);
        response.setSuccess(false);
        return response;
    }

    /**
     * 生成一个表示错误响应的对象，包含单个错误消息。
     * @param error 错误消息
     * @return ApiResponse<T> 错误响应对象
     */
    public static <T> ApiResponse<T> error(String error) {
        Map<String, String> errors = new HashMap<>();
        errors.put(error, error);
        return error(errors);
    }

    /**
     * 设置响应为错误状态，并可指定错误数据。
     * @param msg 错误消息
     * @param data 错误时返回的数据
     * @return ApiResponse<T> 错误响应对象
     */
    public ApiResponse<T> error(String msg, T data) {
        this.setData(data);
        this.setSuccess(false);
        this.setCode(ApiResponseCode.SERVICE_ERROR.getCode());
        this.setCodeMessage(ApiResponseCode.SERVICE_ERROR.getMessage());
        return this;
    }

    /**
     * 设置响应为错误状态，指定错误码和错误消息集合。
     * @param code 错误码
     * @param errors 错误消息集合
     * @return ApiResponse<T> 错误响应对象
     */
    public ApiResponse<T> error(Integer code, Map<String, String> errors) {
        this.setCode(code);
        this.setErrorMessage(errors);
        this.setData(data);
        this.setSuccess(false);
        return this;
    }
}
