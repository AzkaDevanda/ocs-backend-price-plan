package com.sts.sinorita;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "com.sts.sinorita" })
@EnableJpaRepositories(basePackages = { "com.sts.sinorita.auth.repository",
		"com.sts.sinorita.repository", "com.sts.sinorita.storeProcedure",
		"com.sts.sinorita.dto" })
public class OcsApplication {

	@PostConstruct
	public void init() {
		// Mengatur timezone default JVM ke GMT+7
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
	}

	public static void main(String[] args) {
		SpringApplication.run(OcsApplication.class, args);
	}

}