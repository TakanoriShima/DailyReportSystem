package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository repository) {
        this.employeeRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Employee> getEmployeeList() {
        return employeeRepository.findAll();
    }

    /** 1件を検索して返す */
    public Employee getEmployee (Integer id) {
        return employeeRepository.findById(id).get();
    }

    /** 登録処理 */
    @Transactional
    public Employee saveEmployee (Employee employee) {
        return employeeRepository.save(employee);
    }
}
