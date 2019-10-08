package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class Media extends Entity {

  private String name;
  private MediaType type;
  private String resourceUrl;
  private UUID adminUserIdCreatedBy;
  private UUID adminUserIdLastModifiedBy;

  @Builder
  public Media(UUID id,
               LocalDateTime creationDate,
               LocalDateTime lastModificationDate,
               String mediaName,
               MediaType type,
               String mediaResourceUrl,
               UUID adminUserIdCreatedBy,
               UUID adminUserIdLastModifiedBy) {
    super(id, creationDate, lastModificationDate);
    this.type = type;
    this.name = mediaName;
    this.resourceUrl = mediaResourceUrl;
    this.adminUserIdCreatedBy = adminUserIdCreatedBy;
    this.adminUserIdLastModifiedBy = adminUserIdLastModifiedBy;
  }

}
