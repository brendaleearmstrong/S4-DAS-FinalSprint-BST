package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TreeController {
    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/enter-numbers")
    public String showInputForm() {
        return "enter-numbers";
    }

    @PostMapping("/process-numbers")
    @ResponseBody
    public ResponseEntity<BinarySearchTree> processNumbers(@RequestBody List<Integer> numbers) {
        return ResponseEntity.ok(treeService.createTree(numbers));
    }

    @GetMapping("/previous-trees")
    public String showPreviousTrees(Model model) {
        model.addAttribute("trees", treeService.getAllTrees());
        return "previous-trees";
    }

    @GetMapping("/api/trees/{id}")
    @ResponseBody
    public ResponseEntity<BinarySearchTree> getTree(@PathVariable Long id) {
        return treeService.getTreeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/trees/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }
}
