package com.keyin.bst.repository;

import com.keyin.bst.model.BinarySearchTree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<BinarySearchTree, Long> {
}