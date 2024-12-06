package com.keyin.bst.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BinarySearchTree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "binary_search_tree_input_numbers",
            joinColumns = @JoinColumn(name = "binary_search_tree_id"))
    private List<Integer> inputNumbers = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "binary_search_tree_nodes",
            joinColumns = @JoinColumn(name = "binary_search_tree_id"))
    private List<TreeNode> nodes = new ArrayList<>();

    @Column
    private int leafNodes;

    @Column
    private int duplicates;

    @Column
    private int height;

    @Embeddable
    public static class TreeNode {
        @Column(nullable = false)
        private Integer value;

        @Column(name = "left_index")
        private Integer leftIndex;

        @Column(name = "right_index")
        private Integer rightIndex;

        @Column(nullable = false)
        private int height = 1;

        @Column(nullable = false)
        private int count = 1;

        public TreeNode(Integer value, Integer leftIndex, Integer rightIndex) {
            this.value = value;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }

        public TreeNode() {
        }

        public Integer getValue() { return value; }
        public void setValue(Integer value) { this.value = value; }
        public Integer getLeftIndex() { return leftIndex; }
        public void setLeftIndex(Integer leftIndex) { this.leftIndex = leftIndex; }
        public Integer getRightIndex() { return rightIndex; }
        public void setRightIndex(Integer rightIndex) { this.rightIndex = rightIndex; }
        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
    }

    public void insertNumbers(List<Integer> numbers) {
        this.inputNumbers = new ArrayList<>(numbers);
        this.nodes = new ArrayList<>();
        for (Integer number : numbers) {
            insert(number);
        }
        calculateStats();
    }

    private void insert(Integer value) {
        if (nodes.isEmpty()) {
            nodes.add(new TreeNode(value, null, null));
            return;
        }
        insertAndBalance(0, value);
    }

    private int insertAndBalance(int nodeIndex, int value) {
        TreeNode node = nodes.get(nodeIndex);

        if (value == node.getValue()) {
            node.setCount(node.getCount() + 1);
            return nodeIndex;
        }

        if (value < node.getValue()) {
            if (node.getLeftIndex() == null) {
                node.setLeftIndex(nodes.size());
                nodes.add(new TreeNode(value, null, null));
                updateHeight(nodeIndex);
                return balance(nodeIndex);
            }
            node.setLeftIndex(insertAndBalance(node.getLeftIndex(), value));
        } else {
            if (node.getRightIndex() == null) {
                node.setRightIndex(nodes.size());
                nodes.add(new TreeNode(value, null, null));
                updateHeight(nodeIndex);
                return balance(nodeIndex);
            }
            node.setRightIndex(insertAndBalance(node.getRightIndex(), value));
        }

        updateHeight(nodeIndex);
        return balance(nodeIndex);
    }

    private void calculateStats() {
        if (nodes.isEmpty()) return;

        int leafCount = 0;
        int duplicateCount = 0;

        for (TreeNode node : nodes) {
            if (node.getLeftIndex() == null && node.getRightIndex() == null) {
                leafCount++;
            }
            if (node.getCount() > 1) {
                duplicateCount += node.getCount() - 1;
            }
        }

        this.leafNodes = leafCount;
        this.duplicates = duplicateCount;
        this.height = nodes.get(0).getHeight();
    }

    private void updateHeight(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int leftHeight = node.getLeftIndex() != null ? nodes.get(node.getLeftIndex()).getHeight() : 0;
        int rightHeight = node.getRightIndex() != null ? nodes.get(node.getRightIndex()).getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
    }

    private int getBalanceFactor(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int leftHeight = node.getLeftIndex() != null ? nodes.get(node.getLeftIndex()).getHeight() : 0;
        int rightHeight = node.getRightIndex() != null ? nodes.get(node.getRightIndex()).getHeight() : 0;
        return leftHeight - rightHeight;
    }

    private int balance(int nodeIndex) {
        updateHeight(nodeIndex);
        int balance = getBalanceFactor(nodeIndex);

        if (balance > 1) {
            int leftChildIndex = nodes.get(nodeIndex).getLeftIndex();
            if (getBalanceFactor(leftChildIndex) < 0) {
                nodes.get(nodeIndex).setLeftIndex(rotateLeft(leftChildIndex));
            }
            return rotateRight(nodeIndex);
        }

        if (balance < -1) {
            int rightChildIndex = nodes.get(nodeIndex).getRightIndex();
            if (getBalanceFactor(rightChildIndex) > 0) {
                nodes.get(nodeIndex).setRightIndex(rotateRight(rightChildIndex));
            }
            return rotateLeft(nodeIndex);
        }

        return nodeIndex;
    }

    private int rotateLeft(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int rightIndex = node.getRightIndex();
        TreeNode rightNode = nodes.get(rightIndex);

        node.setRightIndex(rightNode.getLeftIndex());
        rightNode.setLeftIndex(nodeIndex);

        updateHeight(nodeIndex);
        updateHeight(rightIndex);

        return rightIndex;
    }

    private int rotateRight(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int leftIndex = node.getLeftIndex();
        TreeNode leftNode = nodes.get(leftIndex);

        node.setLeftIndex(leftNode.getRightIndex());
        leftNode.setRightIndex(nodeIndex);

        updateHeight(nodeIndex);
        updateHeight(leftIndex);

        return leftIndex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Integer> getInputNumbers() { return inputNumbers; }
    public List<TreeNode> getNodes() { return nodes; }
    public int getLeafNodes() { return leafNodes; }
    public void setLeafNodes(int leafNodes) { this.leafNodes = leafNodes; }
    public int getDuplicates() { return duplicates; }
    public void setDuplicates(int duplicates) { this.duplicates = duplicates; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
}