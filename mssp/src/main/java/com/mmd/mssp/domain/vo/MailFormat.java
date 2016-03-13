package com.mmd.mssp.domain.vo;

import java.io.File;
import java.io.IOException;

import javax.mail.Authenticator;
import org.apache.commons.io.FileUtils;

import org.springframework.util.ResourceUtils;

public class MailFormat {

	private MailConfig config;
	private String title;
	private String content;

	public MailFormat(MailConfig config) {
		if (config == null) {
			throw new IllegalArgumentException("config not null!");
		}
		this.config = config;
	}

	public String getSenderName() {
		return config.getSenderName();
	}

	public String getSenderAddress() {
		return config.getSenderAddress();
	}

	public Authenticator getAuthenticator() {
		return config.getAuthenticator();
	}

	public String getHostName() {
		return config.getHostName();
	}

	public Integer getSmtpPort() {
		return config.getSmtpPort();
	}

	public boolean isSSLOnConnect() {
		return config.isSSLOnConnect();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContentPath(String path) {
		try {
			File file = ResourceUtils.getFile(path);
			this.content = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public MailConfig getConfig() {
		return config;
	}

}
