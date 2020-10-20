package com.uapp_llc.repository.jpa

import com.uapp_llc.model.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface TaskJpaRepository : JpaRepository<Task, Long> {

  fun findByIdAndColumnId(id: Long, columnId: Long): Task?

  fun findAllByColumnId(columnId: Long, pageable: Pageable): Page<Task>

}
