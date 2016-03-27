package de.nava.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleService {

    public void longRunningOperation() {
        log.info("... this could take a while ...");
    }
}
