/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.domain.vo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.Authenticator;

/**
 *
 * @author changshu.li
 */
public class MailConfig {

	private String senderName;
	private String senderAddress;
	private Authenticator authenticator;
	private String hostName;
	private Integer smtpPort;
	private boolean SSLOnConnect;
	private String serverName;

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public boolean isSSLOnConnect() {
		return SSLOnConnect;
	}

	public void setSSLOnConnect(boolean SSLOnConnect) {
		this.SSLOnConnect = SSLOnConnect;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Map<String, String> getCommonParam() {
		Map<String, String> map = new HashMap();
		Date date = new Date();
		String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		String[] arr = datetime.split(" ");
		map.put("datetime", datetime);
		map.put("date", arr[0]);
		map.put("time", arr[1]);
		map.put("serverName", serverName);
		return Collections.unmodifiableMap(map);
	}
}
