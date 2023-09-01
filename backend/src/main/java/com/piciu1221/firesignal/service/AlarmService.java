package com.piciu1221.firesignal.service;

import com.piciu1221.firesignal.model.Alarm;
import com.piciu1221.firesignal.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public List<Alarm> getAlarmsForFirefighter(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return alarmRepository.findAlarmsForFirefighter(username, pageable);
    }
}
