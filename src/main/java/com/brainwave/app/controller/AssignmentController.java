package com.brainwave.app.controller;

import com.brainwave.app.model.Assignment;
import com.brainwave.app.model.Priority;
import com.brainwave.app.model.Status;
import com.brainwave.app.repository.AssignmentRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @GetMapping("/assignments/new")
    public String showAddForm(Model model) {
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());
        return "add-assignment";
    }

    @PostMapping("/assignments")
    public String saveAssignment(@Valid @ModelAttribute("assignment") Assignment assignment,
                                 BindingResult bindingResult,
                                 Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", Status.values());
            return "add-assignment";
        }

        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/assignments")
    public String viewAssignments(Model model) {
        model.addAttribute("assignments", assignmentRepository.findAll());
        return "assignments";
    }
}