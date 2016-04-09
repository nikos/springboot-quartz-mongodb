package de.nava.demo.repository;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author Niko Schmuck
 */
@Repository
public class JobHistoryRepository {

    private MongoClient mongo;


    public void add(Map<String, Object> keys) {
        if (mongo != null) {
            // TODO: make configurable / use repository ?
            // TODO: set expire TTL based on 'ts' field (ie. 7 days)
            mongo.getDatabase("jobs-demo").getCollection("job_history")
                    .insertOne(new Document(keys));
        }
    }

}
