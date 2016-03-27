package de.nava.demo.config;

import de.nava.demo.job.MyJobTwo;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuartzConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /*
    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("jobone");
        obj.setTargetMethod("myTask");
        return obj;
    }*/

    // Job  is scheduled for 3+1 times with the interval of 30 seconds
    /*
    @Bean
    public SimpleTriggerFactoryBean simpleTriggerMyJobOne() {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setRepeatInterval(30000);
        stFactory.setRepeatCount(3);
        return stFactory;
    }*/

    @Bean
    public JobDetailFactoryBean jobDetailMyJobTwo() {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        factory.setJobClass(MyJobTwo.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "HaHa");
        map.put(MyJobTwo.COUNT, 1);
        factory.setJobDataAsMap(map);
        factory.setGroup("mygroup");
        factory.setName("myjob");
        return factory;
    }

    // Job is scheduled after every 1 minute
    @Bean
    public CronTriggerFactoryBean cronTriggerMyJobTwo() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(jobDetailMyJobTwo().getObject());
        stFactory.setStartDelay(3000);
        stFactory.setGroup("mygroup");
        stFactory.setName("mytrigger");
        stFactory.setCronExpression("0 0/1 * 1/1 * ? *");
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException, SchedulerException {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setApplicationContext(applicationContext);
        //scheduler.setQuartzProperties(quartzProperties());
        scheduler.setConfigLocation(new ClassPathResource("/quartz.properties"));
        scheduler.setTriggers(
                //simpleTriggerMyJobOne().getObject(),
                cronTriggerMyJobTwo().getObject()
        );
        return scheduler;

        // ~~

        //return new StdSchedulerFactory(quartzProperties());
    }

    /*
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }*/

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}