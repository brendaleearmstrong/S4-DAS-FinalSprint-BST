package com.keyin.bst.model;

public class Node {
    private int id;
    private int key;
    private String value;
    private Long parentId;

    public Node() {}

    public Node(int id, int key, String value, Long parentId) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
