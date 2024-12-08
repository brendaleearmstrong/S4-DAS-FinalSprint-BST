package com.keyin.bst.repository;

import com.keyin.bst.model.BinarySearchTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BinaryTreeRepository extends JpaRepository<BinarySearchTree, Long> {
    List<BinarySearchTree> findAllByOrderByIdDesc();
    List<BinarySearchTree> findTop10ByOrderByIdDesc();
}