package com.msbjy.makedata;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 读取 data目录下的用户登录机器日志，将登录数据生产到对应的日志采集接口中。
 */
public class ProdeceUserLoginLog {
    public static void main(String[] args) throws InterruptedException {
        String logType = "userLoginLog";
        String url="http://mynode5:8686/collector/common/"+logType;
//        String url="http://localhost:8686/collector/common/"+logType;
        String result = null; //调用接口返回结果
        HttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        //设置请求头
        post.setHeader("Content-Type", "application/json");


        //循环获取对应的日志数据，生产到对应的数据采集接口中
        while(true){
            InputStream in = null;
            BufferedReader br = null;
            try{
                /**
                 * 获取日志文件数据路径:
                 * ProdeceUserLoginLog.class.getClassLoader().getResource().getPath 获取的是编译后classes文件夹的根路径
                 */
                in=ProdeceUserLoginLog.class.getClassLoader().getResourceAsStream("userLoginInfos");
                br=new BufferedReader(new InputStreamReader(in));
                String line=br.readLine();
                while (line!=null){
                    //设置请求体
                    post.setEntity(new StringEntity("["+line+"]",Charset.forName("UTF-8")));
                    Thread.sleep(200);
                    //获取返回信息
                    HttpResponse response = client.execute(post);
                    if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                        result = EntityUtils.toString(response.getEntity(), "utf-8");
                    }
                    System.out.println("SpringBoot服务端返回结果："+result);
                    //将每条数据发送到对应的日志采集接口中，获取下一条数据
                    line=br.readLine();
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try {
                    br.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
