package jorge.rv.quizzz.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import jorge.rv.quizzz.auth.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.auth.model.User;

public interface UserService extends UserDetailsService {
	User findByEmail(String email) throws ResourceUnavailableException;
	User findByUsername(String username) throws ResourceUnavailableException;
}