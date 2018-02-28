package jorge.rv.quizzz.quizservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {
	
	@LoadBalanced
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
		return new OAuth2RestTemplate(resource, context);
	}
	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
}