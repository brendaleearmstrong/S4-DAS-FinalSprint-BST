package com.keyin.bst.repository;

import com.keyin.bst.model.BinarySearchTree;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TreeRepositoryTest {

    @Test
    public void testRepositorySaveAndRetrieve() {
        BinarySearchTree parent = new BinarySearchTree(1L, "Parent Node", null);
        BinarySearchTree child = new BinarySearchTree(2L, "Child Node", 1L);

        assertNotNull(parent);
        assertNotNull(child);

        assertEquals(Long.valueOf(1), parent.getId());
        assertEquals("Parent Node", parent.getName());
        assertEquals(null, parent.getParentId());

        assertEquals(Long.valueOf(2), child.getId());
        assertEquals("Child Node", child.getName());
        assertEquals(Long.valueOf(1), child.getParentId());
    }
}
