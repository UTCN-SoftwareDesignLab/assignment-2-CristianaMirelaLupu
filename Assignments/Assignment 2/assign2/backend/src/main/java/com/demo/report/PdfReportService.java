package com.demo.report;

import com.demo.book.BookService;
import com.demo.book.model.Book;
import com.demo.book.model.dto.BookDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
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

        parameter.put("title", new String("Books out of stock"));

        JasperReport jasperDesign = null;
        try {
            jasperDesign = JasperCompileManager.compileReport(getDesign());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperDesign, parameter,
                    bookCollectionDataSource);

        File file = new File(FILE_NAME);

            OutputStream outputSteam = new FileOutputStream(file);

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
        mystyle.setFontSize(22f);
        mystyle.setPdfFontName("Helvetica");
        mystyle.setPdfEncoding("UTF-8");
        jasDes.addStyle(mystyle);

        // Fields
        JRDesignField field1 = new JRDesignField();
        field1.setName("title");
        field1.setValueClass(String.class);
        jasDes.addField(field1);

        JRDesignField field2 = new JRDesignField();
        field2.setName("author");
        field2.setValueClass(String.class);
        jasDes.addField(field2);

        // Title
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(50);

        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setText("Books out of stock");
        titleText.setX(0);
        titleText.setY(10);
        titleText.setWidth(515);
        titleText.setHeight(30);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setFontSize(22f);
        titleBand.addElement(titleText);
        jasDes.setTitle(titleBand);

        // Detail
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(60);

        JRDesignTextField tf1 = new JRDesignTextField();
        tf1.setBlankWhenNull(true);
        tf1.setX(0);
        tf1.setY(10);
        tf1.setWidth(200);
        tf1.setHeight(30);
        tf1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf1.setStyle(mystyle);
        tf1.setExpression(new JRDesignExpression("$F{title}"));
        detailBand.addElement(tf1);

        JRDesignTextField tf2 = new JRDesignTextField();
        tf2.setBlankWhenNull(true);
        tf2.setX(200);
        tf2.setY(10);
        tf2.setWidth(200);
        tf2.setHeight(30);
        tf2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        tf2.setStyle(mystyle);
        tf2.setExpression(new JRDesignExpression("$F{author}"));
        detailBand.addElement(tf2);

        ((JRDesignSection) jasDes.getDetailSection()).addBand(detailBand);

        return jasDes;
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
