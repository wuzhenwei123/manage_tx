package com.sys.manageAdminUser.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.base.controller.BaseController;
import com.base.exception.BaseException;
import com.base.perm.Permission;
import com.base.utils.ConfigConstants;
import com.base.utils.MD5;
import com.base.utils.PicCompression;
import com.base.utils.RequestHandler;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;
import com.base.utils.https.HttpUtils;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRole.service.ManageAdminRoleService;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.sys.manageAdminUser.service.ManageAdminUserService;
import com.sys.manageAdminUser.service.ManageAdminUserServiceImpl;
import com.wx.service.WeiXinService;

/**
 * @author keeny
 * @time 2017-03-23 14:13:13
 */
@Controller
@RequestMapping("/manageAdminUser")
public class ManageAdminUserController extends BaseController {

	// private static Logger logger =
	// Logger.getLogger(ManageAdminUserController.class); //Logger
	@Autowired
	private ManageAdminRoleService manageadminroleService = null;// 角色
	@Autowired
	private ManageAdminUserService manageadminuserService = null;
	@Autowired
	private WeiXinService weiXinService;
	
	private static MD5 MD5 = new MD5();

	/***************** 页面中转 *********************/
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		String errorMsg = "";// 错误提示
		HttpSession session = request.getSession();
		String url = "/sys/login";
		String adminName = RequestHandler.getString(request, "adminName");
		String passwd = RequestHandler.getString(request, "passwd");
		if (StringUtils.isNotBlank(passwd) && StringUtils.isNotBlank(adminName)) {
			ManageAdminUser manageadminuser1 = new ManageAdminUser();
			manageadminuser1.setAdminName(adminName);
			ManageAdminUser manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser1);
			if (manageadminuser != null) {
				String passwd2 = manageadminuser.getPasswd();
				if (StringUtils.isNotBlank(passwd2) && MD5.getMD5ofStr(passwd).equals(passwd2)) {
					session = reSession(request);// 重置session
					// 账户正常
					manageadminuserService.login(session, adminName);
					url = "redirect:/main";
				} else {
					errorMsg = "密码错误";
				}
			} else {
				errorMsg = "账号不存在";
			}
		} else {
			errorMsg = "账号和密码不能为空";
		}
		if (!errorMsg.equals(""))
			model.addAttribute("error", errorMsg);
		return url;
	}

	/**
	 * 从新获取session
	 * 
	 * @param request
	 * @return
	 */
	private HttpSession reSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		Cookie cookie = request.getCookies()[0];// 获取cookie
		cookie.setMaxAge(0);// 让cookie过期
		session = request.getSession(true);
		return session;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String init(HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!isKF(request) && !isDB(request)) {
			//获取角色列表
			ManageAdminRole manageAdminRole = new ManageAdminRole();
			manageAdminRole.setState(1);
			List<ManageAdminRole> listRole = manageadminroleService.getManageAdminRoleBaseList(manageAdminRole);
			model.addAttribute("listRole", listRole);
		}
		return "/sys/manageAdminUser/manageAdminUserIndex";
	}
	/**
	 * 批发商、零售商、电商代理列表入口
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wholesaler/{promoter_id}/{role_id}", method = RequestMethod.GET)
	public String wholesaler(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable String promoter_id,
			@PathVariable String role_id
		) {
		model.addAttribute("role_id", role_id);
		model.addAttribute("promoter_id", promoter_id);
		return "/sys/manageAdminUser/manageAdminUserIndex_wholesaler";
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUpdateInfo", method = RequestMethod.GET)
	public String toUpdateInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminUser manageadminuser1 = new ManageAdminUser();
		manageadminuser1.setAdminId(getCurrentAdminUserId(request));
		ManageAdminUser manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser1);
		model.addAttribute("manageadminuser", manageadminuser);
		return "/sys/manageAdminUser/manageAdminUserInfo";
	}

	@Permission(value = "/manageAdminUser/addManageAdminUser")
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminRole manageAdminRole = new ManageAdminRole();
		manageAdminRole.setState(1);
		List<ManageAdminRole> manageAdminRoleBaseList = manageadminroleService
				.getManageAdminRoleBaseList(manageAdminRole);
		model.addAttribute("roleList", manageAdminRoleBaseList);
		return "/sys/manageAdminUser/manageAdminUserAdd";
	}
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@Permission(value = "/manageAdminUser/reset_passwd")
	@RequestMapping(value = "/reset_passwd", method = RequestMethod.GET)
	public String reset_passwd(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminUser manageadminuser1 = requst2Bean(request, ManageAdminUser.class);
		ManageAdminUser manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser1);
		model.addAttribute("manageadminuser", manageadminuser);
		return "/sys/manageAdminUser/reset_passwd";
	}
	/**
	 * 更新密码
	 * @param request
	 * @param response
	 * @param model
	 * @throws BaseException
	 */
	@Permission(value = "/manageAdminUser/reset_passwd")
	@RequestMapping(value = "/update_passwd", method = RequestMethod.POST)
	public void update_passwd(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		Integer adminId = manageadminuser.getAdminId();
		if(adminId == null) {
			writeErrorMsg("用户ID不能为空", null, response);
			return;
		}
		
		String new_password_1 = RequestHandler.getString(request, "new_password_1");
		String new_password_2 = RequestHandler.getString(request, "new_password_2");

		if (StringUtils.isNotBlank(new_password_1)
				&& StringUtils.isNotBlank(new_password_2)) {
			if (new_password_1.equals(new_password_2)) {
				ManageAdminUser manageAdminUser3 = manageadminuserService.getManageAdminUser(manageadminuser);
				if (manageAdminUser3 != null) {
					manageadminuser.setPasswd(MD5.getMD5ofStr(new_password_1));
					manageadminuserService.updateManageAdminUser(manageadminuser);
					writeSuccessMsg("修改成功", null, response);
				} else {
					writeErrorMsg("用户不存在失败", null, response);
				}
			} else {
				writeErrorMsg("两次密码不一致", null, response);
			}
		} else {
			writeErrorMsg("密码不能为空", null, response);
		}
	}

	@Permission(value = "/manageAdminUser/updateManageAdminUser")
	@RequestMapping(value = "/toUpdate", method = RequestMethod.GET)
	public String toUpdate(HttpServletRequest request, HttpServletResponse response, Model model) {
		
		ManageAdminRole manageAdminRole = new ManageAdminRole();
		manageAdminRole.setState(1);
		List<ManageAdminRole> manageAdminRoleBaseList = manageadminroleService
				.getManageAdminRoleBaseList(manageAdminRole);
		model.addAttribute("roleList", manageAdminRoleBaseList);// 角色
		
		ManageAdminUser manageadminuser1 = requst2Bean(request, ManageAdminUser.class);
		ManageAdminUser manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser1);
		model.addAttribute("manageadminuser", manageadminuser);
		return "/sys/manageAdminUser/manageAdminUserUpdate";
	}

	@Permission(value = "/manageAdminUser/getManageAdminUserList")
	@RequestMapping(value = "/getManageAdminUserList", method = RequestMethod.GET)
	public void getManageAdminUserList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		
