package com.brainwave.app.repository;

import com.brainwave.app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerUsername(String ownerUsername);
}