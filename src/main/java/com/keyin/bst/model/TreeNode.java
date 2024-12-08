package com.keyin.bst.model;

public class TreeNode {
    private TreeNode left;
    private TreeNode right;
    private int data;
    private int height;
    private int balanceFactor;
    private int size;
    private boolean isLeaf;

    public TreeNode(int data) {
        this.data = data;
        this.height = 1;
        this.balanceFactor = 0;
        this.size = 1;
        this.isLeaf = true;
        this.left = null;
        this.right = null;
    }

    public TreeNode getLeft() { return left; }
    public void setLeft(TreeNode left) {
        this.left = left;
        updateMetrics();
    }

    public TreeNode getRight() { return right; }
    public void setRight(TreeNode right) {
        this.right = right;
        updateMetrics();
    }

    public int getData() { return data; }
    public void setData(int data) { this.data = data; }

    public int getHeight() { return height; }
    public int getBalanceFactor() { return balanceFactor; }
    public int getSize() { return size; }
    public boolean isLeaf() { return isLeaf; }

    public void updateMetrics() {
        int leftHeight = (left == null) ? 0 : left.getHeight();
        int rightHeight = (right == null) ? 0 : right.getHeight();

        this.height = Math.max(leftHeight, rightHeight) + 1;
        this.balanceFactor = leftHeight - rightHeight;

        int leftSize = (left == null) ? 0 : left.getSize();
        int rightSize = (right == null) ? 0 : right.getSize();
        this.size = leftSize + rightSize + 1;

        this.isLeaf = (left == null && right == null);
    }
}