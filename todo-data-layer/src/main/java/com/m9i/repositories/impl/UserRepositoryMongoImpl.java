package com.m9i.repositories.impl;

import com.m9i.model.User;
import com.m9i.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryMongoImpl implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryMongoImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public User getUserByEmail(String email) {
        LOGGER.info("Retrieving user by email {}");
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public void saveUser(User user) {
        LOGGER.info("Saving user {}.", user.getEmail());
        if(user.getId() == null || user.getId().isEmpty()) {
            user.setId(UUID.randomUUID().toString());
        }
        mongoTemplate.save(user);
    }
}
