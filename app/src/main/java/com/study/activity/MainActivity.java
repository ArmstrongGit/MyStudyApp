package com.study.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bpsoft.baselibrary.ioc.CheckNet;
import com.bpsoft.baselibrary.ioc.OnClick;
import com.bpsoft.baselibrary.ioc.ViewById;
import com.bpsoft.baselibrary.ioc.ViewUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.study.R;
import com.study.base.BaseTranslucentActivity;
import com.study.beans.Person;
import com.study.beans.Student;
import com.study.callback.DialogCallback;
import com.study.responsebean.CommentResponse;
import com.study.responsebean.HomeList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import cn.addapp.pickers.picker.DatePicker;
import db.DaoSupportFactory;
import db.DaoSupportImpl;
import db.IDaoSupport;
import hotfix.ExceptionCrashHandler;
import hotfix.FixDexManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseTranslucentActivity {


    @ViewById(R.id.btn_1)
    private Button button1;
    @ViewById(R.id.btn_2)
    private Button button2;
    private Button button3;
    private TextView tvResult;
    private String TAG;
    private ArrayList<Integer> list = new ArrayList<>();
    EditText ed_test1;
     String[] items ;
     boolean selected[] ;

        Map<String,String> map=new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }else{
            IDaoSupport<Person> daoSupport= DaoSupportFactory.
                    getFactory().getDao(Person.class);
        }




        /**
         *
         */
        //初始化后台的数据
        Map<String,String> map2=new HashMap<>();
        map2.put("001","海天");
        map2.put("002","李锦记");
        //初始化数据字典数据
        map.put("001","海天");
        map.put("002","李锦记");
        map.put("003","厨邦");
        map.put("004","加加");
        Iterator it = map.entrySet().iterator();
        String value;
        items = new String[map.entrySet().size()];
        selected=new boolean[map.entrySet().size()];
        for (int i = 0; i < map.entrySet().size(); i++) {
            Map.Entry entry = (Map.Entry) it.next();
            value = entry.getValue().toString();
            items[i] = value;
            selected[i]=false;
        }
        Iterator it2 = map.entrySet().iterator();
        for (int i = 0; i < map2.entrySet().size(); i++) {
            Map.Entry entry = (Map.Entry) it2.next();
            value = entry.getValue().toString();
            if(value.equals(items[i])){
                selected[i]=true;
            }else{
                selected[i]=false;
            }

        }
        /**
         *
         */

//        View nav=findViewById(R.id.nav);
//        ViewGroup.LayoutParams p= nav.getLayoutParams();



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
//        }
        setContentView(R.layout.activity_main);
        ViewUtil.inject(this);
        //获取上次崩溃信息上传到服务器
        File crashFile= ExceptionCrashHandler.getmInstance().getCrashFile();

        if(crashFile.exists()){
            //上传到服务器
            try {
                InputStreamReader fileReader=new InputStreamReader(new FileInputStream(crashFile));
                char[] buffer=new char[1024];
                int len=0;
                while ((len=fileReader.read(buffer))!=-1){
                    String str=new String(buffer,0,len);
                    Log.d("ExceptionCrashHandler",str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        //自定义热修复
        fixDexBugs();


        //模拟错误
//        int i=1/0;


//        button1=(Button)findViewById(R.id.btn_1);
//        button2=(Button)findViewById(R.id.btn_2);
        button3=(Button)findViewById(R.id.btn_3);
        tvResult=(TextView)findViewById(R.id.tv_result);
        //1.注册广播
        EventBus.getDefault().register(this);
        ed_test1= (EditText) findViewById(R.id.ed_test1);
        ed_test1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<2){
                    Toast.makeText(MainActivity.this, "名字长度至少为"+s, Toast.LENGTH_SHORT).show();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker picker = new DatePicker(MainActivity.this, DatePicker.YEAR_MONTH);
//                picker.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
//                picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
                picker.setRangeStart(2016, 10, 14);
                picker.setRangeEnd(2020, 11, 11);
                picker.setSelectedItem(2017, 9);
                picker.setCanLinkage(true);
                picker.setCancelText("取消");
                picker.setSubmitText("确定");
                picker.setWeightEnable(true);
                picker.setWheelModeEnable(true);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
                    @Override
                    public void onDatePicked(String year, String month) {
                        Toast.makeText(MainActivity.this, year + "-" + month, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
//                    ed_test1.setText("啦啦啦");
//                EventBus.getDefault().postSticky(new Student("瓜皮测试数据"));
//                Intent i=new Intent(MainActivity.this,FabActivity.class);
//                startActivity(i);
//                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this)
//                        .setContentView(R.layout.delay_dialog)
//                        .setText(R.id.dialog_msg,"呵呵呵呵")
//                        .formButtom(true)
//                        .fullWidth()
//                        .show();
//
//                final EditText contentEt=dialog.getView(R.id.arrive_time);
//
//
//                dialog.setOnclickListener(R.id.dialog_shure, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, contentEt.getText().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
                         }});
                //dialog去操作点击事件

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                for (int i = 0, size = selected.length; i < size; ++i) {
                    if (selected[i]) {
                        list.add(i);
                    }
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(15, 15, 15, 15);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                TextView tv_title=new TextView(MainActivity.this);
                tv_title.setText("请选择");
                tv_title.setGravity(Gravity.CENTER);
                tv_title.setLayoutParams(lp);
                tv_title.setTextSize(26);
                dialog.setCustomTitle(tv_title)
                        .setMultiChoiceItems(items, selected,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if (isChecked) {
                                            list.add(which);
                                        } else {
                                            list.remove(Integer.valueOf(which));
                                        }
                                    }
                                })
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (list.size() == 0) {
                                            Toast.makeText(MainActivity.this, "你什么都没选啊，小伙", Toast.LENGTH_SHORT).show();
                                        } else {
                                            StringBuilder str = new StringBuilder();
                                            for (int i = 0, size = list.size(); i < size; i++) {
                                                str.append(items[list.get(i)]);
                                                if (i < size - 1) {
                                                    str.append(", ");
                                                }
                                            }
                                            Toast.makeText(MainActivity.this, "你选中了: " + str.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ScondActivity.class);
                startActivity(i);

            }
        });


