package com.example.zl.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.zl.imageshowapplication.broadcast.NetBroadCast;
import com.example.zl.imageshowapplication.message.BaseMessage;
import com.example.zl.imageshowapplication.message.MsgEnums;
import com.example.zl.imageshowapplication.message.MsgType;
import com.example.zl.imageshowapplication.myinterface.MsgNotifyReceiver;

import org.simple.eventbus.EventBus;

/**
 * Created by ZhongLeiDev on 2018/9/18.
 */

public class NetworkStatusService extends Service implements MsgNotifyReceiver{

    private NetBroadCast netBroadCast;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        netBroadCast = new NetBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册网络状态变更广播
        registerReceiver(netBroadCast,filter);
        netBroadCast.setNotifyReceiver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除注册
        unregisterReceiver(netBroadCast);
    }

    @Override
    public void handleMsg(BaseMessage msg) {
        switch (msg.getMsgType()) {
            case NET:
                switch (msg.getMsg()) {
                    case NET_WIFI_CONNECTED:    //wifi连接
                        Log.i("NetWorkService","NET_WIFI_CONNECTED！");
                        EventBus.getDefault().post(new BaseMessage(MsgType.NET, MsgEnums.NET_WIFI_CONNECTED, "WiFi已连接！"), "net_status");
                        break;
                    case NET_MOBILE_CONNECTED:  //移动网络连接
                        Log.i("NetWorkService","NET_MOBILE_CONNECTED！");
                        EventBus.getDefault().post(new BaseMessage(MsgType.NET, MsgEnums.NET_MOBILE_CONNECTED, "已连接移动网络，注意流量使用！"), "net_status");
                        break;
                    case NET_DISCONNECTED:  //网络断开
                        Log.i("NetWorkService","NET_DISCONNECTED！");
                        EventBus.getDefault().post(new BaseMessage(MsgType.NET, MsgEnums.NET_DISCONNECTED, "网络已断开！"), "net_status");
                        break;
                }
                break;
        }
    }
}
