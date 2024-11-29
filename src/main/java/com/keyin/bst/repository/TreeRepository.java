package com.keyin.bst.repository;

import com.keyin.bst.model.BinarySearchTree;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TreeRepository extends JpaRepository<BinarySearchTree, Long> {
    List<BinarySearchTree> findByParentId(Long parentId);
}
