package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /** 全件検索して返す */
    public List<Report> getReportList() {
        return reportRepository.findAll();
    }

    /** 一件検索して返す */
    public Report getReport(Integer id) {
        return reportRepository.findById(id).get();
    }

    /** 自分のものだけ検索して返す */
    public List<Report> getMyReportList(Employee employee) {
        return reportRepository.findByEmployee(employee);
    }

    /** 新規登録 */
    @Transactional
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

}
