package de.nava.demo;

import de.nava.demo.testsupport.Application4Testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application4Testing.class)
public class ApplicationIT {

    @Test
    public void contextLoads() {
    }

}
