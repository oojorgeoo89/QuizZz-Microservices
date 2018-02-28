package jorge.rv.quizzz.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jorge.rv.quizzz.controller.utils.HttpUtils;
import jorge.rv.quizzz.exceptions.InvalidTokenException;
import jorge.rv.quizzz.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.model.User;

@Controller
@RequestMapping("/user")
public class UserManagementController {

	@Autowired
	HttpUtils httpUtils;
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String homeLoggedIn() {
		return "home";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String forgotPassword() {
		return "forgotPassword";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public ModelAndView forgotPassword(String email) {
		
		try {
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			restTemplate.exchange("http://user-service/api/users/forgotPassword?email=" + email, HttpMethod.POST, null, new ParameterizedTypeReference<User>() {});
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("header", messageSource.getMessage("label.forgotpassword.success.header", null, null));
			mav.addObject("subheader", messageSource.getMessage("label.forgotpassword.success.subheader", null, null));
			mav.setViewName("simplemessage");
			
			return mav;
			
		} catch (RestClientException e) {
			throw new ResourceUnavailableException();
		}
		
	}

	@RequestMapping(value = "/{user_id}/resetPassword", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView resetPassword(@PathVariable Long user_id, String token) {
		
		try {
			Map<String, String> params = new HashMap<>();
			params.put("token", token);
			String url = httpUtils.generateUrl("user-service", "/api/users/" + user_id + "/verifyResetPasswordToken", params);
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			ResponseEntity<User> resource = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<User>() {});
			User user = resource.getBody();

			ModelAndView mav = new ModelAndView();
			mav.addObject("user", user);
			mav.addObject("token", token);
			mav.setViewName("resetPassword");

			return mav;
		} catch (RestClientException e) {
			throw new InvalidTokenException();
		}
	}

	@RequestMapping(value = "/{user_id}/resetPassword", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public String resetPassword(@PathVariable Long user_id, String token, String password) {
		try {
			
			Map<String, String> params = new HashMap<>();
			params.put("token", token);
			params.put("password", password);
			String url = httpUtils.generateUrl("user-service", "/api/users/" + user_id + "/resetPassword", params);
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<User>() {});

			return "login";
			
		} catch (RestClientException e) {
			throw new InvalidTokenException();
		}
	}
}
