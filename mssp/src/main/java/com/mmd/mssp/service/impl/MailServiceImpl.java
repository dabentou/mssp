package com.mmd.mssp.service.impl;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.vo.MailFormat;
import com.mmd.mssp.service.MailService;

@Service("mailService")
public class MailServiceImpl implements MailService {

	public static final Log logger = LogFactory.getLog(MailServiceImpl.class);

	public void sendHTMLMail( String receiver, String title, String content) {
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.qq.com");
		email.setSSLOnConnect(true);
		email.setSmtpPort(465);
		//email.setAuthenticator(newAuthenticator);
		email.setAuthentication("1075993538@qq.com", "123");
		try {
			email.addTo("3212535948@qq.com", "");
			email.setFrom("1075993538@qq.com", receiver);
			email.setSubject(title);
			email.setMsg(content);
			email.send();
		} catch (EmailException e) {
			throw new RuntimeException(e);// TODO Auto-generated catch block
		}

	}
	
	public static void main(String[] args) {
		new MailServiceImpl().sendHTMLMail("测试", "测试标题", "内同修改");
	}
	
	@Override
	public void sendHTMLMail(MailFormat mailFormat, String receiverAddress,String[] filePath, Map<String, String> paramMap) {
		HtmlEmail email = new HtmlEmail();
		if (mailFormat.getHostName() != null) {
			email.setHostName(mailFormat.getHostName());
		}
		if (mailFormat.isSSLOnConnect()) {
			email.setSSLOnConnect(true);
		}
		if (mailFormat.getSmtpPort() != null) {
			email.setSmtpPort(mailFormat.getSmtpPort());
		}
		if (mailFormat.getAuthenticator() != null) {
			email.setAuthenticator(mailFormat.getAuthenticator());
		}
		try {
			if(filePath!=null){//添加附件
				for(int i=0; i<filePath.length; i++){
					File file = new File(filePath[i]);
					if(file.exists()){
						if(!file.isDirectory()){
							EmailAttachment attachment = new EmailAttachment();
							attachment.setPath(filePath[i]);
							email.attach(attachment);
						}
					}
				}
			}
			email.setFrom(mailFormat.getSenderAddress(), mailFormat.getSenderName());
			email.addTo(receiverAddress, null);
			email.setSubject(mailFormat.getTitle());
			String str = mailFormat.getContent();
			paramMap.putAll(mailFormat.getConfig().getCommonParam());
			for (Map.Entry<String, String> param : paramMap.entrySet()) {
				String key = param.getKey();
				key = "${" + key + "}";
				str = str.replace(key, param.getValue());
			}
			email.setCharset("UTF-8");
			email.setHtmlMsg(str);
			logger.info(String.format("send email [%s], Content size [%d]", mailFormat.getSenderAddress(), str.length()));
			if (logger.isDebugEnabled()) {
				logger.debug(paramMap);
			} else {
				email.send();
			}
		} catch (EmailException e) {
			throw new RuntimeException(e);
		}
	}
}
