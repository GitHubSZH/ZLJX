package com.zljx.controller;

import com.zljx.vo.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    @Autowired
    private HttpClientService httpClientService;


 /*   @RequestMapping("/upload")
    public PicUploadResult upload(){
        String url = "http://localhost:8081/manage/upload";
        String json = httpClientService.doPost(url);
        System.out.println(json);
        PicUploadResult picUploadResult = ObjectMapperUtil.toObejct(json, PicUploadResult.class);
        return  picUploadResult;
    }*/
}
