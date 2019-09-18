package com.zljx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zljx.constant.Status;
import com.zljx.mapper.CartMapperWeb;
import com.zljx.mapper.WorkMapperWeb;
import com.zljx.pojo.Cart;
import com.zljx.pojo.Work;
import com.zljx.service.JDBCTemplate;
import com.zljx.vo.SysResult;
import com.zljx.vo.SysResultLay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/")
public class WebController {

    @Autowired
    private CartMapperWeb cartMapper;

    @Autowired
    private WorkMapperWeb workMapper;




    @RequestMapping("CartList")
    public SysResultLay webCartList(String cartType, Integer page){
      try {
          page = StringUtils.isEmpty(page)?1:page;
          Integer limit = 8;
          PageHelper.startPage(page,limit);
          String type = "汽车吊".equals(cartType)?"汽车吊":"随车吊";
          List<Cart> carts = cartMapper.selectListLimt(type);
          Page<Cart> cartPage = (Page<Cart>) carts;
          return new SysResultLay(0,"查询成功", (int) cartPage.getTotal(),cartPage.getResult());
      }catch (Exception e){
          e.printStackTrace();
      }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }

    @RequestMapping("WorkList")
    public SysResultLay webWorkList(Integer page){
        try {
            page = StringUtils.isEmpty(page)?1:page;
            Integer limit = 8;
            PageHelper.startPage(page,limit);
            List<Work> works = cartMapper.selectWorkListLimt();
            Page<Work> cartPage = (Page<Work>) works;
            return new SysResultLay(0,"查询成功", (int) cartPage.getTotal(),cartPage.getResult());
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }


    @RequestMapping("CartOne")
    public SysResultLay webCartOne(Long imageId){
      try {
          //Long imageId 为图片里的id 即为cartId
          QueryWrapper<Cart> queryWrapper = new QueryWrapper();
          queryWrapper.eq("id",imageId);
          return SysResultLay.oK(cartMapper.selectOne(queryWrapper));
      }catch (Exception e){
          e.printStackTrace();
      }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }


    @RequestMapping("Pre")
    public SysResultLay webPre(Long id,String type){
      try {
          if(StringUtils.isEmpty(type)){
              Work work = workMapper.webPre(id);
              return SysResultLay.oK(work);
          }else{
              Cart cart = cartMapper.webPre(id,type);
              return SysResultLay.oK(cart);
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }
    @RequestMapping("Next")
    public SysResultLay webNext(Long id,String type){
      try {
          if(StringUtils.isEmpty(type)){
              Work work = workMapper.webNext(id);
              return SysResultLay.oK(work);
          }else{
              Cart cart = cartMapper.webNext(id,type);
              return SysResultLay.oK(cart);
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }



    @RequestMapping("WorkOne")
    public SysResultLay webWorkOne(Long imageId){
        try {
            QueryWrapper<Work> queryWrapper = new QueryWrapper();
            queryWrapper.eq("id",imageId);
            return SysResultLay.oK(workMapper.selectOne(queryWrapper));
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"系统正在修复中请稍后尝试访问");
    }

   /* @RequestMapping("test01")
    public SysResult test01(@RequestBody Map<String,Object> map){
        System.out.println(map);
        List ids = Arrays.asList(new String[]{"15","47","46"});
        Map<String,Object> map1 = new HashMap<>();
        map1.put("ids",ids);
        System.out.println(map1);
        return  SysResult.oK(workMapper.selectForeach(map));


    }
    @PostMapping("test02")
    public SysResult test02(@RequestBody List<Work> work){
        System.out.println(work);
        System.out.println(work.size());
        workMapper.updatetest02(work);
        return  SysResult.oK();


    }*/


}
