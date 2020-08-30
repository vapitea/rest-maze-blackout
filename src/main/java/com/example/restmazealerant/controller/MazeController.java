package com.example.restmazealerant.controller;

import com.example.restmazealerant.service.MazeSolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MazeController {
    private final MazeSolver mazeSolver;

    public MazeController(MazeSolver mazeSolver) {
        this.mazeSolver = mazeSolver;
    }

    @GetMapping("/mazeSolution")
    public String getMazeSolution(Model model) {
        model.addAttribute("mazeSolution", mazeSolver.startSolving());
        return "maze-solution";
    }

    @GetMapping("")
    public String getIndexPage() {
        return "redirect:/mazeSolution";
    }
}
