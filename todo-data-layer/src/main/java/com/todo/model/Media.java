package com.todo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Media extends Entity {

  private MediaType type;
  private URL mediaResourceUrl;
  private UUID adminUserIdCreatedBy;
  private UUID adminUserIdLastModifiedBy;

  @Builder
  public Media(UUID id,
               LocalDateTime creationDate,
               LocalDateTime lastModificationDate,
               MediaType type,
               URL mediaResourceUrl,
               UUID adminUserIdCreatedBy,
               UUID adminUserIdLastModifiedBy) {
    super(id, creationDate, lastModificationDate);
    this.type = type;
    this.mediaResourceUrl = mediaResourceUrl;
    this.adminUserIdCreatedBy = adminUserIdCreatedBy;
    this.adminUserIdLastModifiedBy = adminUserIdLastModifiedBy;
  }

}
