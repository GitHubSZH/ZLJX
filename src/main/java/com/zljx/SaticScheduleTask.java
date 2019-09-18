package com.zljx;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zljx.vo.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: sunzhihan
 * @Date：2019/08/19 9:30
 * @Version 1.0
 **/
@Component
@Configuration
@EnableScheduling
public class SaticScheduleTask {




    @Scheduled( cron = "0 30 10,18 * * ?")
    public void yipao(){
        String PYurl = "http://user.yipaoapp.com/run/updateUserRunNum";
        String p = "{\"accessToken\":\"1137924760114696192e9eb763f8e1e415298cf697e3af6a30e\",\"calories\":\"17.200\",\"newQuantity\":\"11000\"}";
        String post = HttpUtils.post(PYurl, p);
        Yp yp = ObjectMapperUtil.toObejct(post, Yp.class);
        if("10000".equals(yp)){
            HuaDou huaDou = yp.getResp();
            if(huaDou.getHuaDou()>=100){
                String buyXieurl = "http://user.yipaoapp.com/userEquip/exchangeEquipment";
                String buyXieJson = "{\"accessToken\":\"1137924760114696192e9eb763f8e1e415298cf697e3af6a30e\",\"equipId\":\"3\",\"number\":\"1\",\"payPwd\":\"225588\"}";
                String buyXieRespone = HttpUtils.post(buyXieurl, buyXieJson);
                SendEmailQQ.sendEmail(buyXieRespone);
            }
        }
        SendEmailQQ.sendEmail(post);
    }

    public static void main(String[] args) throws IOException {
        String json = "{\"code\":\"9999\",\"msg\":\"已达每日计算上限4300步\",\"sign\":null,\"success\":true,\"resp\":null}";

        Yp hashMap = ObjectMapperUtil.toObejct(json, Yp.class);
        System.out.println(hashMap);

        if(hashMap.getCode().equals("10000")){
            HuaDou resp = hashMap.getResp();
            System.out.println(resp.getHuaDou());
        }




     /*   String replace = String.valueOf(code1).replace("=", ":");
        HuaDou huaDou = ObjectMapperUtil.toObejct(replace, HuaDou.class);
        System.out.println(huaDou.getHuaDou());*/

     /*   Map<String,String> map = new HashMap<String,String>();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        map = mapper.readValue(code1,
                new TypeReference<HashMap<String,String>>(){});
        System.out.println(map);
*/



    }

}
