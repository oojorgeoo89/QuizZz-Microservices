package jorge.rv.quizzz.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import jorge.rv.quizzz.gateway.filters.RedirectFilter;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class QuizzzApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzzApiGatewayApplication.class, args);
	}
	
	@Bean
	public RedirectFilter getRedirectFilter() {
		return new RedirectFilter();
	}
}
