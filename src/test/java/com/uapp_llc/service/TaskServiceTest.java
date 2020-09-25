package com.uapp_llc.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import com.uapp_llc.exception.NotFoundException;
import com.uapp_llc.model.Column;
import com.uapp_llc.model.Project;
import com.uapp_llc.model.Task;
import com.uapp_llc.test.comparator.ComparatorFactory;
import com.uapp_llc.test.model.ModelFactoryProducer;
import com.uapp_llc.test.model.column.ColumnType;
import com.uapp_llc.test.model.project.ProjectType;
import com.uapp_llc.test.model.task.TaskType;
import com.uapp_llc.test.stub.repository.TaskRepositoryStub;
import com.uapp_llc.test.stub.repository.identification.IdentificationContext;

// TODO: Test 'move' method

public class TaskServiceTest {

  private IdentificationContext<Task> identification;
  private TaskRepositoryStub repository;
  private TaskService service;

  @BeforeEach
  public void setUp() {
    identification = new IdentificationContext<>();
    repository = new TaskRepositoryStub(identification);
    service = new TaskServiceImpl(repository);
  }

  @Test
  public void create() {
    Project project = ModelFactoryProducer.getFactory(Project.class)
        .createModel(ProjectType.DEFAULT)
        .setId(1L);
    Column column = ModelFactoryProducer.getFactory(Column.class)
        .createModel(ColumnType.MONDAY)
        .setId(2L)
        .setProject(project);
    identification.setStrategy(e -> e.setId(1L));

    service.create(
        column,
        "Job",
        "Go to job"
    );

    Assertions
        .assertThat(repository.find(1L))
        .usingComparator(ComparatorFactory.getComparator(Task.class))
        .isEqualTo(new Task()
            .setId(1L)
            .setName("Job")
            .setDescription("Go to job")
            .setColumn(ModelFactoryProducer.getFactory(Column.class)
                .createModel(ColumnType.MONDAY)
                .setId(2L)
                .setProject(ModelFactoryProducer.getFactory(Project.class)
                    .createModel(ProjectType.DEFAULT)
                    .setId(1L))));
  }

  @Test
  public void update_whenNoEntityWithIdAndColumnId_expectException() {
    Assertions
        .assertThatThrownBy(() -> service.update(1L, 2L, "Meeting", "Meet John"))
        .isExactlyInstanceOf(NotFoundException.class)
        .hasFieldOrPropertyWithValue("getCodes",
            new Object[]{"notFound.task.byIdAndColumnId"})
        .hasFieldOrPropertyWithValue("getArguments", new Object[]{1L, 2L});
  }

  @Test
  public void update() {
    Project project = ModelFactoryProducer.getFactory(Project.class)
        .createModel(ProjectType.DEFAULT)
        .setId(1L);
    Column column = ModelFactoryProducer.getFactory(Column.class)
        .createModel(ColumnType.MONDAY)
        .setId(2L)
        .setProject(project);
    identification.setStrategy(e -> e.setId(1L));
    repository.save(ModelFactoryProducer.getFactory(Task.class)
        .createModel(TaskType.JOB)
        .setColumn(column));

    service.update(
        1L,
        2L,
        "Meeting",
        "Meet John"
    );

    Assertions
        .assertThat(repository.find(1L))
        .usingComparator(ComparatorFactory.getComparator(Task.class))
        .isEqualTo(ModelFactoryProducer.getFactory(Task.class)
            .createModel(TaskType.JOB)
            .setId(1L)
            .setName("Meeting")
            .setDescription("Meet John")
            .setColumn(ModelFactoryProducer.getFactory(Column.class)
                .createModel(ColumnType.MONDAY)
                .setId(2L)
                .setProject(ModelFactoryProducer.getFactory(Project.class)
                    .createModel(ProjectType.DEFAULT)
                    .setId(1L))));
  }

  @Test
  @Disabled
  public void move_whenNotEntityWithIdAndColumnId_expectException() {
  }

  @Test
  @Disabled
  public void move_whenIndexOutOfBounds_expectException() {
  }

  @Test
  @Disabled
  public void move() {
  }

