package jorge.rv.quizzz.controller.utils;

import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpUtilsImpl implements HttpUtils {
	
	@Autowired
	private OAuth2ClientContext clientContext;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private OAuth2RestTemplate oauth2RestTemplate;
	
	@Override
	public RestTemplate getRestTemplate() {
		
		if (clientContext.getAccessToken() == null)
			return restTemplate;
		else
			return oauth2RestTemplate;
		
	}

	@Override
	public String objectToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String generateUrl(String host, String path, Map<String, String> params) {
		URIBuilder builder = new URIBuilder();
		builder
			.setHost(host)
			.setPath(path)
			.setScheme("http");
		
		for (Map.Entry<String, String> entry: params.entrySet()) {
			builder.setParameter(entry.getKey(), entry.getValue());
		}
		
		try {
			return builder.build().toURL().toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}