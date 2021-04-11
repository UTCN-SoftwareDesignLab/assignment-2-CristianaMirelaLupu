package com.demo.report;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface ReportService {
    String export() throws JRException, FileNotFoundException;

    ReportType getType();
}
