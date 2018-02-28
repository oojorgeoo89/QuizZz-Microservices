package jorge.rv.quizzz.quizservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.quizservice.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	User findByUsername(String username);
}
