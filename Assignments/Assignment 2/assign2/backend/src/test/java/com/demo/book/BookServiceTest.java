package com.demo.book;

import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
import com.demo.TestCreationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.demo.TestCreationFactory.randomLong;
import static com.demo.TestCreationFactory.randomString;
import static com.demo.UrlMapping.BOOK;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
        bookService.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create() {

        List <BookDTO> reqBook = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.create(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO result = bookService.create(reqBook.get(0));
        Assertions.assertNotNull(result);
    }

    @Test
    void deleteAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }
}