package com.example.bot1.service;

import com.example.bot1.entity.Time;
import com.example.bot1.repo.TimeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {
    private final TimeRepo timeRepo;

    public TimeService(@Autowired TimeRepo timeRepo) {
        this.timeRepo = timeRepo;
    }

    public List<String> getAllTimes() {
        return timeRepo.findAll().stream().map(Time::getTime).toList();
    }
}
