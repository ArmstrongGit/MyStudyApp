package db;

import android.database.sqlite.SQLiteDatabase;

/**
 * 作者： 苏晓伟 on 2017-10-12.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 上海商哲技术有限公司 All rights reserved.
 * Description:
 */

public interface IDaoSupport<T> {

    public void init(SQLiteDatabase database, Class<T> clazz);

    //插入数据
    public int insert(T t);

}
