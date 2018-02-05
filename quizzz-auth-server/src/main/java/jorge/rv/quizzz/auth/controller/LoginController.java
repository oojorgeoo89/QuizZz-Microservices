package jorge.rv.quizzz.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jorge.rv.quizzz.auth.model.User;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String login(@ModelAttribute User user) {
		return "login";
	}
	
	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public String loginError(@ModelAttribute User user, Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}
	
}
