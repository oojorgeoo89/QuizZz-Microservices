package jorge.rv.quizzz.userservice.service;

import jorge.rv.quizzz.userservice.model.User;

public interface RegistrationService {
	User startRegistration(User user);
	User continueRegistration(User user, String token);
	boolean isRegistrationCompleted(User user);
}
