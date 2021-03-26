package com.todo.dbutils;

import com.todo.config.PropertyManager;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.todo.config.Version;
import com.todo.repositories.impl.AssignedProgramRepositoryMongoImpl;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbManager.class);

  private static MongoDatabase database;

  private static MongoDbManager INSTANCE;

  private MongoDbManager() {
    String env = PropertyManager.getInstance().getProperty("todo.environment");
    LOGGER.info("Connecting to mongodb env: {}", env);
    String mongoUri;if (env.equals(Version.TEST.name()) || env.equals(Version.DEV.name())) {
      mongoUri = PropertyManager.getInstance().getProperty("dev.mongo.uri");
    } else {
      mongoUri = PropertyManager.getInstance().getProperty("production.mongo.uri");
    }
    LOGGER.info("Using mongo uri: {}", mongoUri);
    MongoClientURI connectionString = new MongoClientURI(mongoUri);
    MongoClient mongoClient = new MongoClient(connectionString);
    String dbName = PropertyManager.getInstance().getProperty("mongo.db.name");
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    database = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
  }

  private static MongoDbManager getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new MongoDbManager();
    }
    return INSTANCE;
  }

  public static MongoCollection getMongoCollection(Class type) {
    return getInstance().database.getCollection(type.getSimpleName(), type);
  }

}
