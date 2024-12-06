package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")
public class TreeController {
    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public String showHome() {
        return "home";
    }

    @GetMapping("/enter-numbers")
    public String showInputForm() {
        return "enter-numbers";
    }

    @PostMapping("/api/process-numbers")
    @ResponseBody
    public ResponseEntity<?> processNumbers(@RequestBody List<Integer> numbers) {
        try {
            BinarySearchTree tree = treeService.createTree(numbers);
            return ResponseEntity.ok(tree);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/previous-trees")
    public String showPreviousTrees(Model model) {
        try {
            List<BinarySearchTree> trees = treeService.getAllTrees();
            model.addAttribute("trees", trees);
            return "previous-trees";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading trees: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/api/trees")
    @ResponseBody
    public ResponseEntity<List<BinarySearchTree>> getAllTrees() {
        try {
            return ResponseEntity.ok(treeService.getAllTrees());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}