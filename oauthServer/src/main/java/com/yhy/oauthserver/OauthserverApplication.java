package com.yhy.oauthserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
@RequestMapping
@Controller
public class OauthserverApplication extends WebSecurityConfigurerAdapter {

	  public static void main(String[] args) {
	    SpringApplication.run(OauthserverApplication.class, args);
	  }

	  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	  @Override
	  public AuthenticationManager authenticationManagerBean() throws Exception {
	      return super.authenticationManagerBean();
	  }

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@ResponseBody
	@RequestMapping("/loginOut")
	public boolean loginOut (@RequestParam("access_token")String accessToken){
		if (consumerTokenServices.revokeToken(accessToken)) {
			return true;
		}
		return false;
	}
}
