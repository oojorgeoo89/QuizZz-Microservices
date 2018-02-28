package jorge.rv.quizzz.controller.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jorge.rv.quizzz.controller.utils.HttpUtils;
import jorge.rv.quizzz.controller.utils.RestVerifier;
import jorge.rv.quizzz.exceptions.InvalidTokenException;
import jorge.rv.quizzz.exceptions.ModelVerificationException;
import jorge.rv.quizzz.model.User;

@Controller
@RequestMapping("/user")
public class RegistrationController {

	@Autowired
	private HttpUtils httpUtils;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String showRegistrationForm(@ModelAttribute User user) {
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public ModelAndView signUp(@ModelAttribute @Valid User user, BindingResult result) {
		ModelAndView mav = new ModelAndView();

		try {
			RestVerifier.verifyModelResult(result);
		} catch (ModelVerificationException e) {
			mav.setViewName("registration");
			return mav;
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String jsonObject = httpUtils.objectToJson(user);
		HttpEntity<String> entity = new HttpEntity<String>(jsonObject, headers);
		
		try {
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			ResponseEntity<User> resource = restTemplate.exchange("http://user-service/api/users/registration", HttpMethod.POST, entity, new ParameterizedTypeReference<User>() {});
			User newUser = resource.getBody();
			
			return registrationStepView(newUser, mav);
			
		} catch (RestClientException e) {
			result.rejectValue("email", "label.user.emailInUse");
			mav.setViewName("registration");
			return mav;
		}

	}

	@RequestMapping(value = "/{user_id}/continueRegistration", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView nextRegistrationStep(@PathVariable Long user_id, String token) {
		try {
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			ResponseEntity<User> resource = restTemplate.exchange("http://user-service/api/users/" + user_id + "/continueRegistration?token=" + token, HttpMethod.POST, null, new ParameterizedTypeReference<User>() {});
			User user = resource.getBody();
			
			ModelAndView mav = new ModelAndView();
			return registrationStepView(user, mav);
			
		} catch (RestClientException e) {
			throw new InvalidTokenException();
		}
	}

	private ModelAndView registrationStepView(User user, ModelAndView mav) {

		if (user.getEnabled()) {
			mav.addObject("header", messageSource.getMessage("label.registration.step2.header", null, null));
			mav.addObject("subheader", messageSource.getMessage("label.registration.step2.subheader", null, null));
			mav.setViewName("simplemessage");
		} else {
			mav.addObject("header", messageSource.getMessage("label.registration.step1.header", null, null));
			mav.addObject("subheader", messageSource.getMessage("label.registration.step1.subheader", null, null));
			mav.setViewName("simplemessage");
		}

		return mav;
	}
}
