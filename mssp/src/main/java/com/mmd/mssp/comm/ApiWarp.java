/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.comm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.domain.vo.Paging;

/**
 *
 * @author changshu.li
 */
public class ApiWarp {

	private final List<ErrorEntry> errors = new ArrayList();
	private Map<String, Object> map = new HashMap<String, Object>();

	public ApiWarp putData(String key, Object val) {
		map.put(key, val);
		return this;
	}

	public ApiWarp putData(Object val) {
		map.put("data", val);
		return this;
	}

	public ApiWarp addError(String msg) {
		this.addError("" + errors.size(), msg);
		return this;
	}

	public ApiWarp addError(String code, String msg) {
		errors.add(new ErrorEntry(code, msg));
		return this;
	}

	public ApiWarp putListing(Object val) {
		map.put("listing", val);
		return this;
	}

	public Map<String, Object> toJsonMap() {
		map.put("status", isSuccess());
		if (!errors.isEmpty()) {
			map.put("errors", errors);
		}
		return Collections.unmodifiableMap((JSONObject) JSONObject.toJSON(map));
	}

	public String toJsonString() {
		map.put("status", getStatus());
		if (!errors.isEmpty()) {
			map.put("errors", errors);
		}
		return JSONObject.toJSONString(map);
	}

	public String toJson4errorList() {
		map.put("status", getStatus());
		if (!errors.isEmpty()) {
			int len = errors.size();
			String[] str = new String[len];
			for (int i = 0; i < len; i++) {
				str[i] = errors.get(i).getMsg();
			}
			map.put("errors", str);
		}
		return JSONObject.toJSONString(map);
	}

	public boolean isSuccess() {
		return errors.isEmpty();
	}

	public String getStatus() {
		return isSuccess() ? "OK" : "FAIL";
	}

	public ApiWarp setPaging(Paging pg) {
		this.putData("count", pg.getResult().getTotalElements());
		this.putData("totalpage", pg.getResult().getTotalPages());
		this.putData("pagesize", pg.getResult().getSize());
		return this;
	}

	public static class ErrorEntry {

		private String code;
		private String msg;

		public ErrorEntry(String code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public String getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}

	}
}
