package com.zljx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.zljx.constant.Status;
import com.zljx.mapper.CartMapperMange;
import com.zljx.mapper.WorkMapperManage;
import com.zljx.pojo.Admin;
import com.zljx.pojo.Cart;
import com.zljx.pojo.Work;
import com.zljx.service.ManageService;
import com.zljx.vo.SysResultLay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ManageServiceImpl implements ManageService {


    @Autowired
    private CartMapperMange cartMapper;

    @Autowired
    private WorkMapperManage workMapper;


    @Override
    public List<Cart> findCartLike(int page, int limit, String pName) {
        PageHelper.startPage(page,limit);
        return cartMapper.findAll(pName);
    }

    @Override
    public Admin doLogin(Admin admin) {
     /*   String url = "http://localhost:8081/login/doLogin";
        Map<String,String> params = new HashMap<>();
        params.put("username",admin.getUsername());
        params.put("password",admin.getPassword());
        String json = httpClientService.doPost(url, params);
        SysResult result = ObjectMapperUtil.toObejct(json, SysResult.class);
        return (Admin) result.getData();*/
     return null;
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


    /** 合作的后台查询所有 */
    @Override
    public List<Work> findWorkLike(int page, int limit, String pName) {
        PageHelper.startPage(page,limit);
        return workMapper.findWorkLike(pName);
    }

    /**
     * @describe   合作的后台查询单条信息
     * @params [id]
     * @return com.zljx.pojo.Work
     */
    @Override
    public Work findWorkOne(Long id) {
        QueryWrapper<Work> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return workMapper.selectOne(queryWrapper);
    }

    /**
     * @describe   添加合作信息
     * @params [work]
     * @return com.zljx.vo.SysResultLay
     */
    @Override
    public SysResultLay addWork(Work work) {
        if(StringUtils.isEmpty(work.getWorkName())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"添加合作名称不能为空！");
        }
        if(StringUtils.isEmpty(work.getWorkAddr())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"添加合作地址不能为空！");
        }
        if(StringUtils.isEmpty(work.getWorkDesc())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"添加合作描述不能为空！");
        }
        workMapper.insertWork(work);
        return SysResultLay.msg("添加合作信息成功！");
    }

    /**
     * @describe  添加吊车信息
     * @params [work]
     * @return com.zljx.vo.SysResultLay
     */
    @Override
    public SysResultLay UpdateWork(Work work) {
        if(StringUtils.isEmpty(work.getWorkName())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改合作名称不能为空！");
        }
        if(StringUtils.isEmpty(work.getWorkAddr())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改合作地址不能为空！");
        }
        if(StringUtils.isEmpty(work.getWorkDesc())){
            return  SysResultLay.build(Status.PARAMETER_EXCEPTION,"修改合作描述不能为空！");
        }
        workMapper.updateById(work);
        return SysResultLay.msg("修改合作信息成功！");
    }

    /**
     * @describe 批量删除合作信息
     * @params [ids]
     * @return int
     */
    @Override
    public int deletWorks(Integer[] ids) {
        return workMapper.deleteBatchIds(Arrays.asList(ids));
    }
}
