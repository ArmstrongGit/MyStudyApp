package com.study.responsebean;

/**
 * 作者： 苏晓伟 on 2017-8-9.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 */

public class SimpleResponse {
    private String message;
    private String code;


    public CommentResponse toLzyResponse() {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.code = code;
        commentResponse.message = message;
        return commentResponse;
    }
}
