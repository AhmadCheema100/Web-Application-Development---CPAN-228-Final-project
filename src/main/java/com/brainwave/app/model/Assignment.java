package com.brainwave.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Assignment title is required.")
    @Size(min = 3, max = 100, message = "Assignment title must be between 3 and 100 characters.")
    private String title;

    @NotBlank(message = "Course name is required.")
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters.")
    private String courseName;

    @NotNull(message = "Due date is required.")
    @FutureOrPresent(message = "Due date must be today or in the future.")
    private LocalDate dueDate;

    @NotNull(message = "Priority is required.")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull(message = "Status is required.")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Size(max = 300, message = "Description must be 300 characters or less.")
    private String description;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Assignment() {
    }

    public Assignment(String title, String courseName, LocalDate dueDate, Priority priority, Status status, String description) {
        this.title = title;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.description = description;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}