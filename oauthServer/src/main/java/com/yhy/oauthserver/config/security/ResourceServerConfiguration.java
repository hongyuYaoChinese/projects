package com.yhy.oauthserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

import com.yhy.oauthserver.config.security.MyAuthenticationFailureHandler;
import com.yhy.oauthserver.config.security.MyAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    
    @Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").loginProcessingUrl("/authentication/from")
//				.successHandler(myAuthenticationSuccessHandler)//自定义登录成功处理
				.failureHandler(myAuthenticationFailureHandler)//自定义登录失败处理
        		.and().authorizeRequests()
                .antMatchers("/webjars/**", "/druid/**", "/oauth/**", "/static/**", "/login", "/error").permitAll()
                .anyRequest().access("@myPermissionService.hasPermission(request,authentication)")
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(expressionHandler);
    }
}