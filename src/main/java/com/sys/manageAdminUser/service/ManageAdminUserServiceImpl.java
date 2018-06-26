package com.sys.manageAdminUser.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.sys.adminRoleMethod.model.AdminRoleMethod;
import com.sys.adminRoleMethod.service.AdminRoleMethodService;
import com.sys.manageAdminRole.dao.ManageAdminRoleDao;
import com.sys.manageAdminRole.model.ManageAdminRole;
import com.sys.manageAdminRoleColumn.dao.ManageAdminRoleColumnDao;
import com.sys.manageAdminRoleColumn.model.ManageAdminRoleColumn;
import com.sys.manageAdminRoleColumn.service.ManageAdminRoleColumnService;
import com.sys.manageAdminUser.dao.ManageAdminUserDao;
import com.sys.manageAdminUser.model.ManageAdminUser;
import com.base.utils.ResponseList;
import com.base.utils.SessionName;

/**
 * @author	keeny
 * @time	2017-03-23 14:16:40
 */
@Service
public class ManageAdminUserServiceImpl implements ManageAdminUserService{
	@Resource
    private ManageAdminUserDao manageAdminUserDao;
	@Resource
    private ManageAdminRoleDao manageAdminRoleDao;//角色
	@Resource
    private ManageAdminRoleColumnDao manageAdminRoleColumnDao;//角色菜单权限
	@Autowired
	private AdminRoleMethodService adminRoleMethodService = null;//角色权限

