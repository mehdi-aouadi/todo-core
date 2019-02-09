package com.todo.model;

import lombok.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class AdminUser extends Entity {

    private String userName;
    private String firstName;
    private String lastNAme;
    private String email;
    private char[] password;

}
