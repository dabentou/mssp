package com.mmd.mssp.util;

import java.security.MessageDigest;

public class ValidateCode {  

	private final static String[] hexDigits =  { "2", "3", "4",  "6", "7", "8", "9", "A", "B", "C", "D", "F", "G", "H", "J",  
        "K", "P",  "R", "T", "U", "V", "W",  
        "X", "Y", "Z"}; 




    private static String byteArrayToHexString(byte[] b) { 
		StringBuffer resultSb = new StringBuffer(); 
		for (int i = 0; i < b.length; i++) { 
			resultSb.append(byteToHexString(b[i])); 
		} 
		return resultSb.toString(); 
	} 

	/***
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) { 
		int n = b; 
		if (n < 0) n = 256 + n; 
		int d1 = n / 16; 
		int d2 = n % 16; 
		return hexDigits[d1] + hexDigits[d2]; 
	} 

	/***
	 * 
	 * @param origin
	 * @return
	 */
	public static String createCode(String origin) { 
		String resultString = null; 
		try { 
			resultString=new String(origin); 
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			resultString=byteArrayToHexString(md.digest(resultString.getBytes())); 
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		return resultString; 
	}

	
     
}  