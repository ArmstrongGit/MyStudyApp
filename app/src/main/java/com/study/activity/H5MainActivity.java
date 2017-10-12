package com.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.study.R;
import com.study.beans.LUser;
import com.study.callback.JsonCallback;
import com.study.responsebean.CommentResponse;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.study.utils.Convert.GsonHolder.gson;


public class H5MainActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar mProgressBar;
    String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_main);
        webView= (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_progress);
//        button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(this);

        doWebViewSetting();
        loadUrl("file:///android_asset/login.html");


    }

    private void doWebViewSetting(){
        //   设置WebClient(可不要)
        webView.setWebViewClient(new MyWebViewClient());
        //  支持js(必要)
        webView.getSettings().setJavaScriptEnabled(true);
        //    添加js对象(必要)
        webView.addJavascriptInterface(new JsOperation(this), "client");
    }

    private void loadUrl(String url) {
        mUrl = url;
        mProgressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(url);
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mProgressBar.setVisibility(View.GONE);
        }
    }


    class JsOperation {

        Activity mActivity;

        public JsOperation(Activity activity) {
            mActivity = activity;
        }

        //    测试方法
      @JavascriptInterface
        public void login(String str1,String str2) {
          HashMap data=new HashMap();
          data.put("action","UserWebService-login");
          HashMap content=new HashMap();
          content.put("C_ACCOUNTNAME",str1);
          content.put("C_PASSWORD",str2);
          data.put("content",content);
          OkGo.<CommentResponse<LUser>>post("http://dd.diy-crm.com/bpcrmv10_dingdong/mobile/entrance.bk")
                  .tag(this)
                  .params("data",gson.toJson(data))
                  .execute(new JsonCallback<CommentResponse<LUser>>() {

                      @Override
                      public void onError(com.lzy.okgo.model.Response<CommentResponse<LUser>> response) {
                          super.onError(response);
                          Toast.makeText(mActivity, ""+response.getException(), Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onSuccess(com.lzy.okgo.model.Response<CommentResponse<LUser>> response) {
                              loadUrl("file:///android_asset/index.html");
                      }
                  });
        }
    }


}
