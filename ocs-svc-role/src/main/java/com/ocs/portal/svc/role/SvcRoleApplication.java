package com.ocs.portal.svc.role;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableCaching
@SpringBootApplication
@EntityScan(basePackages = {"com.ocs.portal.entity","com.ocs.portal.svc.role.auth.entity"})
@EnableAsync
public class SvcRoleApplication {
	
	@PostConstruct
	public void init() {
		// Mengatur timezone default JVM ke GMT+7
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Jakarta"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SvcRoleApplication.class, args);
	}

}
