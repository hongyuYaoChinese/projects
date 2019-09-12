package com.yhy.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
        .inMemory()
        .withClient("getway")
        .authorities("WRIGTH_READ", "WRIGTH_WRITE")
        .authorizedGrantTypes("implicit", "client_credentials", "refresh_token", "password", "authorization_code")
        //权限范围
        .scopes("server")
        //权限信息
        .authorities("oauth2")
        .secret(new BCryptPasswordEncoder().encode("secret"));
    }

	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
        .tokenStore(tokenStore())
        .tokenEnhancer(jwtTokenConverter())
        .authenticationManager(authenticationManager)
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
 
    @Bean
    protected JwtAccessTokenConverter jwtTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("springcloud123");
        return converter;
    }

  @Override
  //配置授权Token的节点和Token服务
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    //配置获取Token的策略,允许表单认证，配置之后可通过表单获取Token
    oauthServer.allowFormAuthenticationForClients();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
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
    //tokenServices.setClientDetailsService(clientDetails());
    // token有效期自定义设置，默认12小时
    tokenServices.setAccessTokenValiditySeconds(60*60*12);
    // refresh_token默认30天
    tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
    return tokenServices;
  }
}
