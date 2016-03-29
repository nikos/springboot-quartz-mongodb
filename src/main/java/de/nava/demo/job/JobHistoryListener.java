package de.nava.demo.job;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.quartz.*;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static de.nava.demo.config.QuartzConfiguration.CONTEXT_KEY;

/**
 * Listens on quartz Job lifecycle events to save them into a
 * MongoDB history collection, only finished jobs (whether successful or not are saved).
 *
 * @author Niko Schmuck
 * @see org.quartz.plugins.history.LoggingJobHistoryPlugin
 */
@Slf4j
public class JobHistoryListener implements SchedulerPlugin, JobListener {

    private String name;
    private Scheduler scheduler;
    private MongoClient mongo;

    public void initialize(String pname, Scheduler scheduler, ClassLoadHelper classLoadHelper) throws SchedulerException {
        this.name = pname;
        this.scheduler = scheduler;
        scheduler.getListenerManager().addJobListener(this, EverythingMatcher.allJobs());
    }

    public String getName() {
        return name;
    }

    public void start() {
        // retrieve Spring application context to setup
        try {
            log.debug("Available context keys: {}", Arrays.asList(scheduler.getContext().getKeys()));
            if (scheduler.getContext().containsKey(CONTEXT_KEY)) {
                ApplicationContext ctx = (ApplicationContext) scheduler.getContext().get(CONTEXT_KEY);
                log.info("Retrieving mongo client from context: {}", Arrays.asList(scheduler.getContext().getKeys()));
                mongo = ctx.getBean(MongoClient.class);
            } else {
                log.warn("No Spring context in scheduler, expected key: {}", CONTEXT_KEY);
            }
        } catch (SchedulerException e) {
            log.error("Unable to retrieve application context from quartz scheduler", e);
        }
    }

    public void shutdown() {
        // nothing to do...
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        // nothing to do...
    }

    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("jobWasExecuted :: {}", context);
        Trigger trigger = context.getTrigger();
        if (jobException == null) {
            writeDoc(new HashMap<String, Object>() {{
                put("ts", new Date());
                put("name", context.getJobDetail().getKey().getName());
                put("group", context.getJobDetail().getKey().getGroup());
                put("previousFireTime", trigger.getPreviousFireTime());
                put("nextFireTime", trigger.getNextFireTime());
                put("refireCount", context.getRefireCount());
                put("result", String.valueOf(context.getResult()));
            }});
            // TODO: have explict field stating hasException: true / false ?
        } else {
            writeDoc(new HashMap<String, Object>() {{
                put("ts", new Date());
                put("name", context.getJobDetail().getKey().getName());
                put("group", context.getJobDetail().getKey().getGroup());
                put("previousFireTime", trigger.getPreviousFireTime());
                put("nextFireTime", trigger.getNextFireTime());
                put("refireCount", context.getRefireCount());
                put("errMsg", jobException.getMessage());
                put("jobException", jobException.getMessage());
            }});
        }
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("jobExecutionVetoed :: {}", context);
        Trigger trigger = context.getTrigger();
        writeDoc(new HashMap<String, Object>() {{
            put("ts", new Date());
            put("name", context.getJobDetail().getKey().getName());
            put("group", context.getJobDetail().getKey().getGroup());
            put("previousFireTime", trigger.getPreviousFireTime());
            put("nextFireTime", trigger.getNextFireTime());
            put("refireCount", context.getRefireCount());
            put("veto", true);
        }});
    }


    private void writeDoc(Map<String, Object> keys) {
        if (mongo != null) {
            // TODO: make configurable / use repository ?
            // TODO: set expire TTL based on 'ts' field (ie. 7 days)
            mongo.getDatabase("jobs-demo").getCollection("job_history")
                    .insertOne(new Document(keys));
        }
    }

}
