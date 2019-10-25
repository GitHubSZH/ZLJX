package com.zljx.vo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http请求工具
 * @author pengsijia
 */
public class HttpUtils {

    public static String get(String url) {
        // 获取http客户端
        CloseableHttpClient client = HttpClients.createDefault();
        // 通过httpget方式来实现我们的get请求
        HttpGet httpGet = new HttpGet(url);
        // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了
        CloseableHttpResponse Response = null;
        try {
            Response = client.execute(httpGet);
            // 所有的响应的数据，也全部都是封装在HttpEntity里面
            HttpEntity entity = Response.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            String str = EntityUtils.toString(entity, "UTF-8");
            // EntityUtils.toString(entity)
            System.out.println(str);
            // 关闭
            Response.close();
            return str;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String post(String url, String params){
        /*MallOrderRequestModel model = new MallOrderRequestModel(orderId,"0f906811-e15B-4081-9947-7a1bc8Cb4e2B");
        String params = JSON.toJSON(model).toString();*/
        System.out.println("请求参数："+params);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        CloseableHttpResponse response = null;
        try {
            StringEntity s = new StringEntity(params, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            //设置参数到请求对象中
            httpPost.setEntity(s);
            response = httpclient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String result = EntityUtils.toString(responseEntity);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return "";
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String json = "{\"List\":[{\"AGE\":24,\"C_NAME\":\"培优\",\"NAME\":\"Jack\",\"T_NAME\":\"张雪峰\"},{\"AGE\":24,\"C_NAME\":\"就业\",\"NAME\":\"Jack\",\"T_NAME\":\"王海涛\"}]}";
        Map map = ObjectMapperUtil.toObejct(json, Map.class);
        List list = (List)map.get("List");
        Map result = new HashMap();

        List cName = new ArrayList();
        List TName = new ArrayList();

        for (int i=0;i<list.size();i++){

            Map o = (Map)list.get(i);

            result.put("AGE",o.get("AGE"));
            result.put("NAME",o.get("NAME"));
            cName.add(o.get("C_NAME"));
            TName.add(o.get("T_NAME"));

        }

        result.put("C_NAME",cName);
        result.put("T_NAME",TName);

        System.out.println(result);
    }
}
