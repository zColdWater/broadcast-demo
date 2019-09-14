package com.example.broadcasdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private NetworkReceiver networkReceiver;

    private LocalBroadcastManager localBroadcastManager ;
    private LocalBroadCastReceiver localBroadCastReceiver;
    private IntentFilter localIntentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("broadcast.dynamic.demo.action");
                // 发送无需通知
                sendBroadcast(intent);
                // 发送有序通知 第一个参数 intent 第二个参数 与权限相关 这里传null
                // 发送有序广播 需要注册广播的时候设置 priority 参数，在所有可以接收广播的receiver中，哪个
                // priority值大，哪个会先收到广播，并且还可以截断广播不给后面传递。
                sendOrderedBroadcast(intent,null);
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("静态通知","发送通知");
                Intent intent = new Intent("com.example.broadcast.MY_BROADCAST");
                sendBroadcast(intent);
                // 发送有序通知 第一个参数 intent 第二个参数 与权限相关 这里传null
                sendOrderedBroadcast(intent,null);
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("本地静态通知","发送通知");
                Intent intent = new Intent();
                intent.setAction("localBroadcast.static.demo.action");
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        // 创建动态本地通知
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadCastReceiver = new LocalBroadCastReceiver();
        localIntentFilter = new IntentFilter("localBroadcast.static.demo.action");
        localBroadcastManager.registerReceiver(localBroadCastReceiver, localIntentFilter);

        // 创建动态通知
        intentFilter = new IntentFilter();
        intentFilter.addAction("broadcast.dynamic.demo.action");
        networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver,intentFilter);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册全局广播接收器
        unregisterReceiver(networkReceiver);
        //取消注册应用内广播接收器
        localBroadcastManager.unregisterReceiver(localBroadCastReceiver);
    }

    class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("NetworkReceiver","接到通知");
            Toast.makeText(context, "接到通知", Toast.LENGTH_SHORT).show();
        }
    }

    class LocalBroadCastReceiver extends  BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("LocalBroadCastReceiver","接到通知");
            Toast.makeText(context, "接到通知", Toast.LENGTH_SHORT).show();
        }
    }

}
