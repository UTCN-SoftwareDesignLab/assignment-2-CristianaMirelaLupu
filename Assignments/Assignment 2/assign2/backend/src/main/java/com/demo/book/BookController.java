package com.demo.book;

import com.demo.UrlMapping;
import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
import com.demo.report.ReportServiceFactory;
import com.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(UrlMapping.BOOK)
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }

    @DeleteMapping
    public void deleteAll() { bookService.deleteAll(); }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable String id) {
        bookService.deleteById(Long.parseLong(id));}

    @GetMapping(UrlMapping.EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) throws FileNotFoundException, JRException {
        return reportServiceFactory.getReportService(type).export();
    }
}
