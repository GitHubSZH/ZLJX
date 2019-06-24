package com.zljx.controller;

import com.github.pagehelper.Page;
import com.zljx.constant.Status;
import com.zljx.pojo.Cart;
import com.zljx.service.ManageService;
import com.zljx.vo.ObjectMapperUtil;
import com.zljx.vo.SysResultLay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageService manageService;


    /** 吊车列表的查询和模糊查询 */
    @RequestMapping(value = "/findCartAll",method = RequestMethod.GET)
    public SysResultLay findCartAll(int page,int limit,String pName){
        try {
            if(StringUtils.isEmpty(pName)){
                pName="";
            }
            List<Cart> all = manageService.findCartLike(page,limit,pName);
            Page<Cart> cartPage = (Page<Cart>) all;
            return  new SysResultLay(0,"查询成功", (int) cartPage.getTotal(),cartPage.getResult());
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"查询失败");
    }

    /** 查询单个吊车信息，为修改做准备 */
    @RequestMapping(value = "/findCartOne/{cartId}",method = RequestMethod.GET)
    public SysResultLay findCartOne(@PathVariable("cartId") Long id){
        try {
            if(id<=0&&StringUtils.isEmpty(id)){
                return SysResultLay.build(Status.PARAMETER_EXCEPTION,"吊车Id不能为空！");
            }
            Cart cart =   manageService.findCartOne(id);
            if(cart==null){
                return SysResultLay.build(Status.PARAMETER_EXCEPTION,"没有该条吊车信息！");
            }
            return SysResultLay.oK(cart);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"查询失败");
    }

    /** 添加吊车信息 */
    @RequestMapping(value = "/addCartAll",method = RequestMethod.POST)
    public SysResultLay addCart(Cart cart){
        try {
            System.out.println(cart);
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            SysResultLay resultLay = manageService.addCartAll(cart);
            return resultLay;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"添加失败！");
    }

    /** 修改吊车信息 */
    @RequestMapping(value = "/UpdateCart",method = RequestMethod.POST)
    public SysResultLay UpdateCart(Cart cart){
        System.out.println(cart);
        try {
            cart.setUpdated(new Date());
            SysResultLay resultLay = manageService.UpdateCart(cart);
            return resultLay;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"修改失败！");
    }


    /**
     * 批量删除吊车信息。
     * @return
     */
    @PostMapping("")
    public SysResultLay deleteCarts(Integer[] ids){
        try {
            //Integer[] ids =  ObjectMapperUtil.toObejct(cartIds,Integer[].class);
            int influence = manageService.deletCarts(ids);
            if (influence<0){
                return SysResultLay.build(Status.PARAMETER_EXCEPTION,"该记录已经被删除了！");
            }
            return SysResultLay.oK(null);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return SysResultLay.build(Status.SYSTEM_ERROR,"删除失败！");

    }



}
