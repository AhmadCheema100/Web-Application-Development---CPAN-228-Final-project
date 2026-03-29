package com.brainwave.app.controller;

import com.brainwave.app.repository.AssignmentRepository;
import com.brainwave.app.repository.TaskRepository;
import com.brainwave.app.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final TaskRepository taskRepository;

    public AdminController(UserRepository userRepository, 
                           AssignmentRepository assignmentRepository, 
                           TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.assignmentRepository = assignmentRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Fetch all users and total counts for the dashboard
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("totalAssignments", assignmentRepository.count());
        model.addAttribute("totalTasks", taskRepository.count());
        
        return "admin-dashboard";
    }
}
