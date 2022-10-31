package com.ll.finalProject.week2.repository;

import com.ll.finalProject.week2.domain.CashLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<CashLog, Long> {

}
