package com.keyin.bst.model;

import java.util.List;

public class SearchResult {
    private Long treeId;
    private String inputNumbers;
    private List<Integer> searchPath;
    private String treeStructure;
    private int height;
    private int leafNodes;
    private int balanceFactor;
    private boolean isBalanced;
    private int totalNodes;

    public SearchResult(Long treeId, String inputNumbers, List<Integer> searchPath, String treeStructure) {
        this.treeId = treeId;
        this.inputNumbers = inputNumbers;
        this.searchPath = searchPath;
        this.treeStructure = treeStructure;
    }

    public SearchResult(Long treeId, String inputNumbers, List<Integer> searchPath, String treeStructure,
                        int height, int leafNodes, int balanceFactor, boolean isBalanced, int totalNodes) {
        this.treeId = treeId;
        this.inputNumbers = inputNumbers;
        this.searchPath = searchPath;
        this.treeStructure = treeStructure;
        this.height = height;
        this.leafNodes = leafNodes;
        this.balanceFactor = balanceFactor;
        this.isBalanced = isBalanced;
        this.totalNodes = totalNodes;
    }

    public Long getTreeId() { return treeId; }
    public void setTreeId(Long treeId) { this.treeId = treeId; }

    public String getInputNumbers() { return inputNumbers; }
    public void setInputNumbers(String inputNumbers) { this.inputNumbers = inputNumbers; }

    public List<Integer> getSearchPath() { return searchPath; }
    public void setSearchPath(List<Integer> searchPath) { this.searchPath = searchPath; }

    public String getTreeStructure() { return treeStructure; }
    public void setTreeStructure(String treeStructure) { this.treeStructure = treeStructure; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getLeafNodes() { return leafNodes; }
    public void setLeafNodes(int leafNodes) { this.leafNodes = leafNodes; }

    public int getBalanceFactor() { return balanceFactor; }
    public void setBalanceFactor(int balanceFactor) { this.balanceFactor = balanceFactor; }

    public boolean isBalanced() { return isBalanced; }
    public void setBalanced(boolean balanced) { isBalanced = balanced; }

    public int getTotalNodes() { return totalNodes; }
    public void setTotalNodes(int totalNodes) { this.totalNodes = totalNodes; }
}