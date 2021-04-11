package com.demo.book;

import com.demo.book.model.dto.BookDTO;
import com.demo.book.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findOutOfStock() {
       List<BookDTO> all =  bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

       List<BookDTO> result = new ArrayList<>();

        for (BookDTO b : all) {
            if (b.getQuantity() == 0) {
                result.add(b);
            }
        }
        return result;
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book actBook = findById(book.getId());
        actBook.setTitle(book.getTitle());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());

        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public void deleteAll (){
        bookRepository.deleteAll();
    }

    public void deleteById (Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookDTO> filter (String description){
        List<BookDTO> all = bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

        List<BookDTO> result = new ArrayList<>();

        for (BookDTO b : all) {
            if (b.getGenre().equals(description) || b.getTitle().equals(description) || b.getAuthor().equals(description)) {
                result.add(b);
            }
        }
        return result;
    }

    public BookDTO sell(BookDTO book, Long nr) {
        Book actBook = findById(book.getId());
        actBook.setQuantity(book.getQuantity() - nr);

        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }
}
