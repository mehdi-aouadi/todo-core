package com.todo.mappers;

import com.todo.contents.ProgramTemplateContent;
import com.todo.contents.TaskContent;
import com.todo.model.ProgramTemplate;
import com.todo.model.Task;
import com.todo.model.User;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ProgramTemplateMapperTest {

  private final LocalDateTime wishDate = LocalDateTime.now().plusDays(2);

  @Test
  public void programTemplateToContentTest() {
    ProgramTemplate programTemplate
        = new ProgramTemplate(UUID.randomUUID(),
        Period.of(0, 0, 21),
        "ProgramTemplate For Test",
        "The very first program template",
        "What not to eat",
        Arrays.asList(
            new Task(UUID.randomUUID(), "No Gluten", new User(), new User(), "Remove Gluten", wishDate),
            new Task(UUID.randomUUID(), "No Sugar", new User(), new User(), "Remove Sugar", wishDate)
        )
    );

    ProgramTemplateContent programTemplateContent =
        ProgramTemplateMapper.INSTANCE.programTemplateToContent(programTemplate);

    assertEquals(programTemplate.getId(), programTemplateContent.getId());
    assertEquals(programTemplate.getPeriod(), programTemplateContent.getPeriod());
    assertEquals(programTemplate.getDescription(), programTemplateContent.getDescription());
    assertEquals(programTemplate.getName(), programTemplateContent.getName());
    assertEquals(programTemplate.getIntroduction(), programTemplateContent.getIntroduction());
    assertEquals(programTemplate.getTaskList().size(), 2);
    assertEquals(TaskMapper.INSTANCE.contentListToTaskList(programTemplateContent.getTaskContentList()),
        programTemplate.getTaskList());
  }

  @Test
  public void contentToProgramTemplate() {
    ProgramTemplateContent programTemplateContent = new ProgramTemplateContent(
        UUID.randomUUID(),
        Period.ofDays(21),
        "Program Name",
        "Program Introduction",
        "Program Description",
        Arrays.asList(
            new TaskContent(UUID.randomUUID(), "No Gluten", UUID.randomUUID(), "requester@email" +
                ".com", UUID.randomUUID(), "assignee@email.com", "Remove Gluten", wishDate),
            new TaskContent(UUID.randomUUID(), "No Sugar", UUID.randomUUID(), "requester@email" +
                ".com", UUID.randomUUID(), "assignee@email.com", "Remove Sugar", wishDate)
        )
    );

    ProgramTemplate programTemplate =
        ProgramTemplateMapper.INSTANCE.contentToProgramTemplate(programTemplateContent);

    assertEquals(programTemplate.getName(), programTemplateContent.getName());
    assertEquals(programTemplate.getDescription(), programTemplateContent.getDescription());
    assertEquals(programTemplate.getIntroduction(), programTemplateContent.getIntroduction());
    assertEquals(programTemplate.getPeriod(), programTemplateContent.getPeriod());
    assertEquals(programTemplate.getId(), programTemplateContent.getId());
    assertEquals(TaskMapper.INSTANCE.taskListToContentList(programTemplate.getTaskList()),
        programTemplateContent.getTaskContentList());
  }

}
