package com.zljx.controller;

import com.zljx.pojo.Work;
import com.zljx.vo.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * @Author: sunzhihan
 * @Date：2019/08/20 14:42
 * @Version 1.0
 **/
@RestController
public class Test01 {

    @Autowired
    private  HttpClientService httpClientService;


    @RequestMapping("/skr")
    public String test01(HttpServletRequest request, HttpServletResponse response) throws ParseException {



        List<Map<String, Object>> bh = UserReadExcelImpl.Bh();
        System.out.println(bh.size());
      /*  for (Map list :    bh) {
            String no = (String)list.get("skill_mrNo");
            String name = (String)list.get("skill_pName");
            String date111 = (String)list.get("skill_operDate");
            if(StringUtils.isEmpty(date111)){
                return date111+"date111";
            }
            String date = date111.replaceAll("/", "-").substring(0,date111.lastIndexOf(" "));

            //System.out.println(no+":"+name+":"+date);

           *//* Map<String,String> params = new HashMap();
            params.put("formFileName","SkillRegistry");
            params.put("schDeptFlow","39efc14b4e4f4fe183dd36c4aeb2594f");
            params.put("roleFlag","doctor");
            params.put("processFlow","2fecaadb97134156bf876b380c450ec5");
            params.put("xmlItemName","skill_operName");
            params.put("itemId","595b088c0e8448c68cb578fe99efe552");
            params.put("itemName"," X射线造影( 在二线指导下操作)");
            params.put("skill_pName",name);
            params.put("skill_operDate",date);
            params.put("skill_mrNo",no);
            params.put("skill_result","1");
            params.put("csrftoken","b2a55876ebc04a218d156b5ad18cfcdf");
            params.put("skill_result_name","是");

           String s = httpClientService.doPost("http://hb.ezhupei.com/pdsci/res/rec/saveRegistryForm",params);
            System.out.println(name+s);*//*
        }*/

        System.out.println("数据添加成功！");
        return "success";
    }

}
