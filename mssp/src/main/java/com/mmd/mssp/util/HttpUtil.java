/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author changshu.li
 */
public class HttpUtil {

	private static final Log logger = LogFactory.getLog(HttpUtil.class);
	private static final int BUFFER_SIZE = 1024 * 15;

	public static String httpGet(String uri) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(uri);
		HttpResponse response = httpclient.execute(httpget);

		logger.info(httpget.getURI().toString() + " == " + response.getStatusLine());

		HttpEntity entity = response.getEntity();
		Header[] cts = response.getHeaders("Content-Type");
		String charset = "UTF-8";
		if (cts.length > 0) {
			String ContentType = cts[0].getValue();
			if (ContentType.contains("charset")) {
				charset = ContentType.split("charset=")[1];
			}
		}
		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				StringBuffer buffer = new StringBuffer();
				char[] chars = new char[BUFFER_SIZE];
				while (true) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(instream, charset));
					int len = reader.read(chars);
					if (len < 0) {
						break;
					}
					buffer.append(chars, 0, len);
				}
				return buffer.toString();
			} catch (IOException ex) {
				throw ex;
			} catch (RuntimeException ex) {
				httpget.abort();
				throw ex;
			} finally {
				instream.close();
				httpclient.getConnectionManager().shutdown();
			}
		}
		return "";
	}

	public static String putParam4query(String uri, String key, String val) {
		if (uri.contains("?")) {
			return uri + "&" + key + "=" + val;
		}
		return uri + "?" + key + "=" + val;
	}
	
	public static String putParams(String uri, Map<String, String> params){
		if(params != null){
			Set<String> set = params.keySet();
			for (String key : set) {
				uri = putParam4query(uri,key,params.get(key));
			}
		}
		return uri;
	}
	
}
