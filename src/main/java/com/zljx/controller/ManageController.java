package com.zljx.controller;

import com.zljx.pojo.Cart;
import com.zljx.service.ManageService;
import com.zljx.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;


    @RequestMapping("/findCartAll")
    public SysResult findCartAll(){
       List<Cart> list = manageService.findCartAll();
        return SysResult.oK(list);
    }

}
