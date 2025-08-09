package com.expense_category_service.repository;


import com.expense_category_service.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserEmail(String userEmail);

   // List<Expense> findByUser(User user);
    Page<Expense> findByUserEmail(String  userEmail, Pageable pageable);


     boolean existsByCategoryId(Long categoryId);

     @Query("select COALESCE(SUM(e.amount), 0) FROM Expense e where e.userEmail = :userEmail")
     Double getTotalExpenseByUser(String userEmail);


     List<Expense> findTop3ByUserEmailOrderByAmountDesc(String userEmail);


   @Query("select COALESCE(SUM(e.amount), 0) FROM Expense e ")
    Double getTotalExpenseByAllUser();
}
