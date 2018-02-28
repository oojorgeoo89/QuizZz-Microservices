package jorge.rv.quizzz.userservice.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import jorge.rv.quizzz.userservice.model.AuthenticatedUser;
import jorge.rv.quizzz.userservice.model.User;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuthConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
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
