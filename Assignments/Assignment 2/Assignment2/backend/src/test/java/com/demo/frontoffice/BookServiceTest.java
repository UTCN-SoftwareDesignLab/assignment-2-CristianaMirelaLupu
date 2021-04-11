package com.demo.frontoffice;
import com.demo.frontoffice.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    void findAll() {
        List<Book> items = new ArrayList<>();
        int nrBooks = 10;
        for (int i = 0; i < nrBooks; i++) {
            items.add(Book.builder().title(String.valueOf(i)).build());
        }

        when(bookRepository.findAll()).thenReturn(items);

        List<Book> all = bookService.findAll();

        Assertions.assertEquals(nrBooks, all.size());
    }
}