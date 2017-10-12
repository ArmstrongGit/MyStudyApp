package com.bpsoft.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者： 苏晓伟 on 2017-9-5.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description: View的Annotation
 */
//Target(ElementType.FIELD) 代表Annotation的位置
    //Target:METHOD放在方法上 TYPE放在类上 FIEL代表属性
@Target(ElementType.METHOD)
//RetentionPolicy 什么时候生效
//RetentionPolicy:CLASS 编译时生效 RUNTIME运行时 SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckNet {

}
