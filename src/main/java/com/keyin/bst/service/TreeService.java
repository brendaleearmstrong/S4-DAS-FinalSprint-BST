package com.keyin.bst.service;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.repository.TreeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TreeService {

    private final TreeRepository treeRepository;

    public TreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    public List<BinarySearchTree> getAllNodes() {
        return treeRepository.findAll();
    }

    public Optional<BinarySearchTree> getNodeById(Long id) {
        return treeRepository.findById(id);
    }

    public BinarySearchTree createNode(BinarySearchTree node) {
        return treeRepository.save(node);
    }

    public BinarySearchTree updateNode(Long id, BinarySearchTree updatedNode) {
        return treeRepository.findById(id).map(existingNode -> {
            existingNode.setName(updatedNode.getName());
            existingNode.setParentId(updatedNode.getParentId());
            return treeRepository.save(existingNode);
        }).orElseThrow(() -> new RuntimeException("Node not found with id: " + id));
    }

    public void deleteNode(Long id) {
        treeRepository.deleteById(id);
    }

    public List<BinarySearchTree> getChildren(Long parentId) {
        return treeRepository.findByParentId(parentId);
    }
}
