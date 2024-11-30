package com.keyin.bst.repository;

import com.keyin.bst.model.BinarySearchTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeRepository extends JpaRepository<BinarySearchTree, Long> {
}
