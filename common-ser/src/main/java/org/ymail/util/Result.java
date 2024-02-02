package org.ymail.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 全局返回对象
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5679018624309023727L;

    /**
     * 正确返回码
     */
    public static final String SUCCESS_CODE = "200";
    /**
     * 错误返回码
     */
    public static final String FAIL_CODE = "500";

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;
    /**
     * 请求是否成功
     */
    private Boolean flag;

    /**
     * 响应数据
     */
    private T data;

    public static Result<Void> success() {
        return new Result<Void>()
                .setFlag(Boolean.TRUE);
    }
    public static <T>Result<T> success(T data) {
        return new Result<T>()
                .setFlag(Boolean.TRUE)
                .setData(data);
    }

    public static <T> Result<T> failure(T data) {
        return new Result<T>()
                .setFlag(Boolean.FALSE)
                .setData(data);
    }
    public static  Result<Void> failure(String msg) {
        return new Result<Void>()
                .setFlag(Boolean.FALSE)
                .setMessage(msg);
    }
    public static Result<Void> res(Boolean t){
        return new Result<Void>()
                .setFlag(t);
    }
    public static Result<Void> res(Boolean t,String msg){
        return new Result<Void>()
                .setFlag(t)
                .setMessage(msg);
    }

}