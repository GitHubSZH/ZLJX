package com.zljx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.zljx.constant.Status;
import com.zljx.mapper.CartMapperMange;
import com.zljx.pojo.Admin;
import com.zljx.pojo.Cart;
import com.zljx.service.ManageService;
import com.zljx.vo.HttpClientService;
import com.zljx.vo.ObjectMapperUtil;
import com.zljx.vo.SysResult;
import com.zljx.vo.SysResultLay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private HttpClientService httpClientService;

    @Autowired
    private CartMapperMange cartMapper;


    @Override
    public List<Cart> findCartLike(int page, int limit, String pName) {
        PageHelper.startPage(page,limit);
        return cartMapper.findAll(pName);
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

    @Override
    public SysResultLay addCartAll(Cart cart) {
        //这让吊车名称自增 类似于吊车1，吊车2.
        /*String cartType  = cart.getCartType();
        Map<String,Object> map = cartMapper.findMAXId(cartType);
        System.out.println(map);
        if(cartType.trim().equals("汽车吊")){
            setCartName(cart, map,"汽车吊");
        }else{
            setCartName(cart, map,"随车吊");
        }*/
        if(StringUtils.isEmpty(cart.getCartName())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"吊车名称不能为空！");
        }
        if(StringUtils.isEmpty(cart.getCartType())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"吊车类型不能为空！");
        }
        if(StringUtils.isEmpty(cart.getCartSize())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"吊车大小不能为空！");
        }
        cartMapper.insertCart(cart);
        return SysResultLay.msg("添加吊车信息成功！");
    }

    private void setCartName(Cart cart, Map<String, Object> map,String cartType) {
        if(!Objects.isNull(map)&&!StringUtils.isEmpty(map.get("id"))){
           Long cartId = (Long) map.get("id");
            Cart cartOne = findCartOne(cartId);
            String name = cartOne.getCartName();
            String[] split = name.split(cartType);
            int nameId = Integer.valueOf(split[1]);
            nameId += 1;
            cart.setCartName(cartType+nameId);
        }else{
            cart.setCartName(cartType+1);
        }
    }

    @Override
    public int deletCarts(Integer[] ids) {
        List list = Arrays.asList(ids);
        int influence =  cartMapper.deleteBatchIds(list);
        return influence;
    }

    @Override
    public Cart findCartOne(Long id) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",id);
        return cartMapper.selectOne(queryWrapper);
    }

    @Override
    public SysResultLay UpdateCart(Cart cart) {
        if(StringUtils.isEmpty(cart.getCartName())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改吊车名称不能为空！");
        }
        if(StringUtils.isEmpty(cart.getCartType())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改吊车类型不能为空！");
        }
        if(StringUtils.isEmpty(cart.getCartSize())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改吊车大小不能为空！");
        }
        cartMapper.updateById(cart);
        return SysResultLay.msg("修改吊车信息成功！");
    }
}
