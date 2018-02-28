package jorge.rv.quizzz.quizservice.service;

import java.util.List;

import jorge.rv.quizzz.quizservice.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.quizservice.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.quizservice.model.Answer;
import jorge.rv.quizzz.quizservice.model.Question;

public interface AnswerService {
	Answer save(Answer answer) throws UnauthorizedActionException;
	Answer find(Long id) throws ResourceUnavailableException;
	Answer update(Answer newAnswer) throws UnauthorizedActionException, ResourceUnavailableException;
	void delete(Answer answer) throws UnauthorizedActionException, ResourceUnavailableException;
	List<Answer> findAnswersByQuestion(Question question);
	int countAnswersInQuestion(Question question);
}
