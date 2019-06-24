package com.zljx.service;

import com.zljx.pojo.Admin;
import com.zljx.pojo.Cart;
import com.zljx.vo.SysResultLay;

import java.util.List;

public interface ManageService {

    List<Cart> findCartLike(int page, int limit, String pName);

    Admin doLogin(Admin admin);

    SysResultLay addCartAll(Cart cart);

    int deletCarts(Integer[] ids);

    Cart findCartOne(Long id);

    SysResultLay UpdateCart(Cart cart);
}
