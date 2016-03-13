package com.mmd.mssp.comm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
*    
* 项目名称：market-pay   
* 类名称：CommonConfig   
* 类描述： 获取config.properties配置  
* 创建人：sheng.tian 
* 创建时间：2014-12-19 下午3:24:02   
*
 */
public class CommonConfig {
    
	private static Logger log = Logger.getLogger(CommonConfig.class);
    
    private static Properties prop;
    
    private static String SYS_PSI;
    
    private static String SYS_B2B;
    
    private static String SYS_B2I;
    
    private static String SYS_B2C;
    
    private static String SYS_IRETAIL;
    
    private static String SYS_ERMP;
    
    private static String TEMP_B2B_PROJECT ;
    
    private static String TEMP_PSI_SELLOUTUPDATE ;
    
    private static String TEMP_B2I_BASICS_PROJECT ;
    
    private static String TEMP_B2I_PROJECT_OTHER ;
    
    private static String TEMP_B2C_PROJECT ;
    
    private static String TEMP_IRETAIL_PROJECT ;
    
    private static String TEMP_IRETAIL_R01 ;
    
    private static String TEMP_IRETAIL_R02 ;
    
    private static String TEMP_IRETAIL_R05 ;
    
    private static String TEMP_IRETAIL_R06 ;
    
    private static String TEMP_IRETAIL_R09 ;
    
    private static String TEMP_IRETAIL_R10 ;
    
    private static String TEMP_IRETAIL_R11 ;
    
    private static String TEMP_IRETAIL_R12 ;
    
    static {
        new CommonConfig();
    }
    
