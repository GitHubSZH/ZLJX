package com.zljx.service;

import com.zljx.pojo.Cart;
import com.zljx.vo.HttpClientService;
import com.zljx.vo.ObjectMapperUtil;
import com.zljx.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private HttpClientService httpClientService;

  /*  @Autowired
    private HttAPIService httAPIService;*/

    @Override
    public List<Cart> findCartAll() {
        String url = "http://localhost:8081/cart/findAll";
       /* System.out.println(httpClientService);
        String json = httpClientService.doPost(url);
        System.out.println(json);*/
        String json = null;
        try {
            json = httpClientService.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(httpClientService);
        System.out.println(json);
        SysResult sysResult = ObjectMapperUtil.toObejct(json, SysResult.class);
        return (List<Cart>) sysResult.getData();

    }
}
