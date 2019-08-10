package com.loha.flippedclassroom.config.security.config;

import com.loha.flippedclassroom.config.security.UserDetailsServiceImpl;
import com.loha.flippedclassroom.config.security.handler.IdentityCheckFailureHandler;
import com.loha.flippedclassroom.config.security.handler.IdentityCheckSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置手机端的security
 *
 * @author zhoujian
 * @date 2018/12/15
 */
@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsServiceImp;
    private final IdentityCheckSuccessHandler identityCheckSuccessHandler;
    private final IdentityCheckFailureHandler identityCheckFailureHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImp, IdentityCheckSuccessHandler identityCheckSuccessHandler,
                               IdentityCheckFailureHandler identityCheckFailureHandler)
    {
        this.userDetailsServiceImp=userDetailsServiceImp;
        this.identityCheckSuccessHandler=identityCheckSuccessHandler;
        this.identityCheckFailureHandler=identityCheckFailureHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImp);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**","/static/**", "/js/**","/img/**","/fonts/**","/plugins/**").permitAll()
                .antMatchers("/login","/","/forgetPwd","/password").permitAll()
                .anyRequest()
                .authenticated()
                .antMatchers( "/student").hasRole("STUDENT")
                .antMatchers("/teacher").hasRole("TEACHER")
                .and().formLogin().loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("account").passwordParameter("password")
                .successHandler(identityCheckSuccessHandler)
                .failureHandler(identityCheckFailureHandler)
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .permitAll().and().csrf().disable()
                .headers().frameOptions().sameOrigin();
    }
}
