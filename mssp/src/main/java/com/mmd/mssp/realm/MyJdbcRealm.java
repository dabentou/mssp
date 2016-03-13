package com.mmd.mssp.realm;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommRolePermission;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.service.CommRolePermissionService;
import com.mmd.mssp.service.CommRoleService;
import com.mmd.mssp.service.CommUserService;

public class MyJdbcRealm extends AuthorizingRealm{
    private static final Logger log = LoggerFactory.getLogger(JdbcRealm.class);
    
    @Resource
    CommUserService  commUserService;
    @Resource
    CommRolePermissionService commRolePermissionService;
    @Resource
    CommRoleService commRoleService;
	@Resource
	PasswordService passwordService;
    
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        String password = this.getPasswordForUser(username);

        if (password == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

        return info;
    }

    private String getPasswordForUser(String username){

    	CommUser currentUser =commUserService.findByLoginName(username);
    	String password = null;
    	if(currentUser != null){
    		password = currentUser.getPassword();
    	}
        return password;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        //null usernames are invalid
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }

        String username = (String) this.getAvailablePrincipal(principals);

        Set<String> roleNames = null;
        Set<String> permissions = null;

        // Retrieve roles and permissions from database
        roleNames = this.getRoleNamesForUser(username);
        permissions = this.getPermissions(username, roleNames);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;

    }

    protected Set<String> getRoleNamesForUser(String username) {
    	
    	CommUser currentUser =commUserService.findByLoginName(username);
    	
    	Set<String> roleNames = new LinkedHashSet<String>();

        if(currentUser != null) {

            CommRole role = currentUser.getCommRole();
            String roleName = null;
            if(role != null){
            	roleName = role.getRoleName();
            }
            // Add the role to the list of names if it isn't null
            if (roleName != null) {
                roleNames.add(roleName);
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Null role name found while retrieving role names for user [" + username + "]");
                }
            }
        }
    	
        return roleNames;
    }

    protected Set<String> getPermissions(String username, Collection<String> roleNames) {

    	Set<String> permissions = new LinkedHashSet<String>();
    	
        for (String roleName : roleNames) {
        	CommRole role = commRoleService.findByRoleName(roleName);
			List<CommRolePermission> permitionList = commRolePermissionService.findByCommRoleId(role);
        	// Loop over results and add each returned role to a set
        	for (CommRolePermission permission : permitionList) {
        		
                String permissionString = permission.getCommPermission().getPermissionCode();

                // Add the permission to the set of permissions
                permissions.add(permissionString);
			}

        }
        return permissions;
    }

	public PasswordService getPasswordService() {
		return passwordService;
	}

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

    
}
