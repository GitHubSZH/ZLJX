package com.zljx.controller;

import com.zljx.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;





    @RequestMapping("/findCartAll")
    public String findCartAll(){
        String json = manageService.findCartAll();
        return json;
        //JsonLaiUIResult cart = ObjectMapperUtil.toObejct(json, JsonLaiUIResult.class);
        /*JsonLaiUIResult jsonLaiUIResult = new JsonLaiUIResult(cart);
        return jsonLaiUIResult;*/
    }

}
