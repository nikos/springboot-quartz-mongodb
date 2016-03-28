package de.nava.demo.testsupport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * Avoid connecting to mongo server, but rather use in-memory Fongo
 * for testing ({@link FakeMongoConfiguration}).
 */
@SpringBootApplication(
        scanBasePackages = "de.nava.demo",
        exclude = {MongoAutoConfiguration.class}
)
public class Application4Testing {

    public static void main(String[] args) {
        SpringApplication.run(Application4Testing.class, args);
    }
}
