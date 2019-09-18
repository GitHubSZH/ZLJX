package com.zljx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zljx.pojo.Cart;
import com.zljx.pojo.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
public interface CartMapperWeb extends BaseMapper<Cart> {
    List<Cart> selectListLimt(@Param("type") String type);

    Cart webPre(@Param("id")Long id, @Param("type")String type);

    Cart webNext(@Param("id")Long id, @Param("type")String type);

    List<Work> selectWorkListLimt();

}
