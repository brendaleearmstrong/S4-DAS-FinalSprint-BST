package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;

    public TreeController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public ResponseEntity<List<BinarySearchTree>> getAllNodes() {
        return ResponseEntity.ok(treeService.getAllNodes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BinarySearchTree> getNodeById(@PathVariable Long id) {
        return treeService.getNodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BinarySearchTree> createNode(@RequestBody BinarySearchTree node) {
        return ResponseEntity.ok(treeService.createNode(node));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BinarySearchTree> updateNode(@PathVariable Long id, @RequestBody BinarySearchTree updatedNode) {
        return ResponseEntity.ok(treeService.updateNode(id, updatedNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNode(@PathVariable Long id) {
        treeService.deleteNode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/children/{parentId}")
    public ResponseEntity<List<BinarySearchTree>> getChildren(@PathVariable Long parentId) {
        return ResponseEntity.ok(treeService.getChildren(parentId));
    }
}
