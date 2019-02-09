package com.todo.model;

import lombok.*;

import java.net.URL;
import java.util.UUID;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class Media extends Entity {

    private MediaType type;
    private URL mediaResource;
    private UUID adminUserIdCreatedBy;
    private UUID adminUserIdLastModifiedBy;

}
