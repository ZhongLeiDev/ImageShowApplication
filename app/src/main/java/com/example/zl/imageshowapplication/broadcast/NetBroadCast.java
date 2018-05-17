 package com.example.zl.imageshowapplication.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.message.MsgType;
import com.example.zl.imageshowapplication.myinterface.MsgNotifier;
import com.example.zl.imageshowapplication.myinterface.MsgNotifyReceiver;

import static android.content.ContentValues.TAG;
import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Administrator on 2018/5/15.
 */

public class NetBroadCast extends BroadcastReceiver implements MsgNotifier {

    private MsgNotifyReceiver mnr;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "网络状态改变");

        boolean success = false;

        //获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        // State state = connManager.getActiveNetworkInfo().getState();
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
        if (NetworkInfo.State.CONNECTED == state) { // 判断是否正在使用WIFI网络
            success = true;
            Log.i("NetBroadCast","正在使用wifi网络！");
            mnr.handleMsg(new BaseMessage(MsgType.NET, MsgEnums.NET_WIFI_CONNECTED, "wifi已连接"));
        }

        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
        if (NetworkInfo.State.CONNECTED == state) { // 判断是否正在使用GPRS网络
            success = true;
            Log.i("NetBroadCast","正在使用移动网络！");
            mnr.handleMsg(new BaseMessage(MsgType.NET,MsgEnums.NET_MOBILE_CONNECTED,"移动网络已连接，建议更换wifi网络！"));
        }

        if (!success) {
//            Toast.makeText(LocationMapActivity.this, "您的网络连接已中断", Toast.LENGTH_LONG).show();
            Log.i("NetBroadCast","网络已断开！");
            mnr.handleMsg(new BaseMessage(MsgType.NET,MsgEnums.NET_DISCONNECTED,"网络不给力，请检查网络连接！"));
        }
    }

    @Override
    public void setNotifyReceiver(MsgNotifyReceiver mnr) {
        this.mnr = mnr;
    }
}
