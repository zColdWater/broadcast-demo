package com.example.broadcasdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StaticCustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("StaticCustomReceiver","接到通知");
        Toast.makeText(context, "接到通知", Toast.LENGTH_SHORT).show();
    }
}
