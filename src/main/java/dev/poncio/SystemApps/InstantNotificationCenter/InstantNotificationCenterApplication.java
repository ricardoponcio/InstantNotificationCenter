package dev.poncio.SystemApps.InstantNotificationCenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = GlobalMethodSecurityConfiguration.class))
public class InstantNotificationCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstantNotificationCenterApplication.class, args);
	}

}
