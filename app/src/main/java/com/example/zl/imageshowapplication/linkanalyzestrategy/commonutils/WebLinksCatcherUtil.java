package com.example.zl.imageshowapplication.linkanalyzestrategy.commonutils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by ZhongLeiDev 0n 2018/05/17
 * 通用URL访问解析工具
 */
public class WebLinksCatcherUtil {


	private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
}

private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
}


/**
* post方式请求服务器(https协议)
* @param url
*            请求地址
* @param charset
*            编码
* @return
* @throws NoSuchAlgorithmException
* @throws KeyManagementException
* @throws IOException
*/
public static String httpGet(String url, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setRequestProperty("(Request-Line)", "GET /coser/detail/22300/1306028 HTTP/1.1");
        conn.setRequestProperty("Host", "bcy.net");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        conn.setRequestProperty("Accept-Charset", "utf-8");
        conn.setRequestProperty("Cookie", "lang_set=zh; UM_distinctid=15bd8ce7c8ec5-04465009e61d7c-47534130-100200-15bd8ce7c8f44; CNZZDATA1257708097=654832210-1493988246-null%7C1498519090; Hm_lvt_330d168f9714e3aa16c5661e62c00232=1498136167,1498436562,1498455409,1498523038; LOGGED_USER=D6IjGx2qbuWq517dqCPw66M%3D%3AdPBni5q3g%2BdcsCKzWbH1QQ%3D%3D; Hm_lpvt_330d168f9714e3aa16c5661e62c00232=1498523174; aliyungf_tc=AQAAAHbP7QQzLw0AhYVq2ubRL7p1u2GQ; acw_tc=AQAAAAU9XxjgDA8AhYVq2mgUyGdTqXzo; PHPSESSID=c1h36oe2s20dr7m8p1otjstqt3; mobile_set=no");
        conn.setDoInput(true);
        conn.setConnectTimeout(40000);
        conn.setReadTimeout(40000);
        InputStream is = conn.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
        String line = "";
        String result = "";
        while(null != (line=br.readLine())){
            result += line;
        }
            is.close();
        return result;
}

/**
 * 以Byte数组的形式获取Get操作结果
 * @param url 用Get方法访问所获取的结果
 * @return
 * @throws NoSuchAlgorithmException
 * @throws KeyManagementException
 * @throws IOException
 */
public static byte[] httpGetByte(String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
    URL console = new URL(url);
    HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
    conn.setSSLSocketFactory(sc.getSocketFactory());
    conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");
    conn.setDoInput(true);
    conn.setConnectTimeout(40000);
    conn.setReadTimeout(40000);
    InputStream is = conn.getInputStream();
    if (is != null) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        is.close();
        return outStream.toByteArray();
    }
    return null;
}

    /**
     * 获取链接内容并以UTF-8编码返回
     * @param srcurl
     * @return
     */
    public static String getLinkSrc(String srcurl) {
    byte[] b = null;
    String src = null;
    try {

        b = httpGetByte(srcurl);
        src = new String(b, "utf-8");

    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    } catch (KeyManagementException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return src;
}

}
