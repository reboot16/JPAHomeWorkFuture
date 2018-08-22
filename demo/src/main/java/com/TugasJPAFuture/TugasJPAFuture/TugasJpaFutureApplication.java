package com.TugasJPAFuture.TugasJPAFuture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TugasJpaFutureApplication {
	public static void main(String[] args) {
		SpringApplication.run(TugasJpaFutureApplication.class, args);
	}
}
