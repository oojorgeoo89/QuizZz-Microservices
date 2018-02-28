package jorge.rv.quizzz.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "jorge.rv.quizzz")
public class QuizzzUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzzUserServiceApplication.class, args);
	}
}
