package db;

/**
 * 作者： 苏晓伟 on 2017-10-12.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class DaoUtil {

    public static String getColumnType(String type){
        String value=null;
        if(type.contains("String")){
            value=" text";
        }else if (type.contains("int")){
            value=" integer";
        }else if (type.contains("float")){
            value=" float";
        }else if (type.contains("double")){
            value=" double";
        }else if (type.contains("char")){
            value=" varchar";
        }else if (type.contains("long")){
            value=" long";
        }
        return value;
    }

    public static String getTableName(Class<?> clazz){
        return clazz.getSimpleName();
    }

}
