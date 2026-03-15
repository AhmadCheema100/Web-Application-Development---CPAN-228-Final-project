package com.brainwave.app.controller;

import com.brainwave.app.model.Assignment;
import com.brainwave.app.model.Priority;
import com.brainwave.app.model.Status;
import com.brainwave.app.repository.AssignmentRepository;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String saveAssignment(
            @Valid @ModelAttribute("assignment") Assignment assignment,
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
    public String viewAssignments(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sortBy));
        Page<Assignment> assignmentPage;

        if (status != null && priority != null) {
            assignmentPage = assignmentRepository.findByStatusAndPriority(status, priority, pageable);
        } else if (status != null) {
            assignmentPage = assignmentRepository.findByStatus(status, pageable);
        } else if (priority != null) {
            assignmentPage = assignmentRepository.findByPriority(priority, pageable);
        } else {
            assignmentPage = assignmentRepository.findAll(pageable);
        }

        model.addAttribute("assignments", assignmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());
        
        return "assignments";
    }
}