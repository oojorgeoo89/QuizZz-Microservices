package jorge.rv.quizzz.userservice.service.token;

import org.springframework.scheduling.annotation.Async;

import jorge.rv.quizzz.userservice.model.TokenModel;
import jorge.rv.quizzz.userservice.model.TokenType;
import jorge.rv.quizzz.userservice.model.User;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}
