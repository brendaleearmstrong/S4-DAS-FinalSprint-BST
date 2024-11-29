package com.keyin.bst.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void testNodeCreationWithConstructor() {
        Node node = new Node(1, 10, "Value1", 1L);
        assertEquals(1, node.getId());
        assertEquals(10, node.getKey());
        assertEquals("Value1", node.getValue());
        assertEquals(1L, node.getParentId());
    }

    @Test
    void testDefaultConstructor() {
        Node node = new Node();
        assertEquals(0, node.getId());
        assertEquals(0, node.getKey());
        assertNull(node.getValue());
        assertNull(node.getParentId());
    }

    @Test
    void testSettersAndGetters() {
        Node node = new Node();
        node.setId(2);
        node.setKey(20);
        node.setValue("TestValue");
        node.setParentId(50L);
        assertEquals(2, node.getId());
        assertEquals(20, node.getKey());
        assertEquals("TestValue", node.getValue());
        assertEquals(50L, node.getParentId());
    }
}
