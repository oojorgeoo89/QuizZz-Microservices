package jorge.rv.quizzz.userservice.service;

import jorge.rv.quizzz.userservice.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.userservice.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.userservice.exceptions.UserAlreadyExistsException;
import jorge.rv.quizzz.userservice.model.User;

public interface UserService {
	User saveUser(User user) throws UserAlreadyExistsException;
	void delete(Long user_id) throws UnauthorizedActionException, ResourceUnavailableException;
	
	User find(Long id) throws ResourceUnavailableException;
	User findByEmail(String email) throws ResourceUnavailableException;
	User findByUsername(String username) throws ResourceUnavailableException;
	User findByUsernameOrEmail(String query);
	
	User updatePassword(User user, String password) throws ResourceUnavailableException;
	User setRegistrationCompleted(User user) throws ResourceUnavailableException;
	boolean isRegistrationCompleted(User user) throws ResourceUnavailableException;
	
}