package com.demo.bookstore.model.dto;

import com.demo.book.model.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingDTO {
    BookDTO bookDTO;
    Long amount;
}
