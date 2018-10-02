package com.unifyed.attendance.core;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mongodb.MongoClientOptions;

@Configuration
@EnableMongoAuditing
public class UnifyedMongoDbConfiguration {

	@Bean
	public MongoTemplate mongoTemplate(MongoProperties properties, ObjectProvider<MongoClientOptions> options,
			Environment environment) throws Exception {
		MultiTenantMongoDbFactory factory = mongoDbFactory(properties, options, environment);
		MongoTemplate template = new MongoTemplate(factory);
		factory.setMongoTemplate(template);
		return template;
	}

	@Bean
	public MultiTenantMongoDbFactory mongoDbFactory(MongoProperties properties,
			ObjectProvider<MongoClientOptions> options, Environment environment) throws Exception {
		return new MultiTenantMongoDbFactory(properties.createMongoClient(options.getIfAvailable(), environment),
				"test");
	}

	@Bean
	public AuditorAware<String> myAuditorProvider() {
		return new SecurityAuditorAware();
	}

}

@Configuration
class ValidataionConfiguration {
	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}

// @Configuration
// class RepositoryConfig extends RepositoryRestConfigurerAdapter {
// @Override
// public void configureRepositoryRestConfiguration(RepositoryRestConfiguration
// config) {
// config.exposeIdsFor(Messaging.class);
// }
// }

class SecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		// TODO Auto-generated method stub
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (sra == null) {
			// System.out.println("fake");
			// return new Faker().internet().emailAddress();
			return "";
		} else {
			System.out.println("user");
			return sra.getRequest().getHeader("principal-user");
		}
	}

}