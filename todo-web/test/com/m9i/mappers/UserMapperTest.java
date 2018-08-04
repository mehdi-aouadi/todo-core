package com.m9i.mappers;

import com.m9i.contents.UserContent;
import com.m9i.model.User;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class UserMapperTest {

    private final UUID userId = UUID.randomUUID();
    private final String userEmail = "user@email.com";
    private final String userPhone = "666";
    private User user;
    private UserContent userContent;

    @Test
    public void userToContentTest() {
        user = new User();
        user.setId(userId.toString());
        user.setEmail(userEmail);
        user.setPhoneNumber(userPhone);

        UserContent userContent = UserMapper.INSTANCE.userToContent(user);

        assertEquals(userId.toString(), userContent.getId());
        assertEquals(userEmail, userContent.getEmail());
        assertEquals(userPhone, userContent.getPhoneNumber());
    }

    @Test
    public void contentToUserTest() {
        userContent = new UserContent();
        userContent.setId(userId.toString());
        userContent.setEmail(userEmail);
        userContent.setPhoneNumber(userPhone);

        user = UserMapper.INSTANCE.contentToUser(userContent);

        assertEquals(userId.toString(), user.getId());
        assertEquals(userEmail, user.getEmail());
        assertEquals(userPhone, user.getPhoneNumber());
    }

}