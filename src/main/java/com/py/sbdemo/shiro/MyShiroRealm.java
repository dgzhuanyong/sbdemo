package com.py.sbdemo.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

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

import com.py.sbdemo.entity.God;
import com.py.sbdemo.service.GodService;

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
	
	/**
	 * 重写shior:hasPermission 标签 支持复杂表达式（使用逆波兰表达式计算）
	 * 其中操作符不限大小写，支持and、or、not、&&、||、！
	 * 唯一缺点就是为了解析方便，所有内容必须用空格隔开
	 * 
	 * create OR update OR delete
	 *	 ( create Or update ) OR  NOT delete
	 *	 ( create && update ) OR  ! delete
	 */
	
	/**
	 * 支持的运算符和运算符优先级
	 */
	@SuppressWarnings("serial")
	public static Map<String, Integer> expMap = new HashMap<String, Integer>(){{
        put("not",0);
        put("!"  ,0);

        put("and",0);
        put("&&" ,0);

        put("or" ,0);
        put("||" ,0);

        put("("  ,1);
        put(")"  ,1);
    }};
    
    public static final Set<String> expList = expMap.keySet();
	
	/**
     * 	重写用户授权过程
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
    	//获得逆波兰表达式
        Stack<String> exp = getExp(expList, permission);
        if (exp.size() == 1){
            return super.isPermitted(principals, exp.pop());
        }
        List<String> expTemp = new ArrayList<>();
        //将其中的权限字符串解析成true , false
        for(String temp : exp){
            if (expList.contains(temp)){
                expTemp.add(temp);
            }else{
                expTemp.add(Boolean.toString(super.isPermitted(principals, temp)) );
            }
        }
        //计算逆波兰
        return computeRpn(expList, expTemp);
    }

    
    /**
     * 计算逆波兰
     * @param expList
     * @param exp
     * @return
     */
    private static boolean computeRpn(Collection<String> expList,Collection<String> exp){
        Stack<Boolean> stack = new Stack<>();
        for(String temp : exp){
            if (expList.contains(temp)){
                if ("!".equals(temp) || "not".equals(temp)){
                    stack.push( !stack.pop() );
                }else if ("and".equals(temp) || "&&".equals(temp)){
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 && s2);
                }else{
                    Boolean s1 = stack.pop();
                    Boolean s2 = stack.pop();
                    stack.push(s1 || s2);
                }
            }else{
                stack.push(Boolean.parseBoolean(temp));
            }
        }
        if (stack.size() > 1){
            throw new RuntimeException("compute error！ stack: "+ exp.toString());
        }else{
            return stack.pop();
        }
    }

    /**
     * 获得逆波兰表达式
     * @param expList
     * @param exp
     * @return
     */
    private static Stack<String> getExp(Collection<String> expList, String exp) {
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        for (String str : exp.split(" ")){
            str = str.trim();
            String strL = str.toLowerCase();
            if ("".equals(str)){
                continue;
            }
            if ("(".equals(str)){
                //左括号
                s1.push(str);
            }else if (")".equals(str)){
                //右括号
                while(!s1.empty()){
                    String temp = s1.pop();
                    if ("(".equals(temp)){
                        break;
                    }else{
                        s2.push(temp);
                    }
                }
            }else if(expList.contains(strL)){
                //操作符
                if (s1.empty()){
                    s1.push(strL);
                }else {
                    String temp = s1.peek();
                    if ("(".equals(temp) || ")".equals(temp)){
                        s1.push(strL);
                    }else if(expMap.get(strL) >= expMap.get(temp)){
                        s1.push(strL);
                    }else{
                        s2.push(s1.pop());
                        s1.push(strL);
                    }
                }
            }else{
                //运算数
                s2.push(str);
            }
        }
        while(!s1.empty()){
            s2.push(s1.pop());
        }
        return s2;
    }
	
}
