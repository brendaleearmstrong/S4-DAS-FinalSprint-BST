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
    private List<Integer> inputNumbers = new ArrayList<>();

    @ElementCollection
    private List<TreeNode> nodes = new ArrayList<>();

    @Embeddable
    public static class TreeNode {
        private Integer value;
        private Integer leftIndex;
        private Integer rightIndex;
        private int height;

        public TreeNode(Integer value, Integer leftIndex, Integer rightIndex) {
            this.value = value;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.height = 1;
        }

        public TreeNode() {
            this.height = 1;
        }

        public Integer getValue() { return value; }
        public void setValue(Integer value) { this.value = value; }
        public Integer getLeftIndex() { return leftIndex; }
        public void setLeftIndex(Integer leftIndex) { this.leftIndex = leftIndex; }
        public Integer getRightIndex() { return rightIndex; }
        public void setRightIndex(Integer rightIndex) { this.rightIndex = rightIndex; }
        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }
    }

    public void insertNumbers(List<Integer> numbers) {
        this.inputNumbers = new ArrayList<>(numbers);
        this.nodes = new ArrayList<>();
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        sortedNumbers.sort(null);
        insertSorted(sortedNumbers, 0, sortedNumbers.size() - 1);
    }

    private void insertSorted(List<Integer> sortedNumbers, int start, int end) {
        if (start > end) return;
        int mid = (start + end) / 2;
        insert(sortedNumbers.get(mid));
        insertSorted(sortedNumbers, start, mid - 1);
        insertSorted(sortedNumbers, mid + 1, end);
    }

    private void insert(Integer value) {
        if (nodes.isEmpty()) {
            nodes.add(new TreeNode(value, null, null));
            return;
        }
        insertAndBalance(0, value);
    }

    private int insertAndBalance(int nodeIndex, int value) {
        if (nodeIndex >= nodes.size()) {
            nodes.add(new TreeNode(value, null, null));
            return nodes.size() - 1;
        }

        TreeNode node = nodes.get(nodeIndex);
        if (value < node.getValue()) {
            if (node.getLeftIndex() == null) {
                node.setLeftIndex(nodes.size());
                nodes.add(new TreeNode(value, null, null));
            } else {
                node.setLeftIndex(insertAndBalance(node.getLeftIndex(), value));
            }
        } else {
            if (node.getRightIndex() == null) {
                node.setRightIndex(nodes.size());
                nodes.add(new TreeNode(value, null, null));
            } else {
                node.setRightIndex(insertAndBalance(node.getRightIndex(), value));
            }
        }

        updateHeight(nodeIndex);
        return balance(nodeIndex);
    }

    private void updateHeight(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int leftHeight = node.getLeftIndex() != null ? nodes.get(node.getLeftIndex()).getHeight() : 0;
        int rightHeight = node.getRightIndex() != null ? nodes.get(node.getRightIndex()).getHeight() : 0;
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
    }

    private int getBalance(int nodeIndex) {
        TreeNode node = nodes.get(nodeIndex);
        int leftHeight = node.getLeftIndex() != null ? nodes.get(node.getLeftIndex()).getHeight() : 0;
        int rightHeight = node.getRightIndex() != null ? nodes.get(node.getRightIndex()).getHeight() : 0;
        return leftHeight - rightHeight;
    }

    private int balance(int nodeIndex) {
        updateHeight(nodeIndex);
        int balance = getBalance(nodeIndex);

        if (balance > 1) {
            TreeNode node = nodes.get(nodeIndex);
            int leftBalance = getBalance(node.getLeftIndex());
            if (leftBalance < 0) {
                node.setLeftIndex(rotateLeft(node.getLeftIndex()));
            }
            return rotateRight(nodeIndex);
        }

        if (balance < -1) {
            TreeNode node = nodes.get(nodeIndex);
            int rightBalance = getBalance(node.getRightIndex());
            if (rightBalance > 0) {
                node.setRightIndex(rotateRight(node.getRightIndex()));
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
}
