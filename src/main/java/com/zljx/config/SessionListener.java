package com.zljx.config;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;



public class SessionListener implements HttpSessionListener {  

	private MySessionContext myc = MySessionContext.getInstance();  

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {  
		HttpSession session = httpSessionEvent.getSession();  
		System.out.println("session创建了: "+session.getId());
		myc.addSession(session);  
	} 

	/**
	 * session销毁执行数据库自动清除操作
	 */
		
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {  
		HttpSession session = httpSessionEvent.getSession();  
		System.out.println("session销毁了: "+session.getId());
		myc.delSession(session);  
/*		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
		KwUserDao dao = ctx.getBean("kwUserDao",KwUserDao.class);
		dao.upecc(session.getId());
		ctx.close();*/
	}  

}  