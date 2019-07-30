package com.zljx.service;

import com.zljx.pojo.Admin;
import com.zljx.pojo.Cart;
import com.zljx.pojo.Work;
import com.zljx.vo.SysResultLay;

import java.util.List;

public interface ManageService {

    List<Cart> findCartLike(int page, int limit, String pName);

    Admin doLogin(Admin admin);

    SysResultLay addCartAll(Cart cart);

    int deletCarts(Integer[] ids);

    Cart findCartOne(Long id);

    SysResultLay UpdateCart(Cart cart);

    List<Work> findWorkLike(int page, int limit, String pName);

    Work findWorkOne(Long id);

    SysResultLay addWork(Work work);

    SysResultLay UpdateWork(Work work);

    int deletWorks(Integer[] ids);
}
