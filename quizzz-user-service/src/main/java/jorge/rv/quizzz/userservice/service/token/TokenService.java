package jorge.rv.quizzz.userservice.service.token;

import java.util.Date;

import jorge.rv.quizzz.userservice.exceptions.InvalidTokenException;
import jorge.rv.quizzz.userservice.model.TokenModel;
import jorge.rv.quizzz.userservice.model.User;

public interface TokenService<T extends TokenModel> {
	T generateTokenForUser(User user);
	void validateTokenForUser(User user, String token) throws InvalidTokenException;
	void invalidateToken(String token);
	void invalidateExpiredTokensPreviousTo(Date date);
}
