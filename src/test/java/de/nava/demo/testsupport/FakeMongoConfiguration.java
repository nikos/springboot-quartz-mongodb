package de.nava.demo.testsupport;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Profile("test")
@Configuration
public class FakeMongoConfiguration {

    @Autowired
    private Environment env;

    protected String getDatabaseName() {
        return env.getRequiredProperty("spring.data.mongodb.database"); // mongo.db.name
    }

    // MongoCLient???
    public Mongo mongo() throws Exception {
        return new Fongo(getDatabaseName()).getMongo();
    }

}
