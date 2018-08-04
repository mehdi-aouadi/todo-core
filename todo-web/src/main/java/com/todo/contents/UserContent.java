package com.todo.contents;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class UserContent {

    private String id;
    private String email;
    private String phoneNumber;

}
