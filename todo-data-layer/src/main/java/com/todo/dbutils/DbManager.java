package com.todo.dbutils;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DbManager {

  private static MongoDatabase database;

  private static DbManager INSTANCE;

  private DbManager() {
    MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
    MongoClient mongoClient = new MongoClient(connectionString);
    CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    database = mongoClient.getDatabase("tododb").withCodecRegistry(pojoCodecRegistry);
  }

  private static DbManager getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DbManager();
    }
    return INSTANCE;
  }

  public static MongoCollection getMongoCollection(Class type) {
    return getInstance().database.getCollection(type.getSimpleName(), type);
  }

}
