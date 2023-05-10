package com.kay.config;

import com.kay.shiro.JwtFilter;
import com.kay.realm.AccountRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(AccountRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        DefaultSubjectDAO dao = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionConfig = new DefaultSessionStorageEvaluator();
        //关闭shiro的session
        sessionConfig.setSessionStorageEnabled(false);
        dao.setSessionStorageEvaluator(sessionConfig);
        manager.setSubjectDAO(dao);
        SecurityUtils.setSecurityManager(manager);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        Map<String, String> map = new HashMap<>();
        map.put("/user/login", "anon");
        map.put("/user/register", "anon");
        map.put("/images/**", "anon");
        map.put("/blog/**", "jwt");
        map.put("/reply/**", "jwt");
        map.put("/comment/**", "jwt");
        map.put("/user/verify", "jwt");
        map.put("/user/edit", "jwt");
        map.put("/user/uploadImg", "jwt");
        map.put("/user/getInfo", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AccountRealm realm() {
        return new AccountRealm();
    }
}
