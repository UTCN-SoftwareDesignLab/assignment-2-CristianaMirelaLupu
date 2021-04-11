package com.demo.book;

import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
import com.demo.user.dto.UserListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .author("author" + i)
                    .build();
            books.add(book);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getTitle(), bookDTOS.get(i).getTitle());
        }
    }

    @Test
    void createAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .author("author" + i)
                    .build();
            books.add(book);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getTitle(), bookDTOS.get(i).getTitle());
        }
    }

    @Test
    void updateAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < nrBooks; i++) {
            Book book = Book.builder()
                    .title("Title " + i)
                    .author("author" + i)
                    .build();
            books.add(book);

            book.setTitle("Titleeeeeeeee" + i);
            bookRepository.save(book);
        }

        List<BookDTO> bookDTOS = bookService.findAll();

        for (int i = 0; i < nrBooks; i++) {
            assertEquals(books.get(i).getId(), bookDTOS.get(i).getId());
            assertEquals(books.get(i).getTitle(), bookDTOS.get(i).getTitle());
        }
    }

    @Test
    void deleteAll() {
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}