package com.demo.frontoffice;

import com.demo.UrlMapping;
import com.demo.frontoffice.model.Book;
import com.demo.report.CSVReportService;
import com.demo.report.PdfReportService;
import com.demo.report.ReportServiceFactory;
import com.demo.report.ReportType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FrontOfficeControllerTest {

    protected MockMvc mockMvc;

    @InjectMocks
    private FrontOfficeController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new FrontOfficeController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<Book> items = new ArrayList<>();
        int nrItems = 10;
        for (int i = 0; i < nrItems; i++) {
            items.add(Book.builder().title(String.valueOf(i)).build());
        }

        when(bookService.findAll()).thenReturn(items);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.FRONT_OFFICE));

        String expectedJsonContent = new ObjectMapper().writeValueAsString(items);
        response.andExpect(status().isOk())
                .andExpect(content().json(expectedJsonContent, true));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(ReportType.PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(ReportType.CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.FRONT_OFFICE + UrlMapping.EXPORT_REPORT, ReportType.PDF.name()));
        ResultActions csvExport = mockMvc.perform(MockMvcRequestBuilders.get(UrlMapping.FRONT_OFFICE + UrlMapping.EXPORT_REPORT, ReportType.CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }
}