package com.py.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.py.entity.God;
import com.py.service.GodService;

public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private GodService godService;
	
	/**
	 * 权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		//principals.getPrimaryPrincipal();
		//simpleAuthorInfo.addStringPermissions(list);
		return simpleAuthorInfo;
	}
	
	/**
	 * 登录验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String account = token.getUsername();
		God god = godService.selectByLoginName(account);
		//用户不存在
		if(null == god) {
			throw new UnknownAccountException();
		}
		//用户已禁用
		if(god.getStatus()) {
			throw new DisabledAccountException();
		}
		ByteSource credentialsSalt = ByteSource.Util.bytes(god.getSalt());
		AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(
				god,
				god.getPassword(), 
				credentialsSalt,
				getName()
		);
		return authcInfo;
	}
	
}
