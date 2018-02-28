package jorge.rv.quizzz.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	
	private AuthenticationManager authenticationManager;
	private DataSource dataSource;
	
	@Autowired
	public OAuth2Config(AuthenticationManager authenticationManager, DataSource dataSource) {
		super();
		this.authenticationManager = authenticationManager;
		this.dataSource = dataSource;
	}
	
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                   .checkTokenAccess("isAuthenticated()");
    }

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
	}
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
               .withClient("quizzz-web")
               .secret("secret")
               .accessTokenValiditySeconds(2000)
               .refreshTokenValiditySeconds(-1)
               .scopes("all")
               .authorizedGrantTypes("password", "implicit", "authorization_code", "refresh_token")
               .autoApprove(true);
    }
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
}
