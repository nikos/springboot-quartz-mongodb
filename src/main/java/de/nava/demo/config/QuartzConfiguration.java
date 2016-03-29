package de.nava.demo.config;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

@Configuration
public class QuartzConfiguration {

    public static final String CONTEXT_KEY = "applicationContext";

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException, SchedulerException {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey(CONTEXT_KEY);
        scheduler.setApplicationContext(applicationContext);
        scheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        return scheduler;
    }

}