package jorge.rv.quizzz.auth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

	@RequestMapping(value = "/user")
	public Principal user(Principal user) {
		return user;
	}
	
}
