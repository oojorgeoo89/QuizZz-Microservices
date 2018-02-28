package jorge.rv.quizzz.quizservice.service.accesscontrol;

import org.springframework.stereotype.Service;

import jorge.rv.quizzz.quizservice.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.quizservice.model.AuthenticatedUser;
import jorge.rv.quizzz.quizservice.model.Quiz;

@Service("AccessControlQuiz")
public class AccessControlServiceQuiz extends AccessControlServiceUserOwned<Quiz> {

	/*
	 * As long as the user is authenticated, it can create a Quiz.
	 */
	@Override
	public void canUserCreateObject(AuthenticatedUser user, Quiz object) throws UnauthorizedActionException {
		if (user == null) {
			throw new UnauthorizedActionException();
		}
	}

}
