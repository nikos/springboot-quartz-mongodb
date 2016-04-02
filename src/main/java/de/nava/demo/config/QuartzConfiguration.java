package de.nava.demo.config;

import de.nava.demo.job.AutoWiringSpringBeanJobFactory;
import org.quartz.Trigger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    public static final String CONTEXT_KEY = "applicationContext";

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Qualifier("trigger1")
    private FactoryBean<? extends Trigger> simpleTriggerMyJobOne;

    @Autowired
    @Qualifier("trigger2")
    private FactoryBean<? extends Trigger> cronTriggerMyJobTwo;

    @Bean
    public AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory(){
        return new AutoWiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContextSchedulerContextKey(CONTEXT_KEY);
        scheduler.setApplicationContext(applicationContext);
        scheduler.setConfigLocation(new ClassPathResource("quartz.properties"));
        scheduler.setJobFactory(autoWiringSpringBeanJobFactory());
        // TODO: how to set triggers to a later point in time?
        scheduler.setTriggers(
                simpleTriggerMyJobOne.getObject(),
                cronTriggerMyJobTwo.getObject()
        );
        return scheduler;
    }

}