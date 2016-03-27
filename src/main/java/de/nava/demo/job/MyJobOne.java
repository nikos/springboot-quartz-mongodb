package de.nava.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("jobone")
public class MyJobOne {

    protected void myTask() {
        log.info("This is Job 1.");
    }

}