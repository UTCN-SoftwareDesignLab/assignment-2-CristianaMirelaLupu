package com.demo.report;

import com.demo.book.BookService;
import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PdfReportService implements ReportService {

    private final BookService bookService;
    private static final String FILE_NAME = "Report.pdf";

    public PdfReportService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String export() {
        List<BookDTO> books = bookService.findOutOfStock();

        Map<String, Object> parameter  = new HashMap<String, Object>();

        JRBeanCollectionDataSource bookCollectionDataSource =
                new JRBeanCollectionDataSource(books);

        parameter.put("bookDataSource", bookCollectionDataSource);

        parameter.put("title", new String("Books out of stock"));

        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(getDesign());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter,
                    new JREmptyDataSource());

        File file = new File(FILE_NAME);
        OutputStream outputSteam = null;

        outputSteam = new FileOutputStream(file);

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputSteam);
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return FILE_NAME;
    }

    private JasperDesign getDesign() throws JRException {
        JasperDesign jasDes = new JasperDesign();
        jasDes.setName("myreport");
        jasDes.setPageWidth(595);
        jasDes.setPageHeight(842);
        jasDes.setLeftMargin(20);
        jasDes.setRightMargin(20);
        jasDes.setTopMargin(20);
        jasDes.setBottomMargin(20);
        jasDes.setColumnWidth(555);

        // Style
        JRDesignStyle mystyle = new JRDesignStyle();
        mystyle.setName("mystyle");
        mystyle.setDefault(true);
        mystyle.setFontName("DejaVu Sans");
        mystyle.setFontSize(22f);
        mystyle.setPdfFontName("Helvetica");
        mystyle.setPdfEncoding("UTF-8");
        jasDes.addStyle(mystyle);

        return jasDes;

    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
