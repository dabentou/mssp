/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmd.mssp.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mmd.mssp.domain.vo.ApproveTemplateType;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author changshu.li
 */
public class SysConfig {

	private static Log logger = LogFactory.getLog(SysConfig.class);
	private static final Properties properties = new Properties();
	public static String CONFIG_FILE = "/config.properties";

	public static final String USER_KEY = "userKey";

	static {
		loadConfig();
	}

	private SysConfig() {
	}

	private static void loadConfig() {
		InputStream input = null;
		try {
			input = SysConfig.class.getResourceAsStream(CONFIG_FILE);
			properties.load(input);
		} catch (IOException ex) {
			logger.error("load " + CONFIG_FILE + "error", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					logger.warn(ex);
				}
			}
		}
	}

	public static Properties getConfig() {
		return new Properties(properties);
	}

	public static String getConfig(String key, String def) {
		return (String) getConfig().getProperty(key, def);
	}

	public static List<ApproveTemplateType> getTemplateStatusValues(){
		List<ApproveTemplateType> list = new ArrayList<ApproveTemplateType>();
		//b2b项目申请
		ApproveTemplateType tempTypeB2B = new ApproveTemplateType();
		tempTypeB2B.setType(getConfig("temp_b2b_project", "temp_b2b_project"));
		tempTypeB2B.setTypeName("B2B项目审批");
		list.add(tempTypeB2B);
		
		//psi项目申请
		ApproveTemplateType tempTypeSellOutUpdate = new ApproveTemplateType();
		tempTypeSellOutUpdate.setType(getConfig("temp_psi_selloutupdate", "temp_psi_selloutupdate"));
		tempTypeSellOutUpdate.setTypeName("SellOut修改审批");
		list.add(tempTypeSellOutUpdate);
		
		//B2i基础项目审批
		ApproveTemplateType tempTypeB2IBasics = new ApproveTemplateType();
		tempTypeB2IBasics.setType(getConfig("temp_b2i_basics_project", "temp_b2i_basics_project"));
		tempTypeB2IBasics.setTypeName("B2I基础项目审批");
		list.add(tempTypeB2IBasics);
		
		//B2c项目审批
		ApproveTemplateType tempTypeB2c = new ApproveTemplateType();
		tempTypeB2c.setType(getConfig("temp_b2c_project", "temp_b2c_project"));
		tempTypeB2c.setTypeName("B2C特殊单审批");
		list.add(tempTypeB2c); 
		
		//iretail项目审批
		ApproveTemplateType tempTypeIretail = new ApproveTemplateType();
		tempTypeIretail.setType(getConfig("temp_iretail_project", "temp_iretail_project"));
		tempTypeIretail.setTypeName("iretail项目审批");
		list.add(tempTypeIretail); 
		
		//iretailR01审批
		ApproveTemplateType iretailR01 = new ApproveTemplateType();
		iretailR01.setType(getConfig("temp_iretail_r01", "temp_iretail_r01"));
		iretailR01.setTypeName("iretai R01审批");
		list.add(iretailR01);
		
		//iretailR01审批
		ApproveTemplateType iretailR02 = new ApproveTemplateType();
		iretailR02.setType(getConfig("temp_iretail_r02", "temp_iretail_r02"));
		iretailR02.setTypeName("iretai R02审批");
		list.add(iretailR02);
		
		//iretailR05审批
		ApproveTemplateType iretailR05 = new ApproveTemplateType();
		iretailR05.setType(getConfig("temp_iretail_r05", "temp_iretail_r05"));
		iretailR05.setTypeName("iretai R05审批");
		list.add(iretailR05);
		
		//iretailR06审批
		ApproveTemplateType iretailR06 = new ApproveTemplateType();
		iretailR06.setType(getConfig("temp_iretail_r06", "temp_iretail_r06"));
		iretailR06.setTypeName("iretai R06审批");
		list.add(iretailR06);
		
		//iretailR09审批
		ApproveTemplateType iretailR09 = new ApproveTemplateType();
		iretailR09.setType(getConfig("temp_iretail_r09", "temp_iretail_r09"));
		iretailR09.setTypeName("iretai R09审批");
		list.add(iretailR09);
		
		//iretailR10审批
		ApproveTemplateType iretailR10 = new ApproveTemplateType();
		iretailR10.setType(getConfig("temp_iretail_r10", "temp_iretail_r10"));
		iretailR10.setTypeName("iretai R10审批");
		list.add(iretailR10);
				
		//iretailR11审批
		ApproveTemplateType iretailR11 = new ApproveTemplateType();
		iretailR11.setType(getConfig("temp_iretail_r11", "temp_iretail_r11"));
		iretailR11.setTypeName("iretai R11审批");
		list.add(iretailR11);
		
		//iretailR12审批
		ApproveTemplateType iretailR12 = new ApproveTemplateType();
		iretailR12.setType(getConfig("temp_iretail_r12", "temp_iretail_r12"));
		iretailR12.setTypeName("iretai R12审批");
		list.add(iretailR12);
		
		//B2i项目审批
		ApproveTemplateType b2iOhter = new ApproveTemplateType();
		b2iOhter.setType(getConfig("temp_b2i_project_other", "temp_b2i_project_other"));
		b2iOhter.setTypeName("B2I特殊项目审批");
		list.add(b2iOhter);
		
		return list;
	}
}
