package jorge.rv.quizzz.quizservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.quizservice.model.Answer;
import jorge.rv.quizzz.quizservice.model.Question;

@Repository("AnswerRepository")
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	int countByQuestion(Question question);
	List<Answer> findByQuestionOrderByOrderAsc(Question question);
}
