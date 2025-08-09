package com.expense_category_service.repository;

import com.expense_category_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {

    List<Category> findByCreatedBy(String createdBy);

    Optional<Category> findById(Long id);

    Optional<Category>  findByTitle(String title);
    boolean existsByTitle(String title);

    boolean existsByTitleAndCreatedBy(String title, String createdBy);

}
