package jorge.rv.quizzz.userservice;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import jorge.rv.quizzz.userservice.exceptions.InvalidTokenException;
import jorge.rv.quizzz.userservice.model.ForgotPasswordToken;
import jorge.rv.quizzz.userservice.model.TokenType;
import jorge.rv.quizzz.userservice.model.User;
import jorge.rv.quizzz.userservice.service.UserManagementService;
import jorge.rv.quizzz.userservice.service.UserManagementServiceImpl;
import jorge.rv.quizzz.userservice.service.UserService;
import jorge.rv.quizzz.userservice.service.token.TokenDeliverySystem;
import jorge.rv.quizzz.userservice.service.token.TokenServiceForgotPassword;

public class UserManagementServiceTests {
	private static final String TOKEN = "token";

	UserManagementService userManagementService;

	// Mocks
	TokenServiceForgotPassword tokenService;
	TokenDeliverySystem tokenDeliverySystem;
	UserService userService;

	// Models
	User user = new User();
	ForgotPasswordToken token = new ForgotPasswordToken();;

	@Before
	public void before() {
		userService = mock(UserService.class);
		tokenService = mock(TokenServiceForgotPassword.class);
		tokenDeliverySystem = mock(TokenDeliverySystem.class);

		userManagementService = new UserManagementServiceImpl(userService, tokenService, tokenDeliverySystem);

		user.setEmail("a@a.com");
		user.setPassword("Password");

	}

	@Test
	public void resendPassword() {

		when(tokenService.generateTokenForUser(user)).thenReturn(token);

		userManagementService.resendPassword(user);

		verify(tokenDeliverySystem, times(1)).sendTokenToUser(token, user, TokenType.FORGOT_PASSWORD);

	}

	@Test(expected = InvalidTokenException.class)
	public void validateInvalidToken() {
		doThrow(new InvalidTokenException()).when(tokenService).validateTokenForUser(user, TOKEN);

		userManagementService.verifyResetPasswordToken(user, TOKEN);
	}

	@Test
	public void validateValidToken() {
		userManagementService.verifyResetPasswordToken(user, TOKEN);

		verify(tokenService, times(1)).validateTokenForUser(user, TOKEN);
	}

	@Test
	public void updatePassword() {
		userManagementService.updatePassword(user, "pass");

		verify(userService, times(1)).updatePassword(user, "pass");
	}
}
