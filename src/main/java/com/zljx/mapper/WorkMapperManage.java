package com.zljx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zljx.pojo.Work;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: sunzhihan
 * @Date：2019/06/25 10:18
 * @Version 1.0
 **/
public interface WorkMapperManage extends BaseMapper<Work> {
    List<Work> findWorkLike(@Param("pName") String pName);

    void insertWork(Work work);
}
