package jorge.rv.quizzz.quizservice.controller.utils;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

public interface HttpUtils {
	RestTemplate getRestTemplate();
	String objectToJson(Object object);
	String generateUrl(String host, String path, Map<String, String> params);
}