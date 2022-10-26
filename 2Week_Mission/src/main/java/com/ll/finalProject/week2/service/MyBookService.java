package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.MyBook;
import com.ll.finalProject.week2.repository.MyBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyBookService {
    private final MyBookRepository myBookRepository;

    public void addBook(MyBook myBook) {
        myBookRepository.save(myBook);
    }
}
