package de.nava.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    public static final String CONTEXT_KEY = "applicationContext";

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey("applicationContext");
        scheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        // scheduler.setAutoStartup(false);  // to not automatically start after startup
        scheduler.setWaitForJobsToCompleteOnShutdown(true);
        return scheduler;
    }

}