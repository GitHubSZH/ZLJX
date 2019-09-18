package com.zljx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zljx.pojo.Cart;
import com.zljx.pojo.Work;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WorkMapperWeb extends BaseMapper<Work> {

    Work webPre(@Param("id") Long id);

    Work webNext(@Param("id") Long id);

    List<Work> selectWorkListLimt();

    /*List<Cart> selectForeach(Map<String,Object> map);*/

    /*void updatetest02(List<Work> work);*/
}
