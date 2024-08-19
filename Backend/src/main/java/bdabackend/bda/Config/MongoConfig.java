package bdabackend.bda.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCollection<Document> tareaCollection() {
        MongoDatabase database = MongoClients.create("mongodb://localhost:27017").getDatabase("lab3");
        return database.getCollection("Tarea");
    }

}
