package com.mmd.mssp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

@Controller
public class KindEditorController {

	@RequestMapping(value="/editor/image/upload")
	@ResponseBody
	public String image(HttpServletRequest request,HttpServletResponse response, @RequestParam(value = "imgFile") MultipartFile[] mfile) throws FileUploadException, IOException{
		
		String savePath = request.getSession().getServletContext().getRealPath("WEB-INF") + File.separator+"upload"+ File.separator;  

		//文件保存目录URL
		String saveUrl  = request.getContextPath() + "/upload/";

		//定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

		//最大文件大小
		long maxSize = 1000000;

		response.setContentType("text/html; charset=UTF-8");
		if(!ServletFileUpload.isMultipartContent(request)){
			getError("请选择文件。");
		}
		//检查目录
		File uploadDir = new File(savePath);
		if(!uploadDir.isDirectory()){
			getError("上传目录不存在。");
		}
		//检查目录写权限
		if(!uploadDir.canWrite()){
			getError("上传目录没有写权限。");
		}

		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if(!extMap.containsKey(dirName)){
			getError("目录名不正确。");
		}
		//创建文件夹
		savePath += dirName + File.separator;
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
 		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd +  File.separator;
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		if (mfile != null) {
			for (int i = 0; i < mfile.length; i++) {
				String fileName = mfile[i].getOriginalFilename();
				// 检查扩展名
				String fileExt = fileName.substring(
						fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(","))
						.contains(fileExt)) {
					getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName)
							+ "格式。");
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_"
						+ new Random().nextInt(1000) + "." + fileExt;
				OutputStream out = null;
				try {
					File uploadedFile = new File(savePath, newFileName);
					out = new BufferedOutputStream(new FileOutputStream(
							uploadedFile));
					InputStream input = mfile[i].getInputStream();
					byte[] bts = new byte[1024];
					while (true) {
						int len = input.read(bts);
						if (len < 0) {
							break;
						}
						out.write(bts, 0, len);
					}
					out.close();
				} catch (Exception e) {
					getError("上传文件失败。");
				} finally {
					if (out != null) {
						out.close();
					}
				}

				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", saveUrl + newFileName);
				return obj.toJSONString();
			}
		}
		return "Errror!";
	}
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}

	@RequestMapping(value = "/editor/excel/upload")
	@ResponseBody
	public String file(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "excelFile") MultipartFile mfile)
			throws FileUploadException, IOException {
		File savePath = FileUtils.getFile(request.getSession()
				.getServletContext().getRealPath("/"), "WEB-INF", "upload");
		String fileName = mfile.getOriginalFilename();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1)
				.toLowerCase();
		String newFileName = UUID.randomUUID().toString() + "." + fileExt;
		OutputStream out = null;
		try {
			File uploadedFile = new File(savePath, newFileName);
			out = new BufferedOutputStream(new FileOutputStream(uploadedFile));
			InputStream input = mfile.getInputStream();
			byte[] bts = new byte[1024];
			while (true) {
				int len = input.read(bts);
				if (len < 0) {
					break;
				}
				out.write(bts, 0, len);
			}
			out.close();
		} catch (Exception e) {
			return getError("上传文件失败。");
		} finally {
			if (out != null) {
				out.close();
			}
		}
		String saveUrl = request.getContextPath() + "/upload/" + newFileName;
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl);
		return obj.toJSONString();
	}
}
