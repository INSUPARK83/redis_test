package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {

    public static final String STATUS_CODE_SUCCESS = "SUCCESS";
    public static final String STATUS_CODE_INVALID = "INVALID";
    public static final String STATUS_CODE_FAIL = "FAIL";

    private long timestamp;

    private String statusCode;

    private String statusMessage;

    private int httpStatusCode = 200;

    private T data;

    public boolean isSuccess() {
        return STATUS_CODE_SUCCESS.equals(statusCode);
    }


    public boolean isInvalid() {
        return STATUS_CODE_INVALID.equals(statusCode);
    }

    public static <T> ResponseWrapper<T> invalid(String failMessage) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setTimestamp(new Date().getTime());
        responseWrapper.setStatusCode(STATUS_CODE_INVALID);
        responseWrapper.setHttpStatusCode(HttpStatus.OK.value());
        responseWrapper.setStatusMessage(failMessage);
        responseWrapper.setData(null);
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> success() {
        return success(null, "");
    }

    public static <T> ResponseWrapper<T> success(T data) {
        return success(data, "");
    }

    public static <T> ResponseWrapper<T> success(T data, String statusMessage) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setTimestamp(new Date().getTime());
        responseWrapper.setStatusCode(STATUS_CODE_SUCCESS);
        responseWrapper.setStatusMessage(statusMessage);
        responseWrapper.setHttpStatusCode(HttpStatus.OK.value());
        responseWrapper.setData(data);
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> internalFail(T data, String failMessage) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setTimestamp(new Date().getTime());
        responseWrapper.setStatusCode(STATUS_CODE_FAIL);
        responseWrapper.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseWrapper.setStatusMessage(failMessage);
        responseWrapper.setData(data);
        return responseWrapper;
    }

    public static <T> ResponseWrapper<T> internalFail(String failMessage) {
        return internalFail(null, failMessage);
    }


    public static <T> ResponseWrapper<T> notFound(String failMessage) {
        return notFound(null, failMessage);
    }

    public static <T> ResponseWrapper<T> notFound(T data, String failMessage) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        responseWrapper.setTimestamp(new Date().getTime());
        responseWrapper.setStatusCode(STATUS_CODE_FAIL);
        responseWrapper.setStatusMessage(failMessage);
        responseWrapper.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
        responseWrapper.setData(data);
        return responseWrapper;
    }

}