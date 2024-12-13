package com.finalproject.finalproject.services;

import com.finalproject.finalproject.entity.History;
import com.finalproject.finalproject.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    public History save(History history) {
        return historyRepository.save(history);
    }
    public List<History> findAll() {
        return historyRepository.findAll();
    }
}