//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            /**
//             * ObservableEmitter： Emitter是发射器的意思，那就很好猜了，
//             * 这个就是用来发出事件的，它可以发出三种类型的事件，
//             * 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)
//             * 就可以分别发出next事件、complete事件和error事件。
//             * @param e
//             * @throws Exception
//             */
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onComplete();
//                e.onNext(3);
//            }
//        }).subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Integer value) {
//                Toast.makeText(mContext, "下游接收到的数据："+value, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {

                Log.d("MAIN","OBSERVABLE IS ON THREAD"+Thread.currentThread().getName());

                e.onNext(12);
                Log.d("Emitter","12");
            }
        });

        Consumer<Integer> consumer=new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {

                Log.d("MAIN","observer IS ON THREAD"+Thread.currentThread().getName());
                Log.d("Main","ON NEXT"+integer);

            }
        };

        //subscribeOn() 指定的是上游发送事件的线程,
        //observeOn() 指定的是下游接收事件的线程.
        //多次指定上游的线程只有第一次指定的有效,
        //也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(consumer);





//      initOKGO3();
    }

//    @OnClick({R.id.btn_1})
    @CheckNet//没网就不执行该方法而是直接打印Toast
    private void onClick(View view){

//              Intent i=new Intent(this, MaskFilterActivity.class);
//                startActivity(i);
//        AlertDialog dialog=new AlertDialog.Builder(this)
//                .setText(R.id.tv_result,"呵呵呵呵")
//                .setContentView(R.layout.activity_third)
//                .setOnclickListener(R.id.button, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
//                    }
//                }).show();



    }


    private void testBug(View v) {

        Toast.makeText(MainActivity.this, "bug修复测试", Toast.LENGTH_SHORT).show();

    }

    private void fixDexBugs() {

        File dexfile=new File(Environment.getExternalStorageDirectory(),"fix.dex");
        if(dexfile.exists()){
            FixDexManager fixDexManager=new FixDexManager(this);
            try{
                fixDexManager.fixDex(dexfile.getAbsolutePath());
                Toast.makeText(MainActivity.this, "修复成功", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "修复失败", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void initOKGO3() {

            OkGo.<CommentResponse<List<HomeList>>>get("http://192.168.1.40:7070/bpcrmv10_dingdong/mobile/entr ance.bk?data={%22user_id%22:%22IAC00056%22,%22content%22:{%22C_A51600_C_ID%22:%22A51600IAC00003-15CD8819DDA755861DE5EV67MC7001324%22},%22action%22:%22GetCountWebService-Listcount%22}")
                .tag(this)
                .execute(new DialogCallback<CommentResponse<List<HomeList>>>(this) {

                    @Override
                    public void onSuccess(Response<CommentResponse<List<HomeList>>> response) {
                        Toast.makeText(MainActivity.this, "获取："+ response.body().getContent().get(0).getTitlename(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Response<CommentResponse<List<HomeList>>> response) {
                        super.onError(response);
                        Toast.makeText(MainActivity.this, response.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(Student student){
        tvResult.setText(student.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除广播
        EventBus.getDefault().unregister(MainActivity.this);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "您拒绝授予权限，请前往设置开启权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
