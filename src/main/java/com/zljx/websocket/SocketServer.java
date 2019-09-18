package com.zljx.websocket;

import com.zljx.pojo.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/socketServer/{userName}")
@Component
public class SocketServer {

	private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

	/**
	 *
	 * 用线程安全的CopyOnWriteArraySet来存放客户端连接的信息
	 */
	// private static CopyOnWriteArraySet<Client> socketServers = new
	// CopyOnWriteArraySet<>();

	private static ConcurrentHashMap<String, Client> socketServers = new ConcurrentHashMap<String, Client>();

	public static ConcurrentHashMap<String, Client> getSocketServers() {
		return socketServers;
	}

	public static void setSocketServers(ConcurrentHashMap<String, Client> socketServers) {
		SocketServer.socketServers = socketServers;
	}

	/**
	 *
	 * websocket封装的session,信息推送，就是通过它来信息推送
	 */
	private Session session;

	/**
	 * 用来装登录用户Session的集合
	 */
	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

	public static ConcurrentHashMap<String, Session> getSessionMap() {
		return sessionMap;
	}

	public static void setSessionMap(ConcurrentHashMap<String, Session> sessionMap) {
		SocketServer.sessionMap = sessionMap;
	}

	/**
	 *
	 * 服务端的userName,因为用的是set，每个客户端的username必须不一样，否则会被覆盖。
	 * 要想完成ui界面聊天的功能，服务端也需要作为客户端来接收后台推送用户发送的信息
	 */
	private final static String SYS_USERNAME = "lau";

	/**
	 *
	 * 用户连接时触发，我们将其添加到 保存客户端连接信息的socketServers中
	 *
	 * @param session
	 * @param userName
	 */
	@OnOpen
	public void open(Session session, @PathParam(value = "userName") String userName) {
		// 业务逻辑:当用户登录进来时触发连接
		this.session = session;
		if (!socketServers.containsKey(userName)) {
			socketServers.put(userName, new Client(userName, session));
		} else {
			socketServers.put(userName, socketServers.get(userName));
		}
		sessionMap.put(userName, session);
		System.out.println(userName + "连接成功....");
		logger.info("客户端:【{}】连接成功", userName);

	}

	/**
	 *
	 * 收到客户端发送信息时触发 我们将其推送给客户端(lau) 其实也就是服务端本身，为了达到前端聊天效果才这么做的
	 *
	 * @param message
	 */
	@OnMessage
	public void onMessage(String message) {
		System.out.println("收到消息啦..................");
		Client clientUser = null;
		for (String client : socketServers.keySet()) {
			clientUser = socketServers.get(client);
			if (clientUser.getSession() == session) {
				sendMessage(clientUser.getUserName() + ":" + message, SYS_USERNAME);
				logger.info("客户端:【{}】发送信息:{}", clientUser.getUserName(), message);
			}
		}
	}

	/**
	 *
	 * 连接关闭触发，通过sessionId来移除 socketServers中客户端连接信息
	 */
	@OnClose
	public void onClose() {
		for (String item : socketServers.keySet()) {
			if (socketServers.get(item).getSession().getId().equals(session.getId())) {
				logger.info("客户端:【{}】断开连接", socketServers.get(item).getUserName());
				socketServers.remove(item, socketServers.get(item));
				sessionMap.remove(item, session);
			}
		}

	}

	/**
	 *
	 * 发生错误时触发
	 * 
	 * @param error
	 */
	@OnError
	public void onError(Throwable error) {

		for (String item : socketServers.keySet()) {
			if (socketServers.get(item).getSession().getId().equals(session.getId())) {
				logger.error("客户端:【{}】发生异常", socketServers.get(item).getUserName());
				socketServers.remove(item, socketServers.get(item));
				sessionMap.remove(item, session);
				error.printStackTrace();
			}
		}

	}

	/**
	 *
	 * 信息发送的方法，通过客户端的userName 拿到其对应的session，调用信息推送的方法
	 * 
	 * @param message
	 * @param userName
	 */
	public synchronized static void sendMessage(String message, String userName) {
		for (String client : socketServers.keySet()) {
			if (userName.equals(socketServers.get(client).getUserName())) {
				try {
					socketServers.get(client).getSession().getBasicRemote().sendText(message);
				} catch (IOException e) {

					e.printStackTrace();
				}
				logger.info("服务端推送给客户端 :【{}】", socketServers.get(client).getUserName(), message);
			}
		}
	}

	/**
	 *
	 * 获取服务端当前客户端的连接数量， 因为服务端本身也作为客户端接受信息， 所以连接总数还要减去服务端 本身的一个连接数
	 *
	 * 这里运用三元运算符是因为客户端第一次在加载的时候 客户端本身也没有进行连接，-1 就会出现总数为-1的情况， 这里主要就是为了避免出现连接数为-1的情况
	 *
	 * @return
	 */
	public synchronized static int getOnlineNum() {
		for (String item : socketServers.keySet()) {
			if (socketServers.get(item).getUserName().equals(SYS_USERNAME)) {
				System.out.println("服务器的名称是:" + item);
				socketServers.remove(item, socketServers.get(item));
			}
		}
		System.out.println("-----------------------当前连接数为" + socketServers.size());
		return socketServers.size();
	}

	/**
	 *
	 * 获取在线用户名，前端界面需要用到
	 * 
	 * @return
	 */
	public synchronized static List<String> getOnlineUsers() {
		List<String> onlineUsers = new ArrayList<String>();
		for (String item : socketServers.keySet()) {
			if (!socketServers.get(item).getUserName().equals(SYS_USERNAME)) {
				onlineUsers.add(socketServers.get(item).getUserName());
			}
		}
		return onlineUsers;
	}

	/**
	 *
	 * 信息群发，我们要排除服务端自己不接收到推送信息 所以我们在发送的时候将服务端排除掉
	 * 
	 * @param message
	 */
	public synchronized static void sendAll(String message) {
		System.out.println("......这是群发消息服务");
		for (String client : socketServers.keySet()) {
			// 群发，不能发送给服务端自己
			if (!socketServers.get(client).getUserName().equals(SYS_USERNAME)) {
				try {
					socketServers.get(client).getSession().getBasicRemote().sendText(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		logger.info("服务端推送给所有客户端 :【{}】", message);
	}

	/**
	 *
	 * 多个人发送给指定的几个用户
	 * 
	 * @param message
	 * @param persons
	 */
	public synchronized static void SendMany(String message, String[] persons) {
		for (String userName : persons) {
			sendMessage(message, userName);
		}
	}
}
