package com.todo.mappers;

import com.todo.contents.UserContent;
import com.todo.model.*;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public class UserMapperTest {

    private final UUID userId = UUID.randomUUID();
    private final UUID userProfileId = UUID.randomUUID();
    private final UUID userHistoryId = UUID.randomUUID();
    private final UUID firstFinishedProgramId = UUID.randomUUID();
    private final UUID secondFinishedProgramId = UUID.randomUUID();
    private final String firstProgramDescription = "The first program description";
    private final String secondProgramDescription = "The second program description";
    private final String userEmail = "user@email.com";
    private final String firstName = "John";
    private final String lastName = "Galt";
    private final String address = "66 tod street";
    private final String phoneNumber = "666 666 666";
    private final String userName = "who_is_john_galt";


    private final LocalDateTime now = LocalDateTime.now();
    private User user;
    private UserProfile userProfile;
    private UserHistory userHistory;
    private UserContent userContent;

    @Test
    public void userToContentTest() {
        user = new User();
        user.setId(userId);
        userProfile = UserProfile.builder()
            .id(userProfileId)
            .firstName(firstName)
            .lastName(lastName)
            .creationDate(now)
            .lastModificationDate(now)
            .address(address)
            .email(userEmail)
            .phoneNumber(phoneNumber)
            .userName(userName)
            .build();
        user.setUserProfile(userProfile);
        Program firstProgram = Program.builder().description(firstProgramDescription).build();
        Program secondProgram = Program.builder().description(secondProgramDescription).build();
        AssignedProgram firstAssignedProgram = AssignedProgram.builder()
            .id(firstFinishedProgramId)
            .program(firstProgram)
            .build();
        AssignedProgram secondAssignedProgram = AssignedProgram.builder()
            .id(secondFinishedProgramId)
            .program(secondProgram)
            .build();
        userHistory = UserHistory.builder()
            .id(userHistoryId)
            .creationDate(now)
            .lastModificationDate(now)
            .finishedAssignedPrograms(Arrays.asList(firstAssignedProgram))
            .cancelledAssignedPrograms(Arrays.asList(secondAssignedProgram))
            .build();
        user.setUserHistory(userHistory);

        userContent = UserMapper.INSTANCE.domainToContent(user);

        assertEquals(userId, userContent.getId());
        assertEquals(userProfileId, userContent.getUserProfile().getId());
        assertEquals(firstName, userContent.getUserProfile().getFirstName());
        assertEquals(lastName, userContent.getUserProfile().getLastName());
        assertEquals(userName, userContent.getUserProfile().getUserName());
        assertEquals(address, userContent.getUserProfile().getAddress());
        assertEquals(phoneNumber, userContent.getUserProfile().getPhoneNumber());
        assertEquals(userEmail, userContent.getUserProfile().getEmail());
        assertEquals(phoneNumber, userContent.getUserProfile().getPhoneNumber());
        assertEquals(userHistoryId, userContent.getUserHistory().getId());
        assertEquals(firstAssignedProgram.getId(), userContent.getUserHistory().getFinishedAssignedPrograms().get(0).getId());
        assertEquals(firstAssignedProgram.getProgram().getDescription(), userContent.getUserHistory().getFinishedAssignedPrograms().get(0).getProgram().getDescription());
        assertEquals(secondAssignedProgram.getId(), userContent.getUserHistory().getCancelledAssignedPrograms().get(0).getId());
        assertEquals(secondAssignedProgram.getProgram().getDescription(), userContent.getUserHistory().getCancelledAssignedPrograms().get(0).getProgram().getDescription());
    }

}