package com.keyin.bst.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BinarySearchTreeTest {

    @Test
    public void testBinarySearchTreeConstructor() {
        BinarySearchTree tree1 = new BinarySearchTree(1L, "Root", null);
        BinarySearchTree tree2 = new BinarySearchTree(2L, "Left Child", 1L);
        BinarySearchTree tree3 = new BinarySearchTree(3L, "Right Child", 1L);

        assertNotNull(tree1);
        assertNotNull(tree2);
        assertNotNull(tree3);

        assertEquals(Long.valueOf(1), tree1.getId());
        assertEquals("Root", tree1.getName());
        assertEquals(null, tree1.getParentId());

        assertEquals(Long.valueOf(2), tree2.getId());
        assertEquals("Left Child", tree2.getName());
        assertEquals(Long.valueOf(1), tree2.getParentId());

        assertEquals(Long.valueOf(3), tree3.getId());
        assertEquals("Right Child", tree3.getName());
        assertEquals(Long.valueOf(1), tree3.getParentId());
    }
}
