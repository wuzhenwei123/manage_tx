package com.base.utils;

import java.util.HashMap;
import java.util.Map;

public class SessionName {
	/** 用户名称 **/
	public static final String ADMIN_USER_NAME = "admin_user_name";
	/** 用户ID **/
	public static final String ADMIN_USER_ID = "admin_user_id";
	/** 用户 **/
	public static final String ADMIN_USER = "admin_user";
	
	/** 系统菜单 **/
	public static final String SYSTEM_COLUMN_LIST = "system_columnList";
	/** 操作权限 **/
	public static final String USER_ROLE_LIST = "admin_user_role_list";
	
	/** 系统布局 **/
	public static final String SYS_LAYOUT = "sys_layout";//container-fluid or container
	
	/** 开户行信息 **/
	public static final String LIST_BANK = "list_bank";//container-fluid or container
	
	/** 开户行信息 **/
	public static final String SESSION_OPENID = "session_openid";//container-fluid or container
	
	/** 订单号 **/
	public static Map<String,String> MAPORDERNO = new HashMap<String,String>();
	
	/** 收益订单号 **/
	public static Map<String,String> MAPORDERNO_SY = new HashMap<String,String>();
	
	/** 缴费业务查询计数器 **/
	public static int TRACENO_COUNT = 1;
	
	public static Map<String,String> xzOrder = new HashMap<String,String>();
	
	public static Map<String,Map<String,String>> maporder = new HashMap<String,Map<String,String>>();
	
	public static Map<String,String> maporderNo = new HashMap<String,String>();
}
