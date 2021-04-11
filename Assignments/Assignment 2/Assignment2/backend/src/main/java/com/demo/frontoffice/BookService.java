package com.demo.frontoffice;

import com.demo.frontoffice.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id){
        return bookRepository.findById(id);
    }

    public Book save (Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book){
        Optional<Book> old = findById(book.getId());

        if (old != null ){
            book.setTitle(book.getTitle());
            book.setAuthor(book.getAuthor());
            book.setQuantity(book.getQuantity());

            return bookRepository.save(book);
        }
    }

    public void deleteAll(){
        bookRepository.deleteAll();
    }

    public void deleteById(Long id){
        Optional<Book> old = findById(id);

        if (old != null ){
            bookRepository.deleteById(id);
        }
    }


}
