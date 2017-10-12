package db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * 作者： 苏晓伟 on 2017-10-12.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public class DaoSupportFactory {
    private SQLiteDatabase mSQLiteDatabase;
    private static DaoSupportFactory mFactory;

    //持有外部数据库的引用
    private DaoSupportFactory() {

        //把数据库放在内存卡里面
        //判断是否有存储卡 6.0动态权限适配
        File dbRoot=new File(Environment.getExternalStorageDirectory()
        .getAbsolutePath()+File.separator+"mystudy"
        +"database");

        if(!dbRoot.exists()){
            dbRoot.mkdirs();
        }

        File dbFile=new File(dbRoot,"test.db");

        //打开或创建一个数据库
        mSQLiteDatabase=SQLiteDatabase.openOrCreateDatabase(dbFile,null);

    }

    public static DaoSupportFactory getFactory() {
        if(mFactory==null){
            synchronized (DaoSupportFactory.class){
                //同步锁
                if(mFactory==null){
                    mFactory=new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T>IDaoSupport getDao(Class<T> clazz){
        IDaoSupport<T> daoSupport=new DaoSupportImpl();
        daoSupport.init(mSQLiteDatabase,clazz);
        return daoSupport;
    }

}
