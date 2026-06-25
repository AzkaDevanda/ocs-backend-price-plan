package com.ocs.portal;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = { "com.ocs.portal", "com.sinorita" })
@EnableJpaRepositories(basePackages = {
		"com.ocs.portal.auth.repository",
		"com.ocs.portal.repository",
		"com.ocs.portal.storeProcedure.impl" })
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