package com.demo.bookstore;

import com.demo.UrlMapping;
import com.demo.book.BookService;
import com.demo.book.model.dto.BookDTO;
import com.demo.bookstore.model.dto.ShoppingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.BOOK_STORE)
@RequiredArgsConstructor
public class BookStoreController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> search (@RequestParam String description) {

        if (description == null){
            bookService.findAll();
        }
        return bookService.filter(description);
    }

    @PatchMapping
    public BookDTO sell(@RequestBody ShoppingDTO shoppingDTO) {
        if (shoppingDTO.getBookDTO().getQuantity() >= shoppingDTO.getQuantity()) {
            return bookService.sell(shoppingDTO.getBookDTO(), shoppingDTO.getQuantity());
        }
        return shoppingDTO.getBookDTO();
    }
}
