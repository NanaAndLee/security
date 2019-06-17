package com.lee.security.browser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger( getClass() );

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info( "登陆用户名：" + username );
        //根据用户名从数据库拿取信息
//        拿到信息之后然后进行比对  如果密码错误，或者账户不存在将实现类里面的对应属性设置成false就会登陆失败
        /*
        {
            判断逻辑
        }
         */

        /**
         * 参数：
         * username : 返回前台传递过来的参数
         * password : 返回数据库拿到的参数，Security会自动校验如果不一致，会提示登陆失败
         * AuthorityUtils.commaSeparatedStringToAuthorityList( "admin" ) : 赋予相应的权限
         */
//        User的第一个构造方法
//        return new User( username, "12345", AuthorityUtils.commaSeparatedStringToAuthorityList( "admin" ) );

        //根据用户查找用户信息
        //根据用户信息进行一些逻辑eg: 根据数据库取出的信息判断vip特权是否过期，用户是否冻结，用户密码是否正确等。。。


        /**
         * 参数 :
         * username,password后面有4个boolean类型的参数
         * password : 密码的加密应该是在用户进行注册的时候加密之后放入数据库的，取出的面膜是加密后的
         * 1  enabled ： 是否激活
         * 2  accountNonExpired ：
         * 3  credentialsNonExpired ： 证书是否过期
         * 4  accountNonLocked ：
         * 四个参数任意一个为false用户都将不能通过验证
         */
//        User的第二个构造方法
        String password = passwordEncoder.encode( "123456" );
        logger.info( "数据库取出的密码是 ： " + password );
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList( "admin" ) );
    }
}
