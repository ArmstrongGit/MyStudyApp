package com.study.responsebean;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * 作者： Armstrong on 2016/11/21.
 * 邮箱：armstrong.su@b-psoft.com
 */

public class CommentResponse<T> implements Serializable{


    private static final long serialVersionUID = 8061092212447953350L;
    /**
     * message : 操作成功
     * content : {"C_NAME":"kate","user_id":"IAC00001"}
     * code : 200
     */

    public String message;
    private T content;
    public String code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
