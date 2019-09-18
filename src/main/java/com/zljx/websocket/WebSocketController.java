package com.zljx.websocket;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zljx.mapper.MessageUserMapper;
import com.zljx.pojo.MessageUser;
import com.zljx.vo.ClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * websocket 消息推送(个人和广播)
 */
@Controller
public class WebSocketController {

	@Autowired
	private SocketServer socketServer;

	@Autowired
	private MessageUserMapper messageUserMapper;

	/**
	 *
	 * 客户端页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ke")
	public String idnex() {

		return "Ke";
	}





	/**
	 * @describe 当进入聊天的框的时候，随机生成用户名称, 连接到服务端，并且发送一条问候消息!
	 * @params []
	 * @return java.lang.String
	 */
	@RequestMapping("getUserName")
	@ResponseBody
	public Map<String,String> setUserName(HttpServletRequest request){
		String clientIp = ClientUtil.getClientIp(request);

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("ip",clientIp);
		MessageUser userDB = messageUserMapper.selectOne(queryWrapper);

		Map<String,String> map = new HashMap<>();
		System.out.println("clientIp"+clientIp);

		//如果ip一样就使用之前的名称
		if(!StringUtils.isEmpty(userDB) && clientIp.equals(userDB.getIp())){
			map.put("userName",userDB.getName());
		}else{
			String userName = "用户"+getRandomString()+clientIp.substring(clientIp.lastIndexOf(".")+1);
			System.out.println(clientIp);
			//将用户的名称 ip放入 messageUser表
			MessageUser user = new MessageUser();
			user.setName(userName);
			user.setIp(clientIp);
			user.setCreated(new Date());
			user.setUpdated(user.getCreated());
			messageUserMapper.insertUser(user);
			map.put("userName",userName);
		}
		return map;
	}

	public static String getRandomString(){
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<3;i++){
			int number=random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
