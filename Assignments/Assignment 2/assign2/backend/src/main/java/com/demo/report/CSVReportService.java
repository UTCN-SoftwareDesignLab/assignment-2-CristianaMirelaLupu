package com.demo.report;

import com.demo.book.BookService;
import com.demo.book.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
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

        try (PrintWriter writer = new PrintWriter(new File(FILE_NAME))) {

            StringBuilder sb = new StringBuilder();

            sb.append("id,");
            sb.append(',');
            sb.append("Title,");
            sb.append(',');
            sb.append("Author,");
            sb.append(',');
            sb.append("Genre,");
            sb.append(',');
            sb.append("Price,");
            sb.append('\n');
            writer.write(sb.toString());

            for (BookDTO b: books) {
                sb.append(b.getId().toString());
                sb.append(',');
                sb.append(b.getTitle());
                sb.append(',');
                sb.append(b.getAuthor());
                sb.append(',');
                sb.append(b.getGenre());
                sb.append(',');
                sb.append(Double.toString(b.getPrice()));
                sb.append('\n');
                writer.write(sb.toString());
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
