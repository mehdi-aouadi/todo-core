package com.todo.mappers;

import com.todo.common.Page;
import com.todo.contents.AssignedProgramContent;
import com.todo.contents.AssignedProgramPageContent;
import com.todo.model.AssignedProgram;

import java.util.List;

public class AssignedProgramMapperDecorator implements AssignedProgramMapper{

    private AssignedProgramMapper delegate = AssignedProgramMapper.INSTANCE;

    @Override
    public AssignedProgramContent domainToContent(AssignedProgram assignedProgram) {
        return delegate.domainToContent(assignedProgram);
    }

    @Override
    public List<AssignedProgramContent> domainListToContentList(List<AssignedProgram> assignedProgramList) {
        return delegate.domainListToContentList(assignedProgramList);
    }

    @Override
    public AssignedProgram contentToDomain(AssignedProgramContent assignedProgramContent) {
        return null;
    }

    @Override
    public List<AssignedProgram> contentListToDomainList(List<AssignedProgramContent> assignedProgramContentList) {
        return null;
    }

    @Override
    public AssignedProgramPageContent domainPageToContentPage(Page<AssignedProgram> page) {
        AssignedProgramPageContent assignedProgramPageContent = delegate.domainPageToContentPage(page);
        assignedProgramPageContent.setAssignedPrograms(domainListToContentList(page.getContent()));
        return assignedProgramPageContent;
    }
}
