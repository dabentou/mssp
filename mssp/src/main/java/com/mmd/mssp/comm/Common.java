package  com.mmd.mssp.comm;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * 业务相关的工具类
* @author sheng.tian
 *
 */
public class Common {
	
	private static Logger log = Logger.getLogger(Common.class.getName());
	
	public static void writeToClient(String info,HttpServletResponse res){
    	res.setContentType("text/plain;charset=UTF-8");
        ServletOutputStream out = null;
        try {
            out = res.getOutputStream();
            out.write(info.getBytes("UTF-8"));
        } catch (IOException e) {
        	log.error(e);
        } finally {
            try {
                out.close();
            } catch (Exception e) {
            	log.error(e);
            }
        }
	}
	
	
	
	/**
	 * 密码加密 方法
	 * 
	 * @param pwd   
	 *        密码
	 * @return
	 */
	public static String pwdMd5(String pwd){
		try{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		    StringBuffer result = new StringBuffer();
		    try {
		        for (byte b : md.digest(pwd.toString().getBytes("UTF-8"))) {
		            result.append(Integer.toHexString((b & 0xf0) >>> 4));
		            result.append(Integer.toHexString(b & 0x0f));
		        }
		    } catch (Exception e) {
		        for (byte b : md.digest(pwd.toString().getBytes())) {
		            result.append(Integer.toHexString((b & 0xf0) >>> 4));
		            result.append(Integer.toHexString(b & 0x0f));
		        }
		    }
		    return result.toString();
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	
	private static Random randGen = null;		
	private static char[] numbersAndLetters = null;		
	
	/**
	 * 动态密钥生成
	 * 
	 * @param length
	 * @return
	 */
	public static String randomKeyStr(int length) {		         
		if (length < 1) {		             
			return null;		         
	    }		         
		if (randGen == null) {		               
			randGen = new Random();		                
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();		                  
		}
        char [] randBuffer = new char[length];		       
        for (int i=0; i<randBuffer.length; i++) {		             
    	   randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];		          
        }		         
        return new String(randBuffer);		
   }
	
	
   
	
}
