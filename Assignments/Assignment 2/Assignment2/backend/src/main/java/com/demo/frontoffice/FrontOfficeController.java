package com.demo.frontoffice;

import com.demo.frontoffice.model.Book;
import com.demo.report.ReportServiceFactory;
import com.demo.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.demo.UrlMapping.EXPORT_REPORT;
import static com.demo.UrlMapping.FRONT_OFFICE;

@RestController
@RequestMapping(FRONT_OFFICE)
@RequiredArgsConstructor
public class FrontOfficeController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<Book> allBooks() {
        return bookService.findAll();
    }

    @GetMapping
    public Optional<Book> findById(Long id){return bookService.findById(id);}

    @GetMapping
    public Book save (Book book){ return bookService.save(book);}

    @GetMapping
    public void deleteAll(){ bookService.deleteAll();}

    @GetMapping
    public void deleteById(Long id){ bookService.deleteById(id);}

    @GetMapping(EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type) {
        return reportServiceFactory.getReportService(type).export();
    }
}
