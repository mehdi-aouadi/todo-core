package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.MediaContent;
import com.todo.contents.MediaPageContent;
import com.todo.model.Media;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MediaMapperDecorator implements MediaMapper {

  private MediaMapper delegate;

  @Override
  public MediaContent domainToContent(Media media) {
    return delegate.domainToContent(media);
  }

  @Override
  public List<MediaContent> domainListToContentList(List<Media> mediaList) {
    return delegate.domainListToContentList(mediaList);
  }

  @Override
  public Media contentToDomain(MediaContent mediaContent) {
    return delegate.contentToDomain(mediaContent);
  }

  @Override
  public MediaPageContent pageToMediaPageContent(Page<Media> mediaPage) {
    MediaPageContent mediaPageContent = delegate.pageToMediaPageContent(mediaPage);
    mediaPageContent.setMedias(delegate.domainListToContentList(mediaPage.getContent()));
    return mediaPageContent;
  }
}
