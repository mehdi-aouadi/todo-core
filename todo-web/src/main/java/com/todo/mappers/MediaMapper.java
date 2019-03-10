package com.todo.mappers;

import com.todo.contents.MediaContent;
import com.todo.model.Media;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MediaMapper {

  MediaMapper INSTANCE = Mappers.getMapper(MediaMapper.class);

  MediaContent domainToContent(Media media);
  Media contentToDomain(MediaContent mediaContent);

}