//		Integer role_id = manageadminuser.getRole_id();
//		if(role_id == null) {
//			ManageAdminUser loginUser = (ManageAdminUser)request.getSession().getAttribute(SessionName.ADMIN_USER);
//			if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.DB_ROLE_ID).intValue()){//拓展员登录只能看到自己的客户
//				manageadminuser.setPromoter_id(loginUser.getAdminId());
//			}
//			if(loginUser.getRole_id().intValue()==Integer.valueOf(ConfigConstants.KF_ROLE_ID).intValue()){//客服只能看到扩展业务员员
//				manageadminuser.setRole_id(Integer.valueOf(ConfigConstants.DB_ROLE_ID));
//			}
//		}
		int manageadminuserListCount = manageadminuserService.getManageAdminUserListCount(manageadminuser);
		ResponseList<ManageAdminUser> manageadminuserList = null;
		if (manageadminuserListCount > 0) {
			manageadminuserList = manageadminuserService.getManageAdminUserList(manageadminuser);
		} else {
			manageadminuserList = new ResponseList<ManageAdminUser>();
		}
		// 设置数据总数
		manageadminuserList.setTotalResults(manageadminuserListCount);

		writeSuccessMsg("ok", manageadminuserList, response);
	}

	@Permission(value = "/manageAdminUser/addManageAdminUser")
	@RequestMapping(value = "/addManageAdminUser", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, Model model) throws BaseException {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		manageadminuser.setCreaterId(getCurrentAdminUserId(request));
		manageadminuser.setCreateTime(new Date());
		manageadminuser.setCreateAdminName(getCurrentAdminUserName(request));
		String passwd = manageadminuser.getPasswd();
		if (passwd != null)
			manageadminuser.setPasswd(MD5.getMD5ofStr(passwd));
		manageadminuserService.insertManageAdminUser(manageadminuser);
		writeSuccessMsg("添加成功", null, response);
	}

	@Permission(value = "/manageAdminUser/updateManageAdminUser")
	@RequestMapping(value = "/updateManageAdminUser", method = RequestMethod.POST)
	public void updateManageAdminUser(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		if (manageadminuser.getPasswd() != null && !"".equals(manageadminuser.getPasswd())) {
			manageadminuser.setPasswd(MD5.getMD5ofStr(manageadminuser.getPasswd()));
		}
		int count = manageadminuserService.updateManageAdminUser(manageadminuser);
		if (count == 1) {
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}
	/**
	 * 更新当前用户信息
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/updateManageAdminUserInfo", method = RequestMethod.POST)
	public void updateManageAdminUserInfo(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		manageadminuser.setAdminId(getCurrentAdminUserId(request));
		int count = manageadminuserService.updateManageAdminUser(manageadminuser);
		if (count == 1) {
			manageadminuser = new ManageAdminUser();
			manageadminuser.setAdminId(getCurrentAdminUserId(request));
			ManageAdminUser manageAdminUser2 = manageadminuserService.getManageAdminUser(manageadminuser);
			ManageAdminUserServiceImpl userServerImpl = new ManageAdminUserServiceImpl();
			userServerImpl.initLoginSession(request.getSession(), manageAdminUser2);
			writeSuccessMsg("修改成功", null, response);
		} else {
			writeErrorMsg("修改失败", null, response);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/updateManageAdminUserPwd", method = RequestMethod.POST)
	public void updateManageAdminUserPwd(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String old_password = RequestHandler.getString(request, "old_password");
		String new_password_1 = RequestHandler.getString(request, "new_password_1");
		String new_password_2 = RequestHandler.getString(request, "new_password_2");

		if (StringUtils.isNotBlank(old_password) && StringUtils.isNotBlank(new_password_1)
				&& StringUtils.isNotBlank(new_password_2)) {
			if (new_password_1.equals(new_password_2)) {
				ManageAdminUser manageadminuser = new ManageAdminUser();
				manageadminuser.setAdminId(getCurrentAdminUserId(request));
				ManageAdminUser manageAdminUser3 = manageadminuserService.getManageAdminUser(manageadminuser);
				if (manageAdminUser3 != null) {
					String passwd = manageAdminUser3.getPasswd();
					if (passwd.equals(MD5.getMD5ofStr(old_password))) {
						manageadminuser.setPasswd(MD5.getMD5ofStr(old_password));
						manageadminuserService.updateManageAdminUser(manageadminuser);
						writeSuccessMsg("修改成功", null, response);
					} else {
						writeErrorMsg("当前密码错误", null, response);
					}
				} else {
					writeErrorMsg("修改失败", null, response);
				}
			} else {
				writeErrorMsg("两次密码不一致", null, response);
			}
		} else {
			writeErrorMsg("密码不能为空", null, response);
		}
	}
	
	/**
	 * 修改收款二维码
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/updateManageAdminPayqrcode", method = RequestMethod.POST)
	public void updateManageAdminPayqrcode(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String pay_qrcode = RequestHandler.getString(request, "pay_qrcode");
		
		if (StringUtils.isNotBlank(pay_qrcode)) {
			ManageAdminUser manageadminuser = new ManageAdminUser();
			manageadminuser.setAdminId(getCurrentAdminUserId(request));
			ManageAdminUser manageAdminUser3 = manageadminuserService.getManageAdminUser(manageadminuser);
			if (manageAdminUser3 != null) {
				manageadminuser.setPay_qrcode(pay_qrcode);
				manageadminuserService.updateManageAdminUser(manageadminuser);
				writeSuccessMsg("修改成功", null, response);
				
			} else {
				writeErrorMsg("修改失败", null, response);
			}
		} else {
			writeErrorMsg("请上传收款二维码", null, response);
		}
	}

	@Permission(value = "/manageAdminUser/removeManageAdminUser")
	@RequestMapping(value = "/removeManageAdminUser", method = RequestMethod.POST)
	public void removeManageAdminUser(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		ManageAdminUser manageadminuser = new ManageAdminUser();
		Integer adminId = RequestHandler.getInteger(request, "adminId");
		manageadminuser.setAdminId(adminId);

		manageadminuserService.removeManageAdminUser(manageadminuser);
		writeSuccessMsg("删除成功", null, response);
	}
	
	@Permission(value = "/manageAdminUser/setDefaultUser")
	@RequestMapping(value = "/setDefaultUser", method = RequestMethod.POST)
	public void setDefaultUser(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		
		ManageAdminUser manageadminuser = new ManageAdminUser();
		Integer adminId = RequestHandler.getInteger(request, "adminId");
		manageadminuser.setIs_default_db(1);
		manageadminuser.setAdminId(adminId);
		manageadminuserService.updateManageAdminUser(manageadminuser);
		
		//将其他设置为非默认
		manageadminuserService.updateDefaultDB(manageadminuser);
		
		writeSuccessMsg("设置成功", null, response);
	}

	@Permission(value = "/manageAdminUser/removeAllManageAdminUser")
	@RequestMapping(value = "/removeAllManageAdminUser", method = RequestMethod.POST)
	public void removeAllManageAdminUser(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		String adminIds = RequestHandler.getString(request, "adminIds");
		if (adminIds != null) {
			List<Integer> list = JSONArray.parseArray(adminIds, Integer.class);
			if (list != null) {
				for (Integer adminId : list) {
					ManageAdminUser manageAdminUser = new ManageAdminUser();
					manageAdminUser.setAdminId(adminId);
					manageadminuserService.removeManageAdminUser(manageAdminUser);
				}
			}
		}
		writeSuccessMsg("删除成功", null, response);
	}
	@Permission(value = "/manageAdminUser/checkState")
	@RequestMapping(value = "/checkState", method = RequestMethod.POST)
	public void checkState(HttpServletRequest request, HttpServletResponse response, Model model)
			throws BaseException {
		Integer adminId = RequestHandler.getInteger(request, "adminId");
		if (adminId != null) {
			ManageAdminUser manageAdminUser = new ManageAdminUser();
			manageAdminUser.setAdminId(adminId);
			manageAdminUser.setState(1);
			manageadminuserService.updateManageAdminUser(manageAdminUser);
		}
		writeSuccessMsg("审核成功", null, response);
	}

	@Permission(value = "/manageAdminUser/addManageAdminUser")
	@RequestMapping(value = "/verifyAdminName", method = RequestMethod.POST)
	public void verifyAdminName(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Boolean> map = new HashMap<>();
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		int manageAdminUserListCount = manageadminuserService.getManageAdminUserListCount(manageadminuser);
		if (manageAdminUserListCount == 0) {
			map.put("valid", true);
		} else {
			map.put("valid", false);
		}
		writeJsonObject(map, response);
	}
	
	@Permission(value = "/manageAdminUser/getQRcode")
	@RequestMapping(value = "/getQRcode", method = RequestMethod.GET)
	public String getQRcode(HttpServletRequest request, HttpServletResponse response, Model model) {
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		try{
			//查看是否已经获取二维码
			manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser);
			if(manageadminuser!=null&&StringUtils.isNoneBlank(manageadminuser.getQr_code_url())){
				model.addAttribute("qr_code_url", manageadminuser.getQr_code_url());
			}else{
				Map<String,String> map  = weiXinService.getEWMYj(ConfigConstants.APPID, ConfigConstants.APPSECRET, manageadminuser.getOpenId());
				manageadminuser.setScan_ticket(map.get("ticket"));
				manageadminuser.setQr_code_url(map.get("imgurl"));
				manageadminuserService.updateManageAdminUser(manageadminuser);
				model.addAttribute("qr_code_url", map.get("imgurl"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		model.addAttribute("adminId", manageadminuser.getAdminId());
		return "/sys/manageAdminUser/qrCode";
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @description 图片下载
	 * @return
	 */
	@Permission(value = "/manageAdminUser/getQRcode")
	@RequestMapping(value = "/getpic", method = RequestMethod.GET)
	public String getpic(HttpServletRequest request, HttpServletResponse response, Model model)
	{
		ManageAdminUser manageadminuser = requst2Bean(request, ManageAdminUser.class);
		Integer type = RequestHandler.getInteger(request, "type");
		try{
			manageadminuser = manageadminuserService.getManageAdminUser(manageadminuser);
			if(manageadminuser!=null&&type!=null){
				Integer size = 0;
				String _size = "";
				switch(type){
				case 1 : 
					size = 302;
					_size = "8x8";
					break;
				case 2 : 
					size = 453;
					_size = "12x12";
					break;
				case 3 :
					size = 567;
					_size = "15x15";
					break;
				case 4:
					size = 1134;
					_size = "30x30";
					break;
				case 5:
					_size = "50x50";
					size = 1890;
					break;
				default:
					_size = "8x8";
					size = 302;
					break;
				}
				String contextPath = ConfigConstants.UPLOAD_FILE_ROOT;;
				File in = new File(contextPath+"/"+"download"+"/" +manageadminuser.getMobile()+".jpg");
				if(!in.exists()){
					HttpUtils.downFile(contextPath+"/"+"download"+"/" +manageadminuser.getMobile()+".jpg", manageadminuser.getQr_code_url());
					in = new File(contextPath+"/"+"download"+"/" +manageadminuser.getMobile()+".jpg");
				}
				File out = new File(contextPath+"/"+"download"+"/" +manageadminuser.getMobile()+".jpg");
				PicCompression.resize(in, out, size);
				model.addAttribute("name",manageadminuser.getMobile()+"-"+_size+".jpg");
				model.addAttribute("link", contextPath+"/"+"download"+"/" +manageadminuser.getMobile()+".jpg");
			}
		} catch (Exception e)
		{
			writeErrorMsg(e.getLocalizedMessage(), null, response);
		}
		return "/common/excelup";
	}
}
