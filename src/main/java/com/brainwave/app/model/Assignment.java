package com.brainwave.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}