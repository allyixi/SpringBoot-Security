package com.security.springboot05security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //定制请求的授权规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll()  //"/"和"/home"允许所有人访问
                    .antMatchers("/vip").hasRole("vip")  //"/vip"只允许vip角色进入
                    .anyRequest().authenticated()
                    .and() //连接两个http
                .formLogin()//1.如果访问的页面需要登陆则跳转/login页面 2.如果登陆失败重定向到/login?error 3.默认post形式的/login代表登陆请求
                    .usernameParameter("user").passwordParameter("pwd")//指定用户名和密码的名字 对应html里的表单
                    .loginPage("/login")//指定登陆页
                    .permitAll();
        //开启自动配置的注销功能
        //访问/logout 注销用户，清空session
        //注销成功后默认返回/login?logout 可通过http.logoutSuccessUrl()进行配置
        http
                .logout()
                .logoutSuccessUrl("/")//配置注销后返回页面
                .permitAll();
        //开启记住我功能
        http.rememberMe().rememberMeParameter("remeber");//与表单一致
    }


    //定制认证规则
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        UserDetails vip =
                User.withDefaultPasswordEncoder()
                        .username("vip")
                        .password("password")
                        .roles("USER","vip")//添加多个角色,不区分大小写
                        .build();

        return new InMemoryUserDetailsManager(user,vip);
    }
}
