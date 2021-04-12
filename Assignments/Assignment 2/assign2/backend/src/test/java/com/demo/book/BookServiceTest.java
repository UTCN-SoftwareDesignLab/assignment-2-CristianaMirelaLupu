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

import java.util.List;
import static org.mockito.Mockito.when;

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
    void findById() throws Exception{

        List <Book> reqBook = TestCreationFactory.listOf(Book.class);
        when(bookRepository.save(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO book = bookMapper.toDto(reqBook.get(0));
        bookService.create(book);

        Assertions.assertNotNull(bookService.findById(book.getId()));
    }

    @Test
    void create() {

        List <BookDTO> reqBook = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.create(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO result = bookService.create(reqBook.get(0));
        Assertions.assertNotNull(result);
    }

    @Test
    void update() {

        List <BookDTO> reqBook = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.create(reqBook.get(0))).thenReturn(reqBook.get(0));
        when(bookService.edit(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO res = bookService.create(reqBook.get(0));
        BookDTO result = bookService.edit(res);
        Assertions.assertNotNull(result);
    }

    @Test
    void deleteAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        bookRepository.deleteAll();

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(0, all.size());
    }

    @Test
    void deleteById() {

        List <Book> reqBook = TestCreationFactory.listOf(Book.class);
        when(bookRepository.save(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO book = bookMapper.toDto(reqBook.get(0));
        bookService.deleteById(book.getId());

        Assertions.assertNull(bookService.findById(book.getId()));
    }

    @Test
    void sell(Long nr) {

        List <Book> reqBook = TestCreationFactory.listOf(Book.class);
        when(bookRepository.save(reqBook.get(0))).thenReturn(reqBook.get(0));

        BookDTO book = bookMapper.toDto(reqBook.get(0));
        bookService.sell(book,nr);

        Assertions.assertNull(bookService.findById(book.getId()));
    }

    @Test
    void search(String description) {

        List <Book> reqBook = TestCreationFactory.listOf(Book.class);
        when(bookRepository.save(reqBook.get(0))).thenReturn(reqBook.get(0));

        Assertions.assertNotNull(bookService.filter(description));
    }
}