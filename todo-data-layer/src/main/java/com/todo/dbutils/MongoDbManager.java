package com.todo.dbutils;

import com.todo.config.PropertyManager;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.todo.config.Version;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDbManager {

  private static MongoDatabase database;

  private static MongoDbManager INSTANCE;

  private MongoDbManager() {
    String env = PropertyManager.getInstance().getProperty("todo.environment");
    String mongoUri;
    if (env == Version.TEST.name()) {
      mongoUri = PropertyManager.getInstance().getProperty("dev.mongo.uri");
    } else {
      mongoUri = PropertyManager.getInstance().getProperty("production.mongo.uri");
    }
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
