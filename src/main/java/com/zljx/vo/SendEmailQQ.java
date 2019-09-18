package com.zljx.vo;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * @Author: sunzhihan
 * @Date：2019/08/19 11:16
 * @Version 1.0
 **/
public class SendEmailQQ {

    public  static  void sendEmail(String msg) {
        try {
            HtmlEmail email = new HtmlEmail();//创建电子邮件对象
            email.setSSL(true);
            email.setDebug(true);
            email.setHostName("SMTP.qq.com");//设置发送电子邮件使用的服务器主机名
            email.setSmtpPort(587);//设置发送电子邮件使用的邮件服务器的TCP端口地址
            email.setAuthenticator(new DefaultAuthenticator("1242924870", "ohhfokgbsenehcja"));//邮件服务器身份验证
            email.setFrom("1242924870@qq.com");//设置发信人邮箱
            email.setSubject("亦跑");//设置邮件主题
            email.setMsg(msg);//设置邮件文本内容
            email.addTo("1242924870@qq.com");//设置收件人
            //EmailAttachment attach = new EmailAttachment();//附件对象
            // attach.setPath("C:/temp/wenzhi.doc");//附件文件在系统中的路径
           // attach.setDescription(EmailAttachment.ATTACHMENT);
           // email.attach(attach);//添加附件
            email.send();//发送邮件
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }

}
