package com.uapp_llc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uapp_llc.dto.task.ContentDto;
import com.uapp_llc.dto.task.MoveDto;
import com.uapp_llc.dto.task.TaskDto;
import com.uapp_llc.mapper.TaskMapper;
import com.uapp_llc.model.Column;
import com.uapp_llc.model.Task;
import com.uapp_llc.service.ColumnService;
import com.uapp_llc.service.TaskService;

@RestController
public class TaskController {

  private final ColumnService columnService;
  private final TaskService taskService;

  @Autowired
  public TaskController(ColumnService columnService, TaskService taskService) {
    this.columnService = columnService;
    this.taskService = taskService;
  }

  @GetMapping("/columns/{columnId}/tasks")
  public Page<TaskDto> getAll(@PathVariable Long columnId,
                              @PageableDefault(sort = "index") Pageable pageable) {
    Page<Task> tasks = taskService.findAll(columnId, pageable);
    return tasks.map(TaskMapper.INSTANCE::toDto);
  }

  @PostMapping("/columns/{columnId}/tasks")
  public TaskDto create(@PathVariable Long columnId,
                        @RequestBody ContentDto dto) {
    Column column = columnService.find(columnId);
    Task task = taskService.create(
        column,
        dto.getName(),
        dto.getDescription()
    );
    return TaskMapper.INSTANCE.toDto(task);
  }

  @GetMapping("/columns/{columnId}/tasks/{id}")
  public TaskDto get(@PathVariable Long columnId,
                     @PathVariable Long id) {
    Task task = taskService.find(id, columnId);
    return TaskMapper.INSTANCE.toDto(task);
  }

  @PatchMapping("/columns/{columnId}/tasks/{id}")
  public TaskDto update(@PathVariable Long columnId,
                        @PathVariable Long id,
                        @RequestBody ContentDto dto) {
    Task task = taskService.update(
        id,
        columnId,
        dto.getName(),
        dto.getDescription()
    );
    return TaskMapper.INSTANCE.toDto(task);
  }

  @PutMapping("/columns/{columnId}/tasks/{id}")
  public TaskDto move(@PathVariable Long columnId,
                      @PathVariable Long id,
                      @RequestBody MoveDto dto) {
    Long newColumnId = dto.getNewColumnId();
    Column newColumn = (newColumnId != null && !newColumnId.equals(columnId))
        ? columnService.find(newColumnId)
        : null;
    Task task = taskService.move(
        id,
        columnId,
        newColumn,
        dto.getNewIndex()
    );
    return TaskMapper.INSTANCE.toDto(task);
  }

  @DeleteMapping("/columns/{columnId}/tasks/{id}")
  public void delete(@PathVariable Long columnId,
                     @PathVariable Long id) {
    taskService.delete(id, columnId);
  }

}
