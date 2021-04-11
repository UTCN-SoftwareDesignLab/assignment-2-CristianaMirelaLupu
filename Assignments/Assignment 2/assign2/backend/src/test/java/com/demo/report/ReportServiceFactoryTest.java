package com.demo.report;

import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class ReportServiceFactoryTest {

    @Autowired
    private ReportServiceFactory reportServiceFactory;

    @Test
    void getReportService() throws FileNotFoundException, JRException {
        ReportService csvReportService = reportServiceFactory.getReportService(ReportType.CSV);
        Assertions.assertEquals("Report.csv", csvReportService.export());

        ReportService pdfReportService = reportServiceFactory.getReportService(ReportType.PDF);
        Assertions.assertEquals("Report.pdf", pdfReportService.export());
    }
}