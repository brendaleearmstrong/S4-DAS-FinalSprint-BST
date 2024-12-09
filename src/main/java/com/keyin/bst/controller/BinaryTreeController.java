package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.model.SearchResult;
import com.keyin.bst.service.BinaryTreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BinaryTreeController {
    private final BinaryTreeService binaryTreeService;

    public BinaryTreeController(BinaryTreeService binaryTreeService) {
        this.binaryTreeService = binaryTreeService;
    }

    @GetMapping
    public String showHome() {
        return "home";
    }

    @GetMapping("/enter-numbers")
    public String showEnterNumbers() {
        return "enter-numbers";
    }

    @PostMapping("/process-numbers")
    @ResponseBody
    public ResponseEntity<?> processNumbers(@RequestParam("numbers") String numbers) {
        try {
            if (numbers == null || numbers.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Numbers cannot be empty"));
            }

            BinarySearchTree tree = binaryTreeService.createTree(numbers);
            Map<String, Object> response = new HashMap<>();
            response.put("treeStructure", tree.getTreeStructure());
            response.put("height", tree.getHeight());
            response.put("totalNodes", tree.getTotalNodes());
            response.put("leafNodes", tree.getLeafNodes());
            response.put("balanceFactor", tree.getBalanceFactor());
            response.put("isBalanced", tree.isBalanced());
            response.put("id", tree.getId());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error processing tree: " + e.getMessage()));
        }
    }

    @GetMapping("/previous-trees")
    public String showPreviousTrees(Model model) {
        try {
            List<BinarySearchTree> trees = binaryTreeService.getRecentTrees();
            model.addAttribute("trees", trees);
            return "previous-trees";
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving trees: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/api/trees")
    @ResponseBody
    public ResponseEntity<?> getAllTrees() {
        try {
            List<BinarySearchTree> trees = binaryTreeService.getAllTrees();
            return ResponseEntity.ok(trees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error retrieving trees: " + e.getMessage()));
        }
    }

    @GetMapping("/search-number")
    public String showSearchNumber() {
        return "search-number";
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<?> searchNumber(@RequestParam("number") String numberStr) {
        try {
            int number = Integer.parseInt(numberStr.trim());
            List<SearchResult> results = binaryTreeService.searchNumber(number);

            if (results.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "found", false,
                        "message", "Number not found in any trees"
                ));
            }
            return ResponseEntity.ok(Map.of(
                    "found", true,
                    "results", results
            ));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid number format"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error during search: " + e.getMessage()));
        }
    }

    @GetMapping("/tree/{id}/metrics")
    @ResponseBody
    public ResponseEntity<?> getTreeMetrics(@PathVariable Long id) {
        try {
            BinarySearchTree tree = binaryTreeService.getTreeById(id);
            if (tree == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("height", tree.getHeight());
            metrics.put("balanceFactor", tree.getBalanceFactor());
            metrics.put("totalNodes", tree.getTotalNodes());
            metrics.put("leafNodes", tree.getLeafNodes());
            metrics.put("isBalanced", tree.isBalanced());
            metrics.put("treeStructure", tree.getTreeStructure());
            metrics.put("inputNumbers", tree.getInputNumbers());
            return ResponseEntity.ok(metrics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Error retrieving tree metrics: " + e.getMessage()));
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}