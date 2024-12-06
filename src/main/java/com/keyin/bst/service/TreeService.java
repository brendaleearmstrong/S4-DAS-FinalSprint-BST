package com.keyin.bst.service;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.repository.TreeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TreeService {
    private final TreeRepository treeRepository;

    public TreeService(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    @Transactional
    public BinarySearchTree createTree(List<Integer> numbers) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insertNumbers(numbers);
        return treeRepository.save(tree);
    }

    @Transactional(readOnly = true)
    public List<BinarySearchTree> getAllTrees() {
        return treeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<BinarySearchTree> getTreeById(Long id) {
        return treeRepository.findById(id);
    }
}