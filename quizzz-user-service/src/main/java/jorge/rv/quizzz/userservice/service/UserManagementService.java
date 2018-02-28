package jorge.rv.quizzz.userservice.service;

import jorge.rv.quizzz.userservice.model.User;

public interface UserManagementService {
	void resendPassword(User user);
	void verifyResetPasswordToken(User user, String token);
	void updatePassword(User user, String password);
}
