package de.nava.demo.testsupport;

import de.nava.demo.config.QuartzConfiguration;
import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * Avoid connecting to Mongo DB server, but rather use Quartz default RAM Job Store.
 */
@SpringBootApplication(
        scanBasePackages = "de.nava.demo",
        exclude = {MongoAutoConfiguration.class, QuartzConfiguration.class}
)
public class Application4Testing {

    public static void main(String[] args) {
        SpringApplication.run(Application4Testing.class, args);
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException, SchedulerException {
        return new SchedulerFactoryBean();
    }

}
