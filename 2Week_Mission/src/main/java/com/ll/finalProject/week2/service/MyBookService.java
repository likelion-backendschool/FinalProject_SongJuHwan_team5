package com.ll.finalProject.week2.service;

import com.ll.finalProject.week2.domain.Member;
import com.ll.finalProject.week2.domain.MyBook;
import com.ll.finalProject.week2.domain.Product;
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

    public MyBook findByMemberAndProduct(Product product, Member member) {
        return myBookRepository.findByMemberAndProduct(member, product);
    }

    public void remove(MyBook myBook) {
        myBookRepository.delete(myBook);
    }
}
