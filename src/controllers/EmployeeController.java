package controllers;

import java.util.ArrayList;
import java.util.List;
import models.Employee;


public class EmployeeController extends Controller<Employee>{
	private List<Employee> employeeList;

    public EmployeeController(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public EmployeeController() {
        employeeList = new ArrayList<>();
    }

    @Override
    public void add(Employee employee) {
        employeeList.add(employee);
    }

    @Override
    public void update(int id, Employee updatedEmployee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId()==id) {
                employeeList.set(i, updatedEmployee);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        employeeList.removeIf(employee -> employee.getId()==id);
    }

    @Override
    public List<Employee> getAll() {
        return employeeList;
    }
	
}
