package com.keyin.bst.service;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.model.SearchResult;
import com.keyin.bst.repository.BinaryTreeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.ArrayList;

@Service
public class BinaryTreeService {
    private final BinaryTreeRepository binaryTreeRepository;

    public BinaryTreeService(BinaryTreeRepository binaryTreeRepository) {
        this.binaryTreeRepository = binaryTreeRepository;
    }

    @Transactional
    public BinarySearchTree createTree(String numbers) {
        if (numbers == null || numbers.trim().isEmpty()) {
            throw new IllegalArgumentException("Input numbers cannot be empty");
        }

        try {
            BinarySearchTree tree = new BinarySearchTree();
            tree.setInputNumbers(numbers);
            List<Integer> numbersList = tree.createListOfNumbersFromString(numbers);
            if (numbersList.isEmpty()) {
                throw new IllegalArgumentException("No valid numbers found in input");
            }
            tree.insertNumbers(numbersList);
            return binaryTreeRepository.save(tree);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error creating tree: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BinarySearchTree> getAllTrees() {
        return binaryTreeRepository.findAllByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public List<BinarySearchTree> getRecentTrees() {
        return binaryTreeRepository.findTop10ByOrderByIdDesc();
    }

    @Transactional(readOnly = true)
    public BinarySearchTree getTreeById(Long id) {
        return binaryTreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tree not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<SearchResult> searchNumber(int number) {
        try {
            List<BinarySearchTree> trees = getAllTrees();
            List<SearchResult> results = new ArrayList<>();

            for (BinarySearchTree tree : trees) {
                List<Integer> numbers = tree.createListOfNumbersFromString(tree.getInputNumbers());
                tree.insertNumbers(numbers);

                List<Integer> path = tree.searchPath(number);
                if (!path.isEmpty() && path.get(path.size() - 1) == number) {
                    results.add(new SearchResult(
                            tree.getId(),
                            tree.getInputNumbers(),
                            path,
                            tree.getTreeStructure(),
                            tree.getHeight(),
                            tree.getLeafNodes(),
                            tree.getBalanceFactor(),
                            tree.isBalanced(),
                            tree.getTotalNodes()
                    ));
                }
            }

            return results;
        } catch (Exception e) {
            throw new RuntimeException("Error searching for number: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteTree(Long id) {
        try {
            if (!binaryTreeRepository.existsById(id)) {
                throw new RuntimeException("Tree not found with id: " + id);
            }
            binaryTreeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tree: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public boolean validateTree(Long id) {
        try {
            BinarySearchTree tree = getTreeById(id);
            return tree.isBalanced();
        } catch (Exception e) {
            throw new RuntimeException("Error validating tree: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BinarySearchTree> findTreesByHeight(int height) {
        try {
            List<BinarySearchTree> allTrees = getAllTrees();
            List<BinarySearchTree> matchingTrees = new ArrayList<>();

            for (BinarySearchTree tree : allTrees) {
                if (tree.getHeight() == height) {
                    matchingTrees.add(tree);
                }
            }

            return matchingTrees;
        } catch (Exception e) {
            throw new RuntimeException("Error finding trees by height: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<BinarySearchTree> findBalancedTrees() {
        try {
            List<BinarySearchTree> allTrees = getAllTrees();
            List<BinarySearchTree> balancedTrees = new ArrayList<>();

            for (BinarySearchTree tree : allTrees) {
                if (tree.isBalanced()) {
                    balancedTrees.add(tree);
                }
            }

            return balancedTrees;
        } catch (Exception e) {
            throw new RuntimeException("Error finding balanced trees: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteAllTrees() {
        try {
            binaryTreeRepository.deleteAll();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all trees: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public long getTreeCount() {
        try {
            return binaryTreeRepository.count();
        } catch (Exception e) {
            throw new RuntimeException("Error getting tree count: " + e.getMessage());
        }
    }
}