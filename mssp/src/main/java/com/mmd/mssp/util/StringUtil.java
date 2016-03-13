package com.mmd.mssp.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.domain.vo.ProductPropVo;

/**
 *
 * @author changshu.li
 */
public class StringUtil {

	public static final Pattern phonePt = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
	public static final Pattern emailPt = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	public static boolean isMobile(String phone) {
		Matcher mc = phonePt.matcher(phone);
		return mc.matches();
	}

	public static boolean isEmail(String mail) {
		Matcher mc = emailPt.matcher(mail);
		return mc.matches();
	}

	/**
	 * 分割 query 为两个字符串
	 * <pre>
	 * split2keyVal("12=456", "=") ==> ["12", "456"]
	 * split2keyVal("12", "=")   ==> ["12", ""]
	 * split2keyVal("=456", "=")  ==> ["", "456"]
	 * </pre>
	 *
	 * @param query
	 * @param split
	 * @return
	 */
	public static String[] split2keyVal(String query, String split) {
		int idx = query.indexOf(split);
		String[] res = new String[]{"", ""};
		if (idx < 0) {
			res[0] = query;
		} else {
			res[0] = query.substring(0, idx);
			res[1] = query.substring(idx + split.length());
		}
		return res;
	}

	/**
	 * 如果没有替换 返回 null
	 *
	 * @param qr
	 * @param qkey
	 * @return
	 */
	public static String removeQueryKeyValue(String qr, String qkey) {
		int[] ft = searchQueryKeyValue(qr, qkey, 0);
		if (ft == null) {
			return qr;
		}
		if (ft[0] > 0 && qr.charAt(ft[0] - 1) == '&') {
			ft[0] -= 1;
		}
		return qr.substring(0, ft[0]) + qr.substring(ft[1]);
	}
	
	public static String[] parseNotEmptyAndTrimArray(String scr, String split) {
		if (scr != null) {
			String[] strs = StringUtils.split(scr, split);
			strs = StringUtils.stripAll(strs);
			return (String[]) ArrayUtils.removeElement(strs, "");
		}
		return new String[]{};
	}

	/**
	 * 返回 两个值的数组, 包含 key=value 的字符串中的位置 form 和 to
	 *
	 * @param qr
	 * @param qkey
	 * @param from
	 * @return
	 */
	private static int[] searchQueryKeyValue(String qr, String qkey, int from) {
		int index = qr.indexOf(qkey, from);
		if (index > 0 && qr.charAt(index - 1) != '&') {//前一个字符是否是&
			return searchQueryKeyValue(qr, qkey, index + qkey.length());
		}
		if (index >= 0) {
			int end = index + qkey.length();
			if (end < qr.length()) {//不是结尾
				int endchar = qr.charAt(end);
				if (endchar != '=' && endchar != '&') {//后一个字符是否是 & =
					return searchQueryKeyValue(qr, qkey, index + end);
				}
			}
			int iand = qr.indexOf('&', index);
			if (iand < 0) {
				iand = qr.length();
			}
			return new int[]{index, iand};
		}
		return null;
	}

	public static String joinQuery(String url, String qr) {
		if (!url.contains("?")) {
			url += "?";
		}
		if (url.endsWith("&") || qr.startsWith("&") || qr.length() == 0) {
			return url + qr;
		}
		return url + "&" + qr;
	}

	public static String searchKeyValue(String qr, String qkey) {
		int[] ft = searchQueryKeyValue(qr, qkey, 0);
		if (ft == null) {
			return null;
		}
		return split2keyVal(qr.substring(ft[0], ft[1]), "=")[1];
	}

	public static String searchValueRegx(String qr, String qkey) {
		Pattern find = Pattern.compile("(^|&)" + qkey + "(=([^&]*)|&|$)");
		Matcher mat = find.matcher(qr);
		if (mat.find()) {
			String group = mat.group(3);
			return group == null ? "" : group;
		}
		return null;
	}

	public static boolean isNotNullAndSize(String str, int min, int max) {
		if (str == null) {
			return false;
		}
		return str.length() >= min && str.length() <= max;
	}

	public static boolean isNullOrSize(String str, int min, int max) {
		if (str == null) {
			return true;
		}
		return str.length() >= min && str.length() <= max;
	}

	public static boolean isEmptyOrSize(String str, int min, int max) {
		if ("".equals(str)) {
			return true;
		}
		return isNullOrSize(str, min, max);
	}

	/**
	 * 只允许 有 数字,英文 及 "." "-" "_" 三个特殊符号
	 * @param version
	 * @return 
	 */
	public static boolean isVersionFormat(String version) {
		Matcher matcher = pt.matcher(version);
		return matcher.matches();
	}
	private static Pattern pt = Pattern.compile("^[a-zA-Z0-9.\\-_]+$");
	public static String getRountRenameFilePath(String filePath){
		int i = filePath.lastIndexOf(".");
		String suffix = filePath.substring(i,filePath.length());
		String newFile = UUID.randomUUID().toString().replaceAll("-", "");
		newFile = newFile+ suffix;
		return newFile;
	}
	
	public static String formatDlvqtyString(String dlvqty){
		if(StringUtils.isBlank(dlvqty))
			return "0";
		if(dlvqty.contains(".")){
			return dlvqty.substring(0,dlvqty.indexOf("."));
		}
		return dlvqty;
	}

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param parameterValues
	* @param @param parameterValues2
	* @param @return   
	* @return String   
	* @throws
	*/
	public static String keyValueToJson(String[] key,String[] value) {
		Map<String, String> map= new HashMap<String, String>();
		for (int i = 0; i < key.length; i++) {
			map.put(key[i], value[i]);
		}
		return JSONObject.toJSONString(map);
	}
	public static void main(String[] args) {
		String[]  a={"1","2","3"};
		String[] b ={"a","b","c"};
		String[] c ={"一","二","三"};
		System.out.println(StringUtil.ppToJosn(a, b,c));
 	}


	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param parameterValues
	* @param @param parameterValues2
	* @param @param parameterValues3
	* @param @return   
	* @return String   
	* @throws
	*/
	public static String ppToJosn(String[] cptf,String[] cppf, String[] cpnf) {
		List<ProductPropVo> vos = new ArrayList<ProductPropVo>();
		for (int i = 0; i < cptf.length; i++) {
			ProductPropVo vo = new ProductPropVo();
			vo.setCpt(cptf[i]);
			vo.setCpp(cppf[i]);
			vo.setCpn(cpnf[i]);
			vos.add(vo);
		}
		return JSONObject.toJSONString(vos);
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

}
