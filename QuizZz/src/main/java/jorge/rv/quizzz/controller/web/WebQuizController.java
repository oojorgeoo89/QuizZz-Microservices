package jorge.rv.quizzz.controller.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import jorge.rv.quizzz.controller.utils.HttpUtils;
import jorge.rv.quizzz.controller.utils.RestVerifier;
import jorge.rv.quizzz.exceptions.ModelVerificationException;
import jorge.rv.quizzz.model.AuthenticatedUser;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.model.Quiz;

@Controller
public class WebQuizController {
	
	@Autowired
	HttpUtils httpUtils;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(@AuthenticationPrincipal AuthenticatedUser user, Map<String, Object> model) {
		return "createQuiz";
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result,
			Map<String, Object> model) {
		Quiz newQuiz;

		try {
			RestVerifier.verifyModelResult(result);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			String jsonObject = httpUtils.objectToJson(quiz);
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject, headers);
			
			RestTemplate restTemplate = httpUtils.getRestTemplate();
			ResponseEntity<Quiz> resource = restTemplate.exchange("http://quiz-service/api/quizzes", HttpMethod.POST, entity, new ParameterizedTypeReference<Quiz>() {});
			newQuiz = resource.getBody();
			
		} catch (ModelVerificationException e) {
			return "createQuiz";
		}

		return "redirect:/editQuiz/" + newQuiz.getId();
	}

	@RequestMapping(value = "/editQuiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editQuiz(@PathVariable long quiz_id) {
		RestTemplate restTemplate = httpUtils.getRestTemplate();
		ResponseEntity<Quiz> resource = restTemplate.exchange("http://quiz-service/api/quizzes/" + Long.toString(quiz_id), HttpMethod.GET, null, new ParameterizedTypeReference<Quiz>() {});
		Quiz quiz = resource.getBody();

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("editQuiz");

		return mav;
	}

	@RequestMapping(value = "/editAnswer/{question_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editAnswer(@PathVariable long question_id) {
		RestTemplate restTemplate = httpUtils.getRestTemplate();
		ResponseEntity<Question> resource = restTemplate.exchange("http://quiz-service/api/questions/" + Long.toString(question_id), HttpMethod.GET, null, new ParameterizedTypeReference<Question>() {});
		Question question = resource.getBody();

		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.setViewName("editAnswers");

		return mav;
	}

	@RequestMapping(value = "/quiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView getQuiz(@PathVariable long quiz_id) {
		RestTemplate restTemplate = httpUtils.getRestTemplate();
		ResponseEntity<Quiz> resource = restTemplate.exchange("http://quiz-service/api/quizzes/" + Long.toString(quiz_id), HttpMethod.GET, null, new ParameterizedTypeReference<Quiz>() {});
		Quiz quiz = resource.getBody();

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("quizView");

		return mav;
	}

	@RequestMapping(value = "/quiz/{quiz_id}/play", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView playQuiz(@PathVariable long quiz_id) {
		RestTemplate restTemplate = httpUtils.getRestTemplate();
		ResponseEntity<Quiz> resource = restTemplate.exchange("http://quiz-service/api/quizzes/" + Long.toString(quiz_id), HttpMethod.GET, null, new ParameterizedTypeReference<Quiz>() {});
		Quiz quiz = resource.getBody();

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("playQuiz");

		return mav;
	}
}
