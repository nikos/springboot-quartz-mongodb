package de.nava.demo.config;

import de.nava.demo.job.AutoWiringSpringBeanJobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class QuartzConfiguration {

    public static final String CONTEXT_KEY = "applicationContext";

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        return new AutoWiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey(CONTEXT_KEY);
        scheduler.setApplicationContext(applicationContext);
        scheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        scheduler.setJobFactory(springBeanJobFactory());
        // scheduler.setAutoStartup(false);  // to not automatically start after startup
        return scheduler;
    }

}