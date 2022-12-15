package com.example.bot1.service;

import com.example.bot1.entity.Journal;
import com.example.bot1.repo.JournalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalService {
    private final JournalRepo journalRepo;
    private final TimeService timeService;

    public JournalService(@Autowired JournalRepo journalRepo,
                          @Autowired TimeService timeService) {
        this.journalRepo = journalRepo;
        this.timeService = timeService;
    }

    public void saveJournal(Journal journal) {

        journalRepo.save(journal);
    }

    public List<String> getAllTimeByDate(String date) {
        List<String> busyTimes = journalRepo.findAllByDate(date).stream().map(Journal::getTime).toList();
        List<String> times = timeService.getAllTimes();
        if (!busyTimes.isEmpty()) {
            times.removeAll(busyTimes);
        }
        return times;
    }
}
