package hotfix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;


/**
 * 作者： 苏晓伟 on 2017-9-4.
 * 邮箱：armstrong.su@b-psoft.com
 * Copyright (c) 2017 上海商哲信息技术有限公司. All rights reserved.
 */

public class FixDexManager {

    private Context mContext;

    private File mDexDir;
    private String Tag="FixDexManager";

    public FixDexManager(Context context) {

        this.mContext=context;

        //获取应用可以访问的dex目录
        this.mDexDir=context.getDir("odex",Context.MODE_PRIVATE);

    }


    /**
     *
     * 修复Dex包
     * @param fixdexpath
     */
    public void fixDex(String fixdexpath) throws Exception{
        //1.先获取已经运行的dexElements
        ClassLoader applicationClassLoader= mContext.getClassLoader();

        Object applicationDexElenments=getDexElementsByClassLoader(applicationClassLoader);

        //2.获取下载好的补丁的dexElements

         //2.1移动到系统能够访问的 dex目录下 classloader

         File srcFile=new File(fixdexpath);
            if(!srcFile.exists()){
                throw new FileNotFoundException(fixdexpath);
            }

         File destFile=new File(mDexDir,srcFile.getName());

         if(destFile.exists()){
             Log.e(Tag,"path["+fixdexpath+"] has be loaded");
             return;
         }

        copyFile(srcFile,destFile);


         //2.1把路径转为classloader

        List<File> fixDexFiles=new ArrayList<>();
        fixDexFiles.add(destFile);

        fixdDexFiles(fixDexFiles);



    }

    /**
     * 把dexElements注入到classLoader中
     * @param classloader
     * @param dexElements
     */
    private void injectDexElements(ClassLoader classloader, Object dexElements) throws Exception{

        //先获取pathList
        Field pathListField= BaseDexClassLoader.class.getDeclaredField("pathList");
        //IOC 反射
        pathListField.setAccessible(true);
        Object pathList=pathListField.get(classloader);

        //2.pathList里面的dexElements
        Field dexElementField=  pathList.getClass().getDeclaredField("dexElements");
        dexElementField.setAccessible(true);
        dexElementField.set(pathList,dexElements);



    }

    /**
     * 合并两个数组
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private static Object combileArray(Object arrayLhs,Object arrayRhs){
        //拿到类的泛型
        Class<?> loacalClass=arrayLhs.getClass().getComponentType();
        int i=Array.getLength(arrayLhs);
        //两个数组长度相加
        int j=i+Array.getLength(arrayRhs);
        Object result=Array.newInstance(loacalClass,j);
        for(int k=0;k<j;++k){
            if(k<i){
                //k小于左边的大小就加入左边的数组
                Array.set(result,k,Array.get(arrayLhs,k));
            }else{
                //否则加入右边
                Array.set(result,k,Array.get(arrayRhs,k-1));
            }
        }
        return result;
    }

    /**
     *
     * 拷贝文件
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File src,File dest) throws IOException{

        FileChannel inChannel=null;
        FileChannel outChannel=null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        }finally {
            if(inChannel!=null){
                inChannel.close();
            }
            if(outChannel!=null){
                outChannel.close();
            }
        }
    }



    /**
     *
     * 从classloader中获取dexElements
     * @param classloader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classloader) throws Exception{
        //先获取pathList
        Field pathListField= BaseDexClassLoader.class.getDeclaredField("pathList");
        //IOC 反射
        pathListField.setAccessible(true);
        Object pathList=pathListField.get(classloader);

        //2.pathList里面的dexElements
        Field dexElementField=  pathList.getClass().getDeclaredField("dexElements");
        dexElementField.setAccessible(true);
        dexElementField.get(pathList);

        return dexElementField.get(pathList);
    }

    /**
     *
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception{

       File[] dexFiles= mDexDir.listFiles();
        List<File> fixDexFiles=new ArrayList<>();
        for (File dexFile : dexFiles) {
            if(dexFile.getName().endsWith(".dex")){
                fixDexFiles.add(dexFile);
            }
        }
        fixdDexFiles(fixDexFiles);

    }

    private void fixdDexFiles(List<File> fixDexFiles) throws Exception{
        //1.先获取已经运行的dexElements
        ClassLoader applicationClassLoader= mContext.getClassLoader();

        Object applicationDexElenments=getDexElementsByClassLoader(applicationClassLoader);
        File optimizedDirectory=new File(mDexDir,"odex");
        if(!optimizedDirectory.exists()){
            optimizedDirectory.mkdirs();
        }
        //修复
        //String dexPath, File optimizedDirectory, String librarySearchPath, ClassLoader parent
        for (File fixdexFile : fixDexFiles) {
            //dexPath dex路径
            //optimizedDirectory解压路径
            //librarySearchPath SO文件位置
            //parent 父classloader
            ClassLoader fixdexClassloader=new BaseDexClassLoader(
                    fixdexFile.getAbsolutePath(),//dex路径 必须要在应用目录下的dex文件中
                    optimizedDirectory,//解压路径
                    null,//so路径
                    applicationClassLoader//父classloader
            );

            Object fixDexElements=getDexElementsByClassLoader(fixdexClassloader);

            //3.把补丁的dexElements插到已经运行的dexElements的最前面
            //applicationClassLoader 数组 合并 fixdexClassloader数组
            //合并完成
            applicationDexElenments=combileArray(fixDexElements,applicationDexElenments);



        }

        //3.1注入到原来的类中 applicationClassLoader

        injectDexElements(applicationClassLoader,applicationDexElenments);
    }
}
