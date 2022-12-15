package com.example.bot1.repo;

import com.example.bot1.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepo extends JpaRepository<Journal, Long> {
    List<Journal> findAllByDate(String date);
}
