package com.techacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DailyReportSystemApplication extends SpringBootServletInitializer {
//public class DailyReportSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyReportSystemApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DailyReportSystemApplication.class);
	}
}
