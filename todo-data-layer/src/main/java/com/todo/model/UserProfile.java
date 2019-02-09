package com.todo.model;

import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class UserProfile extends Entity {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

}
