package com.zljx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zljx.pojo.Cart;
import com.zljx.pojo.MessageUser;

/**
 * @Author: sunzhihan
 * @Date：2019/07/17 11:15
 * @Version 1.0
 **/
public interface MessageUserMapper  extends BaseMapper<MessageUser> {
    void insertUser(MessageUser user);
}