	public void login(HttpSession session, String adminName) {
		ManageAdminUser manageadminuser1 = new ManageAdminUser();
		manageadminuser1.setAdminName(adminName);
		ManageAdminUser manageadminuser = manageAdminUserDao.getManageAdminUser(manageadminuser1);		
		if(manageadminuser!=null){
			Integer role_id = manageadminuser.getRole_id();
			
			if(role_id!=null){
				ManageAdminRole manageAdminRole = new ManageAdminRole();
				manageAdminRole.setRoleId(role_id);
				ManageAdminRole manageAdminRole2 = manageAdminRoleDao.getManageAdminRole(manageAdminRole);
				manageadminuser.setRoleName(manageAdminRole2.getRoleName());
			}
			initLoginSession(session,manageadminuser);
			
			manageadminuser.setLastLogin(new Date());
			manageAdminUserDao.updateManageAdminUser(manageadminuser);
			
			this.initColumn(session);
			this.initOp(session);
		}
	}
	public void initLoginSession(HttpSession session,ManageAdminUser manageadminuser){
		session.setAttribute(SessionName.ADMIN_USER_ID, manageadminuser.getAdminId());
		session.setAttribute(SessionName.ADMIN_USER_NAME, manageadminuser.getAdminName());
		session.setAttribute(SessionName.ADMIN_USER, manageadminuser);
	}
	@Override
	public void initColumn(HttpSession session) {
		Object userObj = session.getAttribute(SessionName.ADMIN_USER);
		if(userObj!=null){
			ManageAdminUser manageadminuser = (ManageAdminUser) userObj;
			if(manageadminuser.getRole_id()!=null){
				ManageAdminRoleColumn manageAdminRoleColumn = new ManageAdminRoleColumn();
				manageAdminRoleColumn.setRoleId(manageadminuser.getRole_id());
				manageAdminRoleColumn.setUse_state(1);//已分配的
				manageAdminRoleColumn.setOrder("asc");
				manageAdminRoleColumn.setSort("sort_id");
				List<ManageAdminRoleColumn> manageAdminRoleColumnBaseList = manageAdminRoleColumnDao.getManageAdminRoleColumnBaseList(manageAdminRoleColumn);
				
				if(manageAdminRoleColumnBaseList!=null){
					List<ManageAdminRoleColumn> childColumnList = new ArrayList<ManageAdminRoleColumn>();
					List<ManageAdminRoleColumn> parentColumnList = new ArrayList<ManageAdminRoleColumn>();
					
					// 设置首页入口
					ManageAdminRoleColumn mainColummn = new ManageAdminRoleColumn();
					mainColummn.setParentColumnID(-1);
					mainColummn.setColumnId(-11);
					mainColummn.setColumnName("首页");
					mainColummn.setColumnImg("glyphicon glyphicon-home");
					ManageAdminRoleColumn mainChildColumn = new ManageAdminRoleColumn();
					mainChildColumn.setColumnName("首页");
					mainChildColumn.setColumnUrl("/main");
					mainChildColumn.setColumnId(0);
					mainChildColumn.setParentColumnID(mainColummn.getColumnId());
					List<ManageAdminRoleColumn> mainChildList = new ArrayList<>();
					mainChildList.add(mainChildColumn);
					mainColummn.setChildList(mainChildList);
					parentColumnList.add(mainColummn);
					// 设置首页入口
					
					for (ManageAdminRoleColumn manageAdminRoleColumn2 : manageAdminRoleColumnBaseList) {
						if(manageAdminRoleColumn2.getParentColumnID()!=null && manageAdminRoleColumn2.getParentColumnID() == -1){
							parentColumnList.add(manageAdminRoleColumn2);
						}else{
							childColumnList.add(manageAdminRoleColumn2);
						}
					}
					for (ManageAdminRoleColumn parentColumn : parentColumnList) {
						List<ManageAdminRoleColumn> childList = parentColumn.getChildList();
						if(childList == null)
							childList = new ArrayList<ManageAdminRoleColumn>();
						for (ManageAdminRoleColumn childColumn : childColumnList) {
							Integer parentColumnID = childColumn.getParentColumnID();
							if(parentColumnID!=null && parentColumnID.intValue() == parentColumn.getColumnId()){
								childList.add(childColumn);
							}
						}
						parentColumn.setChildList(childList);
					}
					session.setAttribute(SessionName.SYSTEM_COLUMN_LIST, parentColumnList);//系统菜单
				}
			}
		}
	}
	@Override
	public void initOp(HttpSession session) {
		Object userObj = session.getAttribute(SessionName.ADMIN_USER);
		if(userObj!=null){
			ManageAdminUser manageadminuser = (ManageAdminUser) userObj;
			AdminRoleMethod adminRoleMethod = new AdminRoleMethod();
			adminRoleMethod.setRoleId(manageadminuser.getRole_id());
			List<AdminRoleMethod> adminRoleMethodBaseList = adminRoleMethodService.getAdminRoleMethodBaseList(adminRoleMethod);
			session.setAttribute(SessionName.USER_ROLE_LIST, adminRoleMethodBaseList);//权限列表
		}
	}
    public ResponseList<ManageAdminUser> getManageAdminUserList(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserList(manageAdminUser);
    }
    
    public List<ManageAdminUser> getManageAdminUserBaseList(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserBaseList(manageAdminUser);
    }
    
    public int getManageAdminUserListCount(ManageAdminUser manageAdminUser) {
        return manageAdminUserDao.getManageAdminUserListCount(manageAdminUser);
    }

    public ManageAdminUser getManageAdminUser(ManageAdminUser manageAdminUser) { 
        return manageAdminUserDao.getManageAdminUser(manageAdminUser);
    }

    public int insertManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.insertManageAdminUser(manageAdminUser);
    }
    
    public int checkAdminName(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.checkAdminName(manageAdminUser);
    }

    public int updateManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.updateManageAdminUser(manageAdminUser);
    }
    
    public int unBindWx(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.unBindWx(manageAdminUser);
    }
    
    public int updateDefaultDB(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.updateDefaultDB(manageAdminUser);
    }
    
    public int updateManageAdminUserByOpenId(ManageAdminUser manageAdminUser){
    	return manageAdminUserDao.updateManageAdminUserByOpenId(manageAdminUser);
    }
    
    public int removeManageAdminUser(ManageAdminUser manageAdminUser){
        return manageAdminUserDao.removeManageAdminUser(manageAdminUser);
    }
    
    public ManageAdminUser getManageAdminUserById(Integer adminId){
    	return manageAdminUserDao.getManageAdminUserById(adminId);
    }
}
