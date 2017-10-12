package com.study.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.study.R;

import java.nio.charset.Charset;
import java.util.Locale;

public class Main3Activity extends AppCompatActivity implements
        NfcAdapter.CreateNdefMessageCallback,NfcAdapter.OnNdefPushCompleteCallback {

    private EditText mBeamText;

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mBeamText = (EditText) findViewById(R.id.edittext_beam_text);
        mNfcAdapter = mNfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()), 0);
        //指定推送消息的回调对象
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        //指定推送完成后的回调对象
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
    }


    //当推送消息方完成消息推送后调用该方法（屏幕缩小方）
    @Override
    public void onNdefPushComplete(NfcEvent event) {
        //Toast.makeText(this, "消息传输完成", Toast.LENGTH_LONG).show();
        Log.d("message", "complete");

    }

    //实现回调方法
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //获得文本框中输入的字符
        String text = mBeamText.getText().toString().trim();
        //如果未输入字符，则使用以下文本传送
        if ("".equals(text)) {
            text = "Android Beam Test";
        }
        //创建一个封装文本数据的NdefMessage对象，creatTextRecord方法用于创建封装文本的NdefRecord对象
        NdefMessage msg = new NdefMessage(new NdefRecord[]
                {createTextRecord(text)});
        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null,
                    null);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);

    }

    @Override
    public void onNewIntent(Intent intent) {
        //接收到数据后进行处理
        processIntent(intent);
    }

    public NdefRecord createTextRecord(String text) {
        byte[] langBytes = Locale.CHINA.getLanguage().getBytes(
                Charset.forName("US-ASCII"));
        Charset utfEncoding = Charset.forName("UTF-8");
        byte[] textBytes = text.getBytes(utfEncoding);
        int utfBit = 0;
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
                textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);

        return record;
    }

    //处理接收到的数据，将数据解析后并Toast出来
    void processIntent(Intent intent) {

        Parcelable[] rawMsgs = intent
                .getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        NdefMessage msg = (NdefMessage) rawMsgs[0];
        String text = TextRecord.parse(msg.getRecords()[0]).getText();
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
