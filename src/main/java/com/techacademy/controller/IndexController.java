package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
public class IndexController {

//    @GetMapping("/")
//    public String getIndex() {
//        return "index";
//    }

    private final ReportService service;

    public IndexController(ReportService service) {
        this.service = service;
    }

    /** 自分のレポート一覧画面を表示 */
    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        /** 自分のレポートの検索結果をModelに登録 */
        Employee employee = userDetail.getEmployee();
        model.addAttribute("myreportlist", service.getMyReportList(employee));
        return "index";
    }

}
