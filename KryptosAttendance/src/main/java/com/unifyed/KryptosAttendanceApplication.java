package com.unifyed;

import com.unifyed.attendance.core.UnifyedTenantInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class KryptosAttendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KryptosAttendanceApplication.class, args);
	}
	
	@Bean
	public MappedInterceptor myMappedInterceptor() {
		return new MappedInterceptor(new String[]{"/**"}, new UnifyedTenantInterceptor());
	}
}

