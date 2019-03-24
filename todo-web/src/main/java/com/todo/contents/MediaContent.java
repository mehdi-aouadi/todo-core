package com.todo.contents;

import com.todo.model.MediaType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class MediaContent {

  private UUID id;
  private MediaType type;
  private URL mediaResourceUrl;

}
