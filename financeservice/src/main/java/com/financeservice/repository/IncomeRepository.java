package com.financeservice.repository;


import com.financeservice.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findByTitleAndUserEmail(String title, String userEmail);
    //boolean existsByTitleAnduserEmail(String title, String user);

    List<Income> findAllByUserEmail(String user);

   @Query("Select COALESCE(SUM(i.amount), 0)  from Income i where i.userEmail = userEmail")
    Double getTotalIncomeByUserEmail(String  userEmail);

    @Query("select sum(e.amount) from Income e")
    Double getTotalIncomeForAllUserEmail();
}
