package com.zljx.service;

import com.zljx.pojo.Admin;
import com.zljx.vo.HttpClientService;
import com.zljx.vo.ObjectMapperUtil;
import com.zljx.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private HttpClientService httpClientService;

  /*  @Autowired
    private HttAPIService httAPIService;*/

    @Override
    public String findCartAll() {
        String url = "http://localhost:8081/cart/findAll";
       /* System.out.println(httpClientService);
        String json = httpClientService.doPost(url);
        System.out.println(json);*/
        String json = httpClientService.doGet(url);
        return json;
    }

    @Override
    public Admin doLogin(Admin admin) {
        String url = "http://localhost:8081/login/doLogin";
        Map<String,String> params = new HashMap<>();
        params.put("username",admin.getUsername());
        params.put("password",admin.getPassword());
        String json = httpClientService.doPost(url, params);
        SysResult result = ObjectMapperUtil.toObejct(json, SysResult.class);
        return (Admin) result.getData();
    }
}
