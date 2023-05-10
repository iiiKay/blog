package com.kay.realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kay.dao.UserDao;
import com.kay.pojo.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名和密码查询数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, token.getUsername());
        User user = userDao.selectOne(wrapper);
        //判断用户是否存在
        if (!Objects.isNull(user)) {
            //加密密码
            Md5Hash md5Hash = new Md5Hash(token.getPassword(), ByteSource.Util.bytes(user.getSalt()));
            token.setPassword(md5Hash.toString().toCharArray());
            return new SimpleAuthenticationInfo(user, user.getPwd(), getName());
        }
        return null;
    }
}
