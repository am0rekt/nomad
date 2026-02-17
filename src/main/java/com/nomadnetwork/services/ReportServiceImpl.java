package com.nomadnetwork.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomadnetwork.entity.Post;
import com.nomadnetwork.entity.Report;
import com.nomadnetwork.entity.User;
import com.nomadnetwork.repository.ReportRepository;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
    private ReportRepository reportRepository;

    public void reportPost(Post post, User user, String reason) {

        if (reportRepository.existsByPostAndReportedBy(post, user)) {
            return; // already reported
        }

        Report report = new Report();
        report.setPost(post);
        report.setReportedBy(user);
        report.setReason(reason);
        report.setReportedAt(LocalDateTime.now());

        reportRepository.save(report);
    }
    

    @Override
    public List<Report> getAllReports() {
    	return reportRepository.findAllByOrderByReportedAtDesc();
    }

    @Override
    public long getReportCount() {
        return reportRepository.count();
    }

}
