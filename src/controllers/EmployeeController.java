package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Employee;


public class EmployeeController {
	private List<Employee> employeeList;

    public EmployeeController(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public EmployeeController() {
        employeeList = new ArrayList<>();
    }

    // Add a new employee
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    // Update an existing employee
    public void updateEmployee(int id, Employee updatedEmployee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId()==id) {
                employeeList.set(i, updatedEmployee);
                break;
            }
        }
    }

    // Delete a employee by ID
    public void deleteEmployee(int id) {
        employeeList.removeIf(employee -> employee.getId()==id);
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeList;
    }
	
}
