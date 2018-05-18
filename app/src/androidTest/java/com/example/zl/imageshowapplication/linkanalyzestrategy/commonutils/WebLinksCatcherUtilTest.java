package com.example.zl.imageshowapplication.linkanalyzestrategy.commonutils;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ZhongLeiDev on 2018/5/18.
 */
public class WebLinksCatcherUtilTest {
    @Test
    public void getHTTPSLinkSrc() throws Exception {
        String str = WebLinksCatcherUtil.getHTTPSLinkSrc(ServerLinkUrls.BCY_WHICHDAY_TOPPOST100 + "20180506");
        Log.i("HTTPS:", str);
        Assert.assertNotNull(str);
    }

    @Test
    public void getHTTPLinkSrc() throws Exception {
//        String str = WebLinksCatcherUtil.getHTTPLinkSrc();

    }


}