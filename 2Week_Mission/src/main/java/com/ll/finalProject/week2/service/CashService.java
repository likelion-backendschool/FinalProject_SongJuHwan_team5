package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.CashLog;
import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashService {

    private final CashRepository cashRepository;

    public void addCash(Member member, int cash, String eventType) {
        CashLog cashLog = new CashLog();
        cashLog.setMember(member);
        cashLog.setPrice(cash);
        cashLog.setEventType(eventType);
        cashRepository.save(cashLog);
    }
}
