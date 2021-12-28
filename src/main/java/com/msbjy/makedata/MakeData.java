package com.msbjy.makedata;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;

/**
 * 模拟访问数据采集端口，数据采集端口采集数据
 */
public class MakeData {
    public static void main(String[] args) {
        //测试公司的API接口，将json当做一个字符串传入httppost的请求体
        String logType = "userLoginLog";
//        String url="http://mynode5:8686/collector/common/"+logType;
        String url="http://localhost:8686/collector/common/"+logType;
        String result = null;
        HttpClient client = HttpClients.createDefault();
        try {
            int i = 0;
            while(i<10){
                HttpPost post = new HttpPost(url);
                //设置请求头
                post.setHeader("Content-Type", "application/json");

                String body = "[{'id':"+i+",'name':'zhangsan','age':14,'gender':'男'},{'name':'lisi','age':15,'gender':'女'}]";
                //设置请求体
                post.setEntity(new StringEntity(body,Charset.forName("UTF-8")));
                Thread.sleep(2000);
                i++;

                //获取返回信息
                HttpResponse response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                result = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            System.out.println(result);
        }
        } catch (Exception e) {
            System.out.println("接口请求失败"+e.getStackTrace());
        }

    }
}
