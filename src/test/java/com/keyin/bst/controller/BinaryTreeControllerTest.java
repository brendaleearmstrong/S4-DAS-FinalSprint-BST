package com.keyin.bst.controller;

import com.keyin.bst.model.BinarySearchTree;
import com.keyin.bst.model.SearchResult;
import com.keyin.bst.service.BinaryTreeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class BinaryTreeControllerTest {
    private BinaryTreeController controller;

    @Mock
    private BinaryTreeService binaryTreeService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        controller = new BinaryTreeController(binaryTreeService);
    }

    @Test
    void testProcessNumbers() {
        BinarySearchTree mockTree = new BinarySearchTree();
        mockTree.insertNumbers(Arrays.asList(50, 30, 70));
        when(binaryTreeService.createTree("50,30,70")).thenReturn(mockTree);

        ResponseEntity<?> response = controller.processNumbers("50,30,70");
        assertTrue(response.getStatusCode().is2xxSuccessful());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertNotNull(responseBody.get("treeStructure"));
    }

    @Test
    void testSearchNumber() {
        List<Integer> path = Arrays.asList(50, 30);
        SearchResult mockResult = new SearchResult(1L, "50,30,70", path, "treeJson", 2, 2, 0, true, 3);
        when(binaryTreeService.searchNumber(30)).thenReturn(Arrays.asList(mockResult));

        ResponseEntity<?> response = controller.searchNumber("30");
        assertTrue(response.getStatusCode().is2xxSuccessful());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertTrue((Boolean) responseBody.get("found"));
    }

    @Test
    void testShowPreviousTrees() {
        List<BinarySearchTree> mockTrees = new ArrayList<>();
        when(binaryTreeService.getRecentTrees()).thenReturn(mockTrees);

        String viewName = controller.showPreviousTrees(model);
        assertEquals("previous-trees", viewName);
        verify(model).addAttribute(eq("trees"), any());
    }

    @Test
    void testInvalidNumberFormat() {
        ResponseEntity<?> response = controller.searchNumber("invalid");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testGetMetrics() {
        BinarySearchTree mockTree = new BinarySearchTree();
        mockTree.insertNumbers(Arrays.asList(50, 30, 70));
        when(binaryTreeService.getTreeById(1L)).thenReturn(mockTree);

        ResponseEntity<?> response = controller.getTreeMetrics(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
    }
}