package jorge.rv.quizzz.auth.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpUtils {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;		
	}

}
