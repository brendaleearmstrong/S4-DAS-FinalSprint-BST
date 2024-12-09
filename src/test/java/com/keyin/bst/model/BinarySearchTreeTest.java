package com.keyin.bst.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class BinarySearchTreeTest {
    private BinarySearchTree tree;

    @BeforeEach
    void setUp() {
        tree = new BinarySearchTree();
    }

    @Test
    void testCreateListOfNumbersFromString() {
        String input = "15, 50, 26, 16, 73, 0";
        List<Integer> numbers = tree.createListOfNumbersFromString(input);
        assertEquals(Arrays.asList(15, 50, 26, 16, 73, 0), numbers);
    }

    @Test
    void testCreateListOfNumbersFromEmptyString() {
        String input = "";
        List<Integer> numbers = tree.createListOfNumbersFromString(input);
        assertTrue(numbers.isEmpty());
    }

    @Test
    void testInsertNumbersAndMetrics() {
        List<Integer> numbers = Arrays.asList(50, 30, 70, 20, 40, 60, 80);
        tree.insertNumbers(numbers);

        assertEquals(3, tree.getHeight());
        assertEquals(4, tree.getLeafNodes());
        assertEquals(7, tree.getTotalNodes());
        assertTrue(tree.isBalanced());
    }

    @Test
    void testSearchPath() {
        List<Integer> numbers = Arrays.asList(50, 30, 70, 20, 40, 60, 80);
        tree.insertNumbers(numbers);

        List<Integer> path = tree.searchPath(40);
        assertEquals(Arrays.asList(50, 30, 40), path);
    }

    @Test
    void testSearchPathNotFound() {
        List<Integer> numbers = Arrays.asList(50, 30, 70, 20, 40, 60, 80);
        tree.insertNumbers(numbers);

        List<Integer> path = tree.searchPath(45);
        assertTrue(path.isEmpty() || path.get(path.size() - 1) != 45);
    }

    @Test
    void testTreeBalancing() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        tree.insertNumbers(numbers);
        assertTrue(tree.isBalanced());
    }

    @Test
    void testTreeStructureJson() {
        List<Integer> numbers = Arrays.asList(50, 30, 70);
        tree.insertNumbers(numbers);
        String structure = tree.getTreeStructure();
        assertTrue(structure.contains("\"value\":50"));
        assertTrue(structure.contains("\"value\":30"));
        assertTrue(structure.contains("\"value\":70"));
    }
}
