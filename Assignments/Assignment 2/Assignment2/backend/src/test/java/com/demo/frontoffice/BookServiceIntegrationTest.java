package com.demo.frontoffice;

import com.demo.frontoffice.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrBooks = 10;
        for (int i = 0; i < nrBooks; i++) {
            bookRepository.save(Book.builder().title(String.valueOf(i)).build());
        }

        List<Book> all = bookService.findAll();

        Assertions.assertEquals(nrBooks, all.size());
    }
}