    private CommonConfig() {
        prop = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("/config.properties");
            prop.load(in);
            SYS_PSI= prop.getProperty("sys_psi");
            SYS_B2B= prop.getProperty("sys_b2b");
            SYS_B2I= prop.getProperty("sys_b2i");
            SYS_B2C= prop.getProperty("sys_b2c");
            SYS_IRETAIL= prop.getProperty("sys_iretail");
            SYS_ERMP= prop.getProperty("sys_ermp");
            TEMP_B2B_PROJECT=prop.getProperty("temp_b2b_project");
            TEMP_PSI_SELLOUTUPDATE=prop.getProperty("temp_psi_selloutupdate");
            TEMP_B2I_BASICS_PROJECT=prop.getProperty("temp_b2i_basics_project");
            TEMP_B2I_PROJECT_OTHER=prop.getProperty("temp_b2i_project_other");
            TEMP_B2C_PROJECT=prop.getProperty("temp_b2c_project");
            TEMP_IRETAIL_PROJECT=prop.getProperty("temp_iretail_project");
            TEMP_IRETAIL_R01=prop.getProperty("temp_iretail_r01");
            TEMP_IRETAIL_R02=prop.getProperty("temp_iretail_r02");
            TEMP_IRETAIL_R05=prop.getProperty("temp_iretail_r05");
            TEMP_IRETAIL_R06=prop.getProperty("temp_iretail_r06");
            TEMP_IRETAIL_R09=prop.getProperty("temp_iretail_r09");
            TEMP_IRETAIL_R10=prop.getProperty("temp_iretail_r10");
            TEMP_IRETAIL_R11=prop.getProperty("temp_iretail_r11");
            TEMP_IRETAIL_R12=prop.getProperty("temp_iretail_r12");
            log.debug("读取配置文件完成！");
        } catch (Throwable e) {
            log.error("读取配置文件出错", e);
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("关闭文件输入流时出错", e);
                }
            }
        }
    }


	public static Hashtable getProperties(){
    	return prop;
    }
    
    /**
     * 根据properties的名称取得值
     * @param key
     * @return
     */
    public static String getValue(String key) throws Exception{
    	Object obj = prop.get(key);
    	if(obj == null){
    		log.error("此properties名称不存在: "+key);
    		throw new Exception("此properties名称不存在: "+key);
    	}
    	return (String)obj;
    }

    public static void main(String[] args) {
		System.out.println(CommonConfig.getTEMP_B2B_PROJECT());
	}
	public static String getSYS_PSI() {
		return SYS_PSI;
	}

	public static void setSYS_PSI(String sYS_PSI) {
		SYS_PSI = sYS_PSI;
	}

	public static String getSYS_B2B() {
		return SYS_B2B;
	}

	public static void setSYS_B2B(String sYS_B2B) {
		SYS_B2B = sYS_B2B;
	}

	public static String getSYS_B2I() {
		return SYS_B2I;
	}

	public static void setSYS_B2I(String sYS_B2I) {
		SYS_B2I = sYS_B2I;
	}

	public static String getSYS_B2C() {
		return SYS_B2C;
	}

	public static void setSYS_B2C(String sYS_B2C) {
		SYS_B2C = sYS_B2C;
	}

	public static String getSYS_IRETAIL() {
		return SYS_IRETAIL;
	}

	public static void setSYS_IRETAIL(String sYS_IRETAIL) {
		SYS_IRETAIL = sYS_IRETAIL;
	}

	public static String getSYS_ERMP() {
		return SYS_ERMP;
	}

	public static void setSYS_ERMP(String sYS_ERMP) {
		SYS_ERMP = sYS_ERMP;
	}

	public static String getTEMP_B2B_PROJECT() {
		return TEMP_B2B_PROJECT;
	}
	
	public static String getTEMP_IRETAIL_PROJECT() {
		return TEMP_IRETAIL_PROJECT;
	}

	public static void setTEMP_B2B_PROJECT(String tEMP_B2B_PROJECT) {
		TEMP_B2B_PROJECT = tEMP_B2B_PROJECT;
	}

	public static String getTEMP_PSI_SELLOUTUPDATE() {
		return TEMP_PSI_SELLOUTUPDATE;
	}

	public static void setTEMP_PSI_SELLOUTUPDATE(String tEMP_PSI_SELLOUTUPDATE) {
		TEMP_PSI_SELLOUTUPDATE = tEMP_PSI_SELLOUTUPDATE;
	}

	public static String getTEMP_B2I_BASICS_PROJECT() {
		return TEMP_B2I_BASICS_PROJECT;
	}

	public static void setTEMP_B2I_BASICS_PROJECT(String tEMP_B2I_BASICS_PROJECT) {
		TEMP_B2I_BASICS_PROJECT = tEMP_B2I_BASICS_PROJECT;
	}
	
	public static String getTEMP_B2I_PROJECT_OTHER() {
		return TEMP_B2I_PROJECT_OTHER;
	}

	public static void setTEMP_B2I_PROJECT_OTHER(String tEMP_B2I_PROJECT_OTHER) {
		TEMP_B2I_PROJECT_OTHER = tEMP_B2I_PROJECT_OTHER;
	}

	public static String getTEMP_B2C_PROJECT() {
		return TEMP_B2C_PROJECT;
	}

	public static void setTEMP_B2C_PROJECT(String tEMP_B2C_PROJECT) {
		TEMP_B2C_PROJECT = tEMP_B2C_PROJECT;
	}

	public static String getTEMP_IRETAIL_R01() {
		return TEMP_IRETAIL_R01;
	}

	public static void setTEMP_IRETAIL_R01(String tEMP_IRETAIL_R01) {
		TEMP_IRETAIL_R01 = tEMP_IRETAIL_R01;
	}

	public static void setTEMP_IRETAIL_PROJECT(String tEMP_IRETAIL_PROJECT) {
		TEMP_IRETAIL_PROJECT = tEMP_IRETAIL_PROJECT;
	}

	public static String getTEMP_IRETAIL_R02() {
		return TEMP_IRETAIL_R02;
	}

	public static void setTEMP_IRETAIL_R02(String tEMP_IRETAIL_R02) {
		TEMP_IRETAIL_R02 = tEMP_IRETAIL_R02;
	}

	public static String getTEMP_IRETAIL_R05() {
		return TEMP_IRETAIL_R05;
	}

	public static void setTEMP_IRETAIL_R05(String tEMP_IRETAIL_R05) {
		TEMP_IRETAIL_R05 = tEMP_IRETAIL_R05;
	}


	public static String getTEMP_IRETAIL_R06() {
		return TEMP_IRETAIL_R06;
	}


	public static void setTEMP_IRETAIL_R06(String tEMP_IRETAIL_R06) {
		TEMP_IRETAIL_R06 = tEMP_IRETAIL_R06;
	}


	public static String getTEMP_IRETAIL_R09() {
		return TEMP_IRETAIL_R09;
	}


	public static void setTEMP_IRETAIL_R09(String tEMP_IRETAIL_R09) {
		TEMP_IRETAIL_R09 = tEMP_IRETAIL_R09;
	}


	public static String getTEMP_IRETAIL_R10() {
		return TEMP_IRETAIL_R10;
	}


	public static void setTEMP_IRETAIL_R10(String tEMP_IRETAIL_R10) {
		TEMP_IRETAIL_R10 = tEMP_IRETAIL_R10;
	}


	public static String getTEMP_IRETAIL_R11() {
		return TEMP_IRETAIL_R11;
	}


	public static void setTEMP_IRETAIL_R11(String tEMP_IRETAIL_R11) {
		TEMP_IRETAIL_R11 = tEMP_IRETAIL_R11;
	}


	public static String getTEMP_IRETAIL_R12() {
		return TEMP_IRETAIL_R12;
	}


	public static void setTEMP_IRETAIL_R12(String tEMP_IRETAIL_R12) {
		TEMP_IRETAIL_R12 = tEMP_IRETAIL_R12;
	}
}
