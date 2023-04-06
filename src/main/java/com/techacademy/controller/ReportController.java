package com.techacademy.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** レポート一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        /** 全件検索結果をModelに登録 */
        model.addAttribute("reportlist", service.getReportList());

        model.addAttribute("title", "日報一覧");
        model.addAttribute("lib", "report/list::lib");
        model.addAttribute("main", "report/list::main");

        return "common/layout";
    }

    /** レポート詳細画面を表示 */
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("report", service.getReport(id));

        model.addAttribute("title", "日報詳細");
        model.addAttribute("lib", "report/detail::lib");
        model.addAttribute("main", "report/detail::main");

        return "common/layout";
    }

    /** レポート登録画面を表示 */
    @GetMapping("/create")
    public String createReport(@AuthenticationPrincipal UserDetail userDetail, Report report, Model model) {

        /** ログイン中のユーザー情報を取得 */
        Employee employee = userDetail.getEmployee();

        /** reportにログイン中のユーザー情報を紐付け */
        report.setEmployee(employee);
        model.addAttribute("report", report);

        model.addAttribute("title", "日報登録登録");
        model.addAttribute("lib", "report/create::lib");
        model.addAttribute("main", "report/create::main");

        return "common/layout";
    }

    /** レポート登録処理 */
    @PostMapping("/create")
    public String postReport(@AuthenticationPrincipal UserDetail userDetail, Report report) {
        Employee emp = userDetail.getEmployee();
        report.setEmployee(emp);
        service.saveReport(report);

        return "redirect:/report/list";
    }

    /** レポート編集画面を表示 */
    @GetMapping("/update/{id}")
    public String updateReport(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("report", service.getReport(id));

        model.addAttribute("title", "日報編集");
        model.addAttribute("lib", "report/update::lib");
        model.addAttribute("main", "report/update::main");

        return "common/layout";
    }

    /** レポート編集処理 */
    @PostMapping("/update/{id}")
    public String postReport(@AuthenticationPrincipal UserDetail userDetail, Report report, @PathVariable("id") Integer id) {
        Employee emp = userDetail.getEmployee();
        report.setEmployee(emp);
        report.setId(id);
        report.setCreatedAt(report.getCreatedAt());
        service.saveReport(report);
        return "redirect:/report/list";
    }
}
