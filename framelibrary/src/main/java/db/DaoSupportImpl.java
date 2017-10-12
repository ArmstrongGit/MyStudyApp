package db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * 作者： 苏晓伟 on 2017-10-12.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class DaoSupportImpl<T> implements IDaoSupport{

    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;
    private String TAG="DaoSupportImpl";

    @Override
    public void init(SQLiteDatabase database, Class clazz) {
        this.mSQLiteDatabase=database;
        this.mClazz=clazz;

        //创建表
        /*
            create table if not exists XXX (
            + " id integer primary key autoincrement, "
            + " name text , "
            )";"
         */
        StringBuffer sb=new StringBuffer();
        sb.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append(" (id integer primary key autoincrement, ");
        Field[] fields=mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);//设置权限
            String name=field.getName();
            String type=field.getType().getSimpleName();
            //type 需要进行转换 int-->integer String-->text
            if(name.contains("$change")||name.contains("serialVersionUID")){

            }else{
                sb.append(name).append(DaoUtil.getColumnType(type))
                        .append(", ");
            }



        }
        sb.replace(sb.length()-2,sb.length(),")");
        String createTableSql=sb.toString();

        Log.d(TAG,"执行-->"+createTableSql);
        //执行创建表
        mSQLiteDatabase.execSQL(createTableSql);
    }


    /*
        插入数据 t 任意对象
         */
    @Override
    public int insert(Object o) {


        return 0;
    }
}
