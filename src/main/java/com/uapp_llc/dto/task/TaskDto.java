package com.uapp_llc.dto.task;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

import com.uapp_llc.dto.column.ColumnDto;

@Getter
@Setter
public class TaskDto {

  private Long id;
  private ZonedDateTime createdAt;
  private String name;
  private String description;
  private ColumnDto column;

}