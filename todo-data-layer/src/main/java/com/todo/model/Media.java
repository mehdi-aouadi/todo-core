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

  private String mediaName;
  private MediaType type;
  private URL mediaResourceUrl;
  private UUID adminUserIdCreatedBy;
  private UUID adminUserIdLastModifiedBy;

  @Builder
  public Media(UUID id,
               LocalDateTime creationDate,
               LocalDateTime lastModificationDate,
               String mediaName,
               MediaType type,
               URL mediaResourceUrl,
               UUID adminUserIdCreatedBy,
               UUID adminUserIdLastModifiedBy) {
    super(id, creationDate, lastModificationDate);
    this.type = type;
    this.mediaName = mediaName;
    this.mediaResourceUrl = mediaResourceUrl;
    this.adminUserIdCreatedBy = adminUserIdCreatedBy;
    this.adminUserIdLastModifiedBy = adminUserIdLastModifiedBy;
  }

}
