package com.mmd.mssp.service;

import java.util.Map;

import com.mmd.mssp.domain.vo.MailFormat;

public interface MailService {
	
	/**
	 * 发送邮件(带附件)
	 * @param mailFormat 邮件格式(html)
	 * @param receiverAddress 收件人
	 * @param filePath 附件路径(无附件传null)
	 * @param map 参数
	 */
	public void sendHTMLMail(MailFormat mailFormat,String receiverAddress,String[] filePath,Map<String,String> map);
}
