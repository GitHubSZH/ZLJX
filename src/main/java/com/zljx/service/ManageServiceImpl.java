package com.zljx.service;

import com.zljx.vo.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
