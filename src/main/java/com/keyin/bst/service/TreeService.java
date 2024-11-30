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

    public BinarySearchTree createTree(List<Integer> numbers) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insertNumbers(numbers);
        return treeRepository.save(tree);
    }

    public List<BinarySearchTree> getAllTrees() {
        return treeRepository.findAll();
    }

    public Optional<BinarySearchTree> getTreeById(Long id) {
        return treeRepository.findById(id);
    }

    public void deleteTree(Long id) {
        treeRepository.deleteById(id);
    }
}
