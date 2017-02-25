package com.example.administrator.wechat.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.wechat.R;
import com.example.administrator.wechat.tools.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bryant on 2017/2/25.
 */

public class RegistActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "RegistActivity";
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initViews();
        initDatas();
    }

    private void initDatas() {
        //假设填的用户名和密码为下面的
        username = "abc";
        password = "cba";
    }

    private void initViews() {
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            httpByGet();
                        } catch (IOException e) {
                            Log.d(TAG,"Get发生IO异常！");
                            e.printStackTrace();
                            return;
                        }

                    }
                }).start();
               break;
            case R.id.btn_post:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            httpByPost();
                        } catch (IOException e) {
                            Log.d(TAG,"Post发生IO异常！");
                            e.printStackTrace();
                            return;
                        }    
                    }
                }).start();
                break;
        }
    }

    private int httpByGet() throws IOException {

        String getUrl = Constants.baidu+"registServlet/"
                +"username="+username
                +"&"
                +"password="+password;
        HttpGet get = new HttpGet(getUrl);
        HttpClient client = getClient();
        HttpResponse response = client.execute(get);
        if(response.getStatusLine().getStatusCode() == 200){
            Log.d(TAG,"服务器返回信息："+response.getEntity().toString());
            return 0;
        }else{
            Log.d(TAG,"get请求失败，失败码:"+response.getStatusLine().getStatusCode());
            return response.getStatusLine().getStatusCode();
        }

    }
    private int httpByPost() throws IOException {
        String postUrl = Constants.baidu+"registServlet/";
        HttpPost post = new HttpPost(postUrl);
        HttpClient client = getClient();
        List<NameValuePair> pairs = new ArrayList<>();
        BasicNameValuePair pair1 = new BasicNameValuePair("username",username);
        BasicNameValuePair pair2 = new BasicNameValuePair("password",password);
        pairs.add(pair1);
        pairs.add(pair2);
        post.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
        HttpResponse response = client.execute(post);
        if(response.getStatusLine().getStatusCode() == 200){
            Log.d(TAG,"服务器返回信息："+response.getEntity().toString());
            return 0;
        }else{
            Log.d(TAG,"get请求失败，失败码:"+response.getStatusLine().getStatusCode());
            return response.getStatusLine().getStatusCode();
        }

    }

    private HttpClient getClient() {
        BasicHttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params,5000);
        HttpConnectionParams.setSoTimeout(params,8000);
        return new DefaultHttpClient(params);
    }
}
