package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面を表示 **/
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("employeelist", service.getEmployeeList());
        
        // 
        model.addAttribute("title", "タイトル");
        model.addAttribute("lib", "employee/list::lib");
        model.addAttribute("main", "employee/list::main");
        return "common/layout";
    }

    /** 詳細画面を表示 */
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));

        model.addAttribute("title", "従業員詳細");
        model.addAttribute("lib", "employee/detail::lib");
        model.addAttribute("main", "employee/detail::main");
        // employee/list.htmlに遷移
        return "common/layout";
    }

    /** 登録画面を表示 */
    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee, Model model) {
        model.addAttribute("title", "従業員登録");
        model.addAttribute("lib", "employee/register::lib");
        model.addAttribute("main", "employee/register::main");

        return "common/layout";
    }

    /** 登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res, Model model) {
        if(res.hasErrors()) {
            model.addAttribute("title", "従業員登録");
            model.addAttribute("lib", "employee/register::lib");
            model.addAttribute("main", "employee/register::main");
            return getRegister(employee, model);
        }
        employee.setDeleteFlag(0);
        Authentication auth = employee.getAuthentication();
        auth.setEmployee(employee);
        auth.setPassword(passwordEncoder.encode(auth.getPassword()));

        service.saveEmployee(employee);

        model.addAttribute("title", "従業員登録");
        model.addAttribute("lib", "employee/list::lib");
        model.addAttribute("main", "employee/list::main");

        return "redirect:/employee/list";
    }

    /** 編集画面を表示 */
    @GetMapping("/update/{id}/")
    public String getUpdate(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));

        model.addAttribute("title", "従業員登録");
        model.addAttribute("lib", "employee/update::lib");
        model.addAttribute("main", "employee/update::main");

        return "common/layout";
    }

    /** 編集処理 */
    @PostMapping("update/{id}/")
    public String updateEmployee(@PathVariable Integer id, @ModelAttribute Employee employee, Model model) {
        /** 変更前の状態を取得 */
        Employee emp = service.getEmployee(id);
        Authentication auth = emp.getAuthentication();

        emp.setName(employee.getName());
        auth.setCode(employee.getAuthentication().getCode());
        auth.setPassword(employee.getAuthentication().getPassword());
        auth.setRole(employee.getAuthentication().getRole());

        service.saveEmployee(emp);

        model.addAttribute("title", "従業員登録");
        model.addAttribute("lib", "employee/update::lib");
        model.addAttribute("main", "employee/update::main");

        return "redirect:/employee/list";
    }

    /** 削除処理 */
    @GetMapping("delete/{id}/")
    public String deleteEmployee(@PathVariable("id") Integer id, @ModelAttribute Employee employee, Model model) {
        Employee emp = service.getEmployee(id);
        emp.setDeleteFlag(1);
        service.saveEmployee(emp);
        return "redirect:/employee/list";
    }
}
