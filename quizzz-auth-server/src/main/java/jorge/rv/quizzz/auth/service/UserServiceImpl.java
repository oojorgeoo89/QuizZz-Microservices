package jorge.rv.quizzz.auth.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jorge.rv.quizzz.auth.model.User;
import jorge.rv.quizzz.auth.service.utils.HttpUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	HttpUtils httpUtils;
	
	@Autowired
	public UserServiceImpl(HttpUtils httpUtils) {
		this.httpUtils = httpUtils;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		RestTemplate restTemplate = httpUtils.getRestTemplate();

		logger.debug("Requesting user-service for username {}", username);
		ResponseEntity<User> resource = restTemplate.exchange("http://user-service/api/users/find?query=" + username, HttpMethod.GET, null, new ParameterizedTypeReference<User>() {});
		User user = resource.getBody();
		logger.debug("User-service responded with user id {}", (user == null) ? null : user.getId());
		
		if (user == null)
			throw new UsernameNotFoundException(username + " can't be resolved to any user");
		
		return user;
	}

}
