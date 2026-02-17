package com.nomadnetwork.services;

import java.util.List;

import com.nomadnetwork.entity.Report;

public interface ReportService {
	List<Report> getAllReports();

    long getReportCount();
}
