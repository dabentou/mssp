/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.util;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author changshu.li
 */
public class WebUtil {

	private static final Log logger = LogFactory.getLog(WebUtil.class);
	private static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	/**
	 *
	 * @param request
	 * @return 出错 或者 不包含该头 返回 -1
	 */
	public static long getIfModifiedSince(HttpServletRequest request) {
		try {
			long since = request.getDateHeader(IF_MODIFIED_SINCE);
			return since;
		} catch (Exception ex) {
			logger.warn("data time parse error ! :" + ex.getMessage());
		}
		return -1L;
	}

	public boolean isModified(HttpServletRequest request, Date date) {
		long since = getIfModifiedSince(request);
		if (since < 0) {
			return true;
		}
		return date.getTime() > since;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String replaceSqlQuery(String val) {
		return val.replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
	}

	public static String getHttpQueryParam(HttpServletRequest request, String key) throws UnsupportedEncodingException {
		String val, ie = request.getParameter("ie");
		String ua = request.getHeader("User-Agent");
		if (ua != null && ua.contains("MSIE")) {//IE 用 GBK 解码
			if (ie != null) {
				try {
					val = WebUtil.getHttpQueryParam(request, key, ie);
				} catch (UnsupportedEncodingException ex) {
					val = WebUtil.getHttpQueryParam(request, key, "UTF-8");
				}
			} else {
				val = WebUtil.getHttpQueryParam(request, key, "GBK");
			}
		} else {
			val = WebUtil.getHttpQueryParam(request, key, "UTF-8");
		}
		request.setAttribute("QueryValue", val);
//		val = val.replaceAll("\\\\", "\\\\\\\\").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		return val;
	}

	public static String getHttpQueryParam(HttpServletRequest request, String key, String charset) throws UnsupportedEncodingException {
		String queryString = request.getQueryString();
		if (!StringUtils.isAsciiPrintable(queryString)) {// 非 ASCII 可打印字符
			byte[] bytes = queryString.getBytes("iso-8859-1");
			queryString = new String(bytes, charset);
		}
		String val = StringUtil.searchKeyValue(queryString, key);
		if (val != null) {
			val = URLDecoder.decode(val, charset);
		}
		request.setAttribute("QueryDecodeCharset", val);
		return val;
	}
}
