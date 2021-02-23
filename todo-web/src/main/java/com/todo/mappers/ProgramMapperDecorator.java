package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.ProgramContent;
import com.todo.contents.ProgramPageContent;
import com.todo.model.Program;
import lombok.AllArgsConstructor;

import java.util.List;

public class ProgramMapperDecorator implements ProgramMapper {

  private ProgramMapper delegate = ProgramMapper.INSTANCE;

  @Override
  public ProgramContent domainToContent(Program program) {
    return delegate.domainToContent(program);
  }

  @Override
  public List<ProgramContent> domainListToContentList(List<Program> programList) {
    return delegate.domainListToContentList(programList);
  }

  @Override
  public Program contentToDomain(ProgramContent programContent) {
    return delegate.contentToDomain(programContent);
  }

  @Override
  public ProgramPageContent pageToProgramPageContent(Page<Program> programPage) {
    ProgramPageContent programPageContent = delegate.pageToProgramPageContent(programPage);
    programPageContent.setPrograms(delegate.domainListToContentList(programPage.getContent()));
    return programPageContent;
  }
}
