package jorge.rv.quizzz.quizservice.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jorge.rv.quizzz.quizservice.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.quizservice.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.quizservice.model.Quiz;
import jorge.rv.quizzz.quizservice.model.Response;
import jorge.rv.quizzz.quizservice.model.Result;
import jorge.rv.quizzz.quizservice.model.User;

public interface QuizService {
	Quiz save(Quiz quiz, User user);

	Page<Quiz> findAll(Pageable pageable);

	Page<Quiz> findAllPublished(Pageable pageable);

	Page<Quiz> findQuizzesByUser(User user, Pageable pageable);

	Quiz find(Long id) throws ResourceUnavailableException;

	Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Quiz> search(String query, Pageable pageable);

	Result checkAnswers(Quiz quiz, List<Response> answersBundle);

	void publishQuiz(Quiz quiz);
}
