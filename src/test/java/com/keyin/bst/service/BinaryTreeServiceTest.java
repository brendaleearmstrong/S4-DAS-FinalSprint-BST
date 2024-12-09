package com.keyin.bst.service;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.model.SearchResult;
import com.keyin.bst.repository.BinaryTreeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class BinaryTreeServiceTest {
    private BinaryTreeService service;

    @Mock
    private BinaryTreeRepository binaryTreeRepository;

    @BeforeEach
    void setUp() {
        service = new BinaryTreeService(binaryTreeRepository);
    }

    @Test
    void testCreateTree() {
        when(binaryTreeRepository.save(any(BinarySearchTree.class))).thenAnswer(i -> i.getArguments()[0]);
        BinarySearchTree result = service.createTree("50,30,70");
        assertNotNull(result);
        verify(binaryTreeRepository).save(any(BinarySearchTree.class));
    }

    @Test
    void testSearchNumber() {
        BinarySearchTree mockTree = new BinarySearchTree();
        mockTree.setInputNumbers("50,30,70");
        mockTree.insertNumbers(Arrays.asList(50, 30, 70));
        when(binaryTreeRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(mockTree));

        List<SearchResult> results = service.searchNumber(30);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(30, results.get(0).getSearchPath().get(results.get(0).getSearchPath().size() - 1));
    }

    @Test
    void testGetAllTrees() {
        List<BinarySearchTree> mockTrees = Arrays.asList(new BinarySearchTree(), new BinarySearchTree());
        when(binaryTreeRepository.findAllByOrderByIdDesc()).thenReturn(mockTrees);

        List<BinarySearchTree> results = service.getAllTrees();
        assertEquals(2, results.size());
    }

    @Test
    void testGetTreeById() {
        BinarySearchTree mockTree = new BinarySearchTree();
        when(binaryTreeRepository.findById(1L)).thenReturn(Optional.of(mockTree));

        BinarySearchTree result = service.getTreeById(1L);
        assertNotNull(result);
    }

    @Test
    void testFindBalancedTrees() {
        BinarySearchTree balancedTree = new BinarySearchTree();
        balancedTree.insertNumbers(Arrays.asList(50, 30, 70));
        when(binaryTreeRepository.findAllByOrderByIdDesc()).thenReturn(Arrays.asList(balancedTree));

        List<BinarySearchTree> results = service.findBalancedTrees();
        assertFalse(results.isEmpty());
    }

    @Test
    void testCreateTreeWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> service.createTree(""));
        assertThrows(IllegalArgumentException.class, () -> service.createTree(null));
    }

    @Test
    void testDeleteTree() {
        when(binaryTreeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(binaryTreeRepository).deleteById(1L);

        assertDoesNotThrow(() -> service.deleteTree(1L));
        verify(binaryTreeRepository).deleteById(1L);
    }
}