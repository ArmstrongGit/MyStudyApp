package hotfix;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.MODEL;
import static android.os.Build.PRODUCT;

/**
 * 作者： 苏晓伟 on 2017-8-30.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 ${ORGANIZATION_NAME}. All rights reserved.
 * 单例的设计模式异常捕捉
 *
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static  ExceptionCrashHandler mInstance;
    //获取应用信息
    private Context mContext;
    private static final String TAG="ExceptionCrashHandler";
    //获取系统默认的
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public static ExceptionCrashHandler getmInstance(){
        //解决多并发的问题
        if(mInstance==null){
           synchronized (ExceptionCrashHandler.class){
               if (mInstance == null) {
                   mInstance=new ExceptionCrashHandler();
               }
           }
        }
        return mInstance;
    }

    public void init(Context context){
        this.mContext=context;

        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);

        mDefaultUncaughtExceptionHandler=Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //全局异常
        Log.e(TAG,"报异常了");

        //写入到本地文件
        //1.崩溃的详细信息
        //2.应用信息 包名 版本号
        //3.手机信息
        //4.保存当前文件，等应用再次启动上传,上传文件不在这里处理
        String crashFileName=savaInfoToSD(e);

        cacheCrashFile(crashFileName);

        //让系统默认处理
        mDefaultUncaughtExceptionHandler.uncaughtException(t,e);
    }

    /**
     * 获取崩溃信息日志
     * @return
     */
    public File getCrashFile(){
        String crashFilename=mContext.getSharedPreferences("crash"
        ,Context.MODE_PRIVATE).getString("CRASH_FILE_NAME","");
        return new File(crashFilename);
    }

    private void cacheCrashFile(String crashFileName) {
        SharedPreferences sp=mContext.getSharedPreferences("crash",Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME",crashFileName).commit();
    }

    private String savaInfoToSD(Throwable e) {

        String fileName=null;
        StringBuffer sb=new StringBuffer();

        //1手机信息  应用信息==>obtaninSimpleInfo
        for(Map.Entry<String,String> entry:obtaninSimpleInfo(mContext).entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }

        //2崩溃的详细信息
        sb.append(obtainException(e));

        //保存文件 手机应用的目录，并没有拿手机的SD卡目录
        // 6.0以上需要动态申请权限

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            File dir=new File(mContext.getFilesDir()+File.separator );

            //先删除之前的异常信息
            if(dir.exists()){
                //删除该目录下的所有子文件
                deleteDir(dir);
            }

            //再重新创建文件夹
            if(!dir.exists()){
                dir.mkdir();
            }

            try{
                fileName=dir.toString()+File.separator+getAssignTime("yyyy_MM_dd_HH_mm")
                        +".txt";
                FileOutputStream fos=new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return fileName;
    }

    private String getAssignTime(String dateFormateStr) {
        DateFormat dateFormat=new SimpleDateFormat(dateFormateStr);
        long currentTime=System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }

    private boolean deleteDir(File dir) {

        if(dir.isDirectory()){
            File[] children=dir.listFiles();
            //遍历删除目录中子目录下
            for (File child : children) {
                child.delete();
            }
        }
        //目录此时为空，可以删除
        return true;

    }

    /**
     * 获取系统捕捉的异常
     * @param e
     * @return
     */
    private String obtainException(Throwable e) {
        //java 异常
        StringWriter stringWriter=new StringWriter();
        PrintWriter printWriter=new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 获取一些简单的信息，软件版本，手机版本，型号等信息存放在hashmap中
     * @param mContext
     * @return
     */
    private HashMap<String,String> obtaninSimpleInfo(Context mContext) {
        HashMap<String,String> map=new HashMap<>();
        PackageManager mPackageManager=mContext.getPackageManager();
        PackageInfo mPackageInfo=null;
        try{
            mPackageInfo=mPackageManager.getPackageInfo(
                    mContext.getPackageName(),mPackageManager.GET_ACTIVITIES);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        map.put("versionName",mPackageInfo.versionName);
        map.put("versionCode",""+mPackageInfo.versionCode);
        map.put("MODEL", MODEL);
        map.put("SDK_INT", ""+Build.VERSION.SDK_INT);
        map.put("PRODUCT",""+PRODUCT);
        map.put("MOBLE_INFO",getMobileInfo());
        return map;
    }

    /**
     *
     * 获取手机信息
     * @return
     */
    public String getMobileInfo() {
        StringBuffer sb=new StringBuffer();
        try{
            //利用反射获取 Build的所有属性
            Field[] fileds=Build.class.getDeclaredFields();
            for(Field field:fileds){
                field.setAccessible(true);
                String name=field.getName();
                String value=field.get(null).toString();
                sb.append(name+"="+value);
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}
