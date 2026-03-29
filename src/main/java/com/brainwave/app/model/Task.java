package com.brainwave.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title is required.")
    @Size(min = 3, max = 100, message = "Task title must be between 3 and 100 characters.")
    private String title;

    @FutureOrPresent(message = "Due date must be today or in the future.")
    private LocalDate dueDate;

    @Size(max = 300, message = "Description must be 300 characters or less.")
    private String description;

    private String ownerUsername;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Task() {
    }

    public Task(String title, LocalDate dueDate, String description , String ownerUsername) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.ownerUsername = ownerUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }
}