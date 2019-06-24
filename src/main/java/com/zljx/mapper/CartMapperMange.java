package com.zljx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zljx.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: sunzhihan
 * @Date：2019/06/19 18:33
 * @Version 1.0
 **/
@Mapper
public interface CartMapperMange extends BaseMapper<Cart> {

    public List<Cart> findAll(@Param("pName") String pName);

    void insertCart(Cart cart);

    Map<String, Object> findMAXId(@Param("cartType") String cartType);
}
