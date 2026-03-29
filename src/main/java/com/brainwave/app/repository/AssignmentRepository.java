package com.brainwave.app.repository;

import com.brainwave.app.model.Assignment;
import com.brainwave.app.model.Priority;
import com.brainwave.app.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    
    Page<Assignment> findByStatusAndPriority(Status status, Priority priority, Pageable pageable);
    Page<Assignment> findByStatus(Status status, Pageable pageable);
    Page<Assignment> findByPriority(Priority priority, Pageable pageable);
}