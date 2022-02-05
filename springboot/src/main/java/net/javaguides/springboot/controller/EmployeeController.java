package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exeception.ResourceNotFountException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    
    // get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }
    
    // 従業員の作成
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
    
    // 単一の従業員情報の取得
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeId(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("対象の従業員が存在しません。" + id));
        return ResponseEntity.ok(employee);
    }
    
    // 単一の従業員情報の更新処理
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("対象の従業員が存在しません。" + id));
        
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        
        Employee updateEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updateEmployee);
    }
    
    // 単一の従業員情報の削除処理
    @DeleteMapping("/employees/{id}")
    public ResponseEntity <Map<String, Boolean>> deleteEmployee(@PathVariable Long id){

            Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFountException("対象の従業員が存在しません。" + id));
            
            employeeRepository.delete(employee);
            Map<String, Boolean> response = new HashMap<>();
            response.put("削除", Boolean.TRUE);
            return ResponseEntity.ok(response);
    }
    
}