  @Test
  public void find_whenNoEntityWithIdAndColumnId_expectException() {
    Assertions
        .assertThatThrownBy(() -> service.find(1L, 2L))
        .isExactlyInstanceOf(NotFoundException.class)
        .hasFieldOrPropertyWithValue("getCodes",
            new Object[]{"notFound.task.byIdAndColumnId"})
        .hasFieldOrPropertyWithValue("getArguments", new Object[]{1L, 2L});
  }

  @Test
  public void find() {
    Project project = ModelFactoryProducer.getFactory(Project.class)
        .createModel(ProjectType.DEFAULT)
        .setId(1L);
    Column column = ModelFactoryProducer.getFactory(Column.class)
        .createModel(ColumnType.MONDAY)
        .setId(2L)
        .setProject(project);
    identification.setStrategy(e -> e.setId(1L));
    repository.save(ModelFactoryProducer.getFactory(Task.class)
        .createModel(TaskType.JOB)
        .setColumn(column));

    Assertions
        .assertThat(service.find(1L, 2L))
        .usingComparator(ComparatorFactory.getComparator(Task.class))
        .isEqualTo(ModelFactoryProducer.getFactory(Task.class)
            .createModel(TaskType.JOB)
            .setId(1L)
            .setColumn(ModelFactoryProducer.getFactory(Column.class)
                .createModel(ColumnType.MONDAY)
                .setId(2L)
                .setProject(ModelFactoryProducer.getFactory(Project.class)
                    .createModel(ProjectType.DEFAULT)
                    .setId(1L))));
  }

  @Test
  public void findAll() {
    Project project = ModelFactoryProducer.getFactory(Project.class)
        .createModel(ProjectType.DEFAULT)
        .setId(1L);
    Column column = ModelFactoryProducer.getFactory(Column.class)
        .createModel(ColumnType.MONDAY)
        .setId(2L)
        .setProject(project);
    identification.setStrategy(e -> e.setId(1L));
    repository.save(ModelFactoryProducer.getFactory(Task.class)
        .createModel(TaskType.JOB)
        .setColumn(column));
    identification.setStrategy(e -> e.setId(2L));
    repository.save(ModelFactoryProducer.getFactory(Task.class)
        .createModel(TaskType.MEETING)
        .setColumn(column));

    Assertions
        .assertThat(service.findAll(2L, Pageable.unpaged()))
        .usingComparatorForType(ComparatorFactory.getComparator(Task.class), Task.class)
        .containsExactlyInAnyOrder(
            ModelFactoryProducer.getFactory(Task.class)
                .createModel(TaskType.JOB)
                .setId(1L)
                .setColumn(ModelFactoryProducer.getFactory(Column.class)
                    .createModel(ColumnType.MONDAY)
                    .setId(2L)
                    .setProject(ModelFactoryProducer.getFactory(Project.class)
                        .createModel(ProjectType.DEFAULT)
                        .setId(1L))),
            ModelFactoryProducer.getFactory(Task.class)
                .createModel(TaskType.MEETING)
                .setId(2L)
                .setColumn(ModelFactoryProducer.getFactory(Column.class)
                    .createModel(ColumnType.MONDAY)
                    .setId(2L)
                    .setProject(ModelFactoryProducer.getFactory(Project.class)
                        .createModel(ProjectType.DEFAULT)
                        .setId(1L)))
        );
  }

  @Test
  public void delete_whenNoEntityWithIdAndColumnId_expectException() {
    Assertions
        .assertThatThrownBy(() -> service.delete(1L, 2L))
        .isExactlyInstanceOf(NotFoundException.class)
        .hasFieldOrPropertyWithValue("getCodes",
            new Object[]{"notFound.task.byIdAndColumnId"})
        .hasFieldOrPropertyWithValue("getArguments", new Object[]{1L, 2L});
  }

  @Test
  public void delete() {
    Project project = ModelFactoryProducer.getFactory(Project.class)
        .createModel(ProjectType.DEFAULT)
        .setId(1L);
    Column column = ModelFactoryProducer.getFactory(Column.class)
        .createModel(ColumnType.MONDAY)
        .setId(2L)
        .setProject(project);
    identification.setStrategy(e -> e.setId(1L));
    repository.save(ModelFactoryProducer.getFactory(Task.class)
        .createModel(TaskType.JOB)
        .setColumn(column));

    service.delete(1L, 2L);

    Assertions
        .assertThat(repository.find(1L))
        .isNull();
  }

}