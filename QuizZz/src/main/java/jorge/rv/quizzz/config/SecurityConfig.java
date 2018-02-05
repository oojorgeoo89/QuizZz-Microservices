package jorge.rv.quizzz.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import jorge.rv.quizzz.model.AuthenticatedUser;
import jorge.rv.quizzz.model.User;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest()
					.permitAll()
			.and()
				.csrf().disable();
	}
	
	@Bean
	@SuppressWarnings("unchecked")
    public PrincipalExtractor principalExtractor() {
        return map -> {
			Map<String,Object> userMap = (Map<String, Object>) map.get("principal");
        	
        	User user = new User();
        	user.setId(Long.valueOf((Integer) userMap.get("id")));
        	user.setEmail((String) userMap.get("email"));
        	user.setUsername((String) userMap.get("username"));
        	
            AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
            return authenticatedUser;
        };
    }

}