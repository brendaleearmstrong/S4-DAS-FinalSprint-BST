package com.keyin.bst.service;

import com.keyin.bst.model.BinarySearchTree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeServiceTest {

    @Test
    public void testServiceLayer() {
        BinarySearchTree node = new BinarySearchTree(1L, "Node", null);
        assertEquals(Long.valueOf(1), node.getId());
        assertEquals("Node", node.getName());
    }
}
