package com.brainwave.app.controller;

import com.brainwave.app.model.Assignment;
import com.brainwave.app.model.Priority;
import com.brainwave.app.model.Status;
import com.brainwave.app.repository.AssignmentRepository;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;

    public AssignmentController(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isTeacher(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
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
            Model model,
            Authentication authentication) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", Status.values());
            return "add-assignment";
        }

        assignment.setOwnerUsername(authentication.getName());
        assignmentRepository.save(assignment);

        return "redirect:/assignments";
    }

    @GetMapping("/assignments")
    public String viewAssignments(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            Authentication authentication) {

        Page<Assignment> assignmentPage;
        PageRequest pageable = PageRequest.of(page, 10, Sort.by(sortBy));

        if (status != null && priority != null) {
            assignmentPage = assignmentRepository.findByStatusAndPriority(status, priority, pageable);
        } else if (status != null) {
            assignmentPage = assignmentRepository.findByStatus(status, pageable);
        } else if (priority != null) {
            assignmentPage = assignmentRepository.findByPriority(priority, pageable);
        } else {
            assignmentPage = assignmentRepository.findAll(pageable);
        }

        boolean isAdmin = isAdmin(authentication);
        boolean isTeacher = isTeacher(authentication);

        model.addAttribute("assignments", assignmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("currentUsername", authentication.getName());
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isTeacher", isTeacher);

        return "assignments";
    }

    @GetMapping("/assignments/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return "redirect:/assignments";
        }

        boolean isAdmin = isAdmin(authentication);
        boolean isTeacher = isTeacher(authentication);
        boolean isOwner = assignment.getOwnerUsername().equals(authentication.getName());

        if (!(isAdmin || (isTeacher && isOwner))) {
            return "redirect:/assignments";
        }

        model.addAttribute("assignment", assignment);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());

        return "add-assignment";
    }

    @PostMapping("/assignments/update/{id}")
    public String updateAssignment(
            @PathVariable Long id,
            @Valid @ModelAttribute("assignment") Assignment assignment,
            BindingResult bindingResult,
            Model model,
            Authentication authentication) {

        Assignment existingAssignment = assignmentRepository.findById(id).orElse(null);

        if (existingAssignment == null) {
            return "redirect:/assignments";
        }

        boolean isAdmin = isAdmin(authentication);
        boolean isTeacher = isTeacher(authentication);
        boolean isOwner = existingAssignment.getOwnerUsername().equals(authentication.getName());

        if (!(isAdmin || (isTeacher && isOwner))) {
            return "redirect:/assignments";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", Status.values());
            return "add-assignment";
        }

        assignment.setId(id);
        assignment.setOwnerUsername(existingAssignment.getOwnerUsername());
        assignment.setCreatedAt(existingAssignment.getCreatedAt());

        assignmentRepository.save(assignment);
        return "redirect:/assignments";
    }

    @GetMapping("/assignments/delete/{id}")
    public String deleteAssignment(@PathVariable Long id, Authentication authentication) {
        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        if (assignment == null) {
            return "redirect:/assignments";
        }

        boolean isAdmin = isAdmin(authentication);
        boolean isTeacher = isTeacher(authentication);
        boolean isOwner = assignment.getOwnerUsername().equals(authentication.getName());

        if (isAdmin || (isTeacher && isOwner)) {
            assignmentRepository.delete(assignment);
        }

        return "redirect:/assignments";
    }
}