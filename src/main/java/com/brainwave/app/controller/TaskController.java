package com.brainwave.app.controller;

import com.brainwave.app.model.Task;
import com.brainwave.app.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "add-task";
    }

    @PostMapping("/tasks")
    public String saveTask(
            @Valid @ModelAttribute("task") Task task,
            BindingResult bindingResult,
            Model model,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "add-task";
        }

        String username = authentication.getName();
        task.setOwnerUsername(username);

        taskRepository.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String viewTasks(Model model, Authentication authentication) {
        if (isAdmin(authentication)) {
            model.addAttribute("tasks", taskRepository.findAll());
        } else {
            String username = authentication.getName();
            model.addAttribute("tasks", taskRepository.findByOwnerUsername(username));
        }
        return "tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id, Authentication authentication) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task != null && (isAdmin(authentication) || task.getOwnerUsername().equals(authentication.getName()))) {
            taskRepository.delete(task);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model, Authentication authentication) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null || (!isAdmin(authentication) && !task.getOwnerUsername().equals(authentication.getName()))) {
            return "redirect:/tasks";
        }

        model.addAttribute("task", task);
        return "add-task";
    }

    @PostMapping("/tasks/update/{id}")
    public String updateTask(@PathVariable Long id,
                             @Valid @ModelAttribute("task") Task task,
                             BindingResult bindingResult,
                             Authentication authentication,
                             Model model) {

        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask == null || (!isAdmin(authentication) && !existingTask.getOwnerUsername().equals(authentication.getName()))) {
            return "redirect:/tasks";
        }

        if (bindingResult.hasErrors()) {
            return "add-task";
        }

        task.setId(id);
        task.setOwnerUsername(existingTask.getOwnerUsername());
        task.setCreatedAt(existingTask.getCreatedAt());

        taskRepository.save(task);
        return "redirect:/tasks";
    }
}