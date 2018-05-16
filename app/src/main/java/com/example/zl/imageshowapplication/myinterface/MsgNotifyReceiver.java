package com.example.zl.imageshowapplication.myinterface;

import com.example.zl.imageshowapplication.Message.BaseMessage;

/**
 * Created by Administrator on 2018/5/15.
 */

public interface MsgNotifyReceiver {

    void handleMsg(BaseMessage msg);

}
