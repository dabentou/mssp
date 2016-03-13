package com.mmd.mssp.domain.vo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.springframework.util.ResourceUtils;

public class PDFFormat {

	private String title;
	private String content;

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


}
