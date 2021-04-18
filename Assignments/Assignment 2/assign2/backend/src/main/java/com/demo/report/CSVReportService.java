package com.demo.report;

import com.demo.book.BookService;
import com.demo.book.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.DoubleBuffer;
import java.util.List;

import static com.demo.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {

    private final BookService bookService;
    private static final String FILE_NAME = "Report.csv";

    public CSVReportService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String export() {

        List<BookDTO> books = bookService.findOutOfStock();


        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(FILE_NAME), false)) ) {

            StringBuilder sb = new StringBuilder();

            sb.append("id");
            sb.append(',');
            sb.append("Title");
            sb.append(',');
            sb.append("Author");
            sb.append(',');
            sb.append("Genre");
            sb.append(',');
            sb.append("Price");
            sb.append('\n');
            writer.write(sb.toString());

            for (BookDTO b: books) {

                StringBuilder lineSb = new StringBuilder();
                lineSb.append(b.getId().toString());
                lineSb.append(',');
                lineSb.append(b.getTitle());
                lineSb.append(',');
                lineSb.append(b.getAuthor());
                lineSb.append(',');
                lineSb.append(b.getGenre());
                lineSb.append(',');
                lineSb.append(Double.toString(b.getPrice()));
                lineSb.append('\n');
                writer.write(lineSb.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return FILE_NAME;
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
