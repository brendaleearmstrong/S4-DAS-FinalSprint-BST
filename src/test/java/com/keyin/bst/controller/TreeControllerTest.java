package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeControllerTest {

    @Test
    public void testAddNode() {
        BinarySearchTree root = new BinarySearchTree(1L, "Root Node", null);
        assertEquals(Long.valueOf(1), root.getId());
        assertEquals("Root Node", root.getName());
        assertEquals(null, root.getParentId());
    }

    @Test
    public void testGetChildNodes() {
        BinarySearchTree parent = new BinarySearchTree(1L, "Parent Node", null);
        BinarySearchTree child = new BinarySearchTree(2L, "Child Node", 1L);

        assertEquals(Long.valueOf(1), child.getParentId());
        assertEquals("Child Node", child.getName());
    }
}
