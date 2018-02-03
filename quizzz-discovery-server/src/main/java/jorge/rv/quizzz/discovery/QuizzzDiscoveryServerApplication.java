package jorge.rv.quizzz.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class QuizzzDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzzDiscoveryServerApplication.class, args);
	}
}
