package com.yhy.oauthserver.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.yhy.oauthserver.config.RedisTokenStore;
import com.yhy.oauthserver.user.service.IUserService;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  
  @Autowired
  private MyUserDetailsService userDetailsService;
  
	@Autowired
	private IUserService userService;
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
        .inMemory()
        .withClient("getway")
        .authorities("WRIGTH_READ", "WRIGTH_WRITE")
        .authorizedGrantTypes("implicit", "client_credentials", "refresh_token", "password", "authorization_code")
        //权限范围 userService.getUserPermissionStringByName("yhy"),
        .scopes("server")
        .secret(passwordEncoder().encode("secret"));
    }

	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .tokenServices(defaultTokenServices())
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtTokenConverter())
        .tokenEnhancer(jwtTokenConverter())
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .reuseRefreshTokens(true)
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}
 
    @Bean
    protected JwtAccessTokenConverter jwtTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("ry19970418!");
        return converter;
    }

  @Override
  //配置授权Token的节点和Token服务
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    //配置获取Token的策略,允许表单认证，配置之后可通过表单获取Token
    oauthServer.realm("oauth2-resources")
    .tokenKeyAccess("permitAll()") //url:/oauth/token_key,exposes public key for token verification if using JWT tokens
    .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
    .allowFormAuthenticationForClients();
  }

  @Bean
  public TokenStore tokenStore() {
    return new RedisTokenStore(redisConnectionFactory);
  }

  /**
   *注意，自定义TokenServices的时候，需要设置@Primary，否则报错
   * @return
   */
  @Primary
  @Bean
  public DefaultTokenServices defaultTokenServices(){
    DefaultTokenServices tokenServices = new DefaultTokenServices();
    tokenServices.setTokenStore(tokenStore());
    tokenServices.setSupportRefreshToken(true);
    // token有效期自定义设置，默认12小时
    tokenServices.setAccessTokenValiditySeconds(60*60*12);
    // refresh_token默认30天
    tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
    return tokenServices;
  }
  
  @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
