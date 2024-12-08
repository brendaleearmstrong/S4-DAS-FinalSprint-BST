package com.keyin.bst.model;

import org.springframework.stereotype.Component;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "binary_trees")
public class BinarySearchTree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "input_numbers", length = 1000)
    private String inputNumbers;

    @Column(name = "tree_structure", columnDefinition = "TEXT")
    private String treeStructure;

    @Column(name = "tree_height")
    private int height;

    @Column(name = "leaf_node_count")
    private int leafNodes;

    @Column(name = "is_balanced")
    private boolean isBalanced;

    @Transient
    private int balanceFactor;

    @Transient
    private int totalNodes;

    @Transient
    private TreeNode root;

    public List<Integer> createListOfNumbersFromString(String numberStr) {
        if (numberStr == null || numberStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(numberStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
    }

    public void insertNumbers(List<Integer> numbers) {
        root = null;
        if (numbers != null && !numbers.isEmpty()) {
            for (Integer number : numbers) {
                root = insertNumberRecursive(root, number);
            }
        }
        updateTreeMetrics();
    }

    private TreeNode insertNumberRecursive(TreeNode node, int value) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value < node.getData()) {
            node.setLeft(insertNumberRecursive(node.getLeft(), value));
        } else if (value > node.getData()) {
            node.setRight(insertNumberRecursive(node.getRight(), value));
        }

        node.updateMetrics();
        return balanceNode(node);
    }

    private TreeNode balanceNode(TreeNode node) {
        int balanceFactor = node.getBalanceFactor();

        if (balanceFactor > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            return rotateLeft(node);
        }

        return node;
    }

    private TreeNode rotateLeft(TreeNode y) {
        if (y == null || y.getRight() == null) {
            return y;
        }

        TreeNode x = y.getRight();
        TreeNode T2 = x.getLeft();

        x.setLeft(y);
        y.setRight(T2);

        y.updateMetrics();
        x.updateMetrics();

        return x;
    }

    private TreeNode rotateRight(TreeNode x) {
        if (x == null || x.getLeft() == null) {
            return x;
        }

        TreeNode y = x.getLeft();
        TreeNode T2 = y.getRight();

        y.setRight(x);
        x.setLeft(T2);

        x.updateMetrics();
        y.updateMetrics();

        return y;
    }

    private void updateTreeMetrics() {
        if (root == null) {
            resetMetrics();
            return;
        }

        root.updateMetrics();
        height = root.getHeight();
        balanceFactor = root.getBalanceFactor();
        totalNodes = root.getSize();
        leafNodes = countLeafNodes(root);
        isBalanced = Math.abs(balanceFactor) <= 1;
        treeStructure = generateTreeStructure(root);
    }

    private void resetMetrics() {
        height = 0;
        leafNodes = 0;
        balanceFactor = 0;
        totalNodes = 0;
        isBalanced = true;
        treeStructure = "null";
    }

    private int countLeafNodes(TreeNode node) {
        if (node == null) return 0;
        if (node.isLeaf()) return 1;
        return countLeafNodes(node.getLeft()) + countLeafNodes(node.getRight());
    }

    private String generateTreeStructure(TreeNode node) {
        if (node == null) return "null";
        return String.format(
                "{\"value\":%d,\"height\":%d,\"balanceFactor\":%d,\"left\":%s,\"right\":%s}",
                node.getData(),
                node.getHeight(),
                node.getBalanceFactor(),
                generateTreeStructure(node.getLeft()),
                generateTreeStructure(node.getRight())
        );
    }

    public List<Integer> searchPath(int target) {
        List<Integer> path = new ArrayList<>();
        searchPathHelper(root, target, path);
        return path;
    }

    private boolean searchPathHelper(TreeNode node, int target, List<Integer> path) {
        if (node == null) return false;

        path.add(node.getData());

        if (node.getData() == target) return true;

        if (target < node.getData() && searchPathHelper(node.getLeft(), target, path)) return true;
        if (target > node.getData() && searchPathHelper(node.getRight(), target, path)) return true;

        path.remove(path.size() - 1);
        return false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getInputNumbers() { return inputNumbers; }
    public void setInputNumbers(String inputNumbers) { this.inputNumbers = inputNumbers; }

    public String getTreeStructure() { return treeStructure; }
    public void setTreeStructure(String treeStructure) { this.treeStructure = treeStructure; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getLeafNodes() { return leafNodes; }
    public void setLeafNodes(int leafNodes) { this.leafNodes = leafNodes; }

    public int getBalanceFactor() { return balanceFactor; }
    public void setBalanceFactor(int balanceFactor) { this.balanceFactor = balanceFactor; }

    public int getTotalNodes() { return totalNodes; }
    public void setTotalNodes(int totalNodes) { this.totalNodes = totalNodes; }

    public boolean isBalanced() { return isBalanced; }
    public void setBalanced(boolean balanced) { isBalanced = balanced; }

    public TreeNode getRoot() { return root; }
}