package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Customer;

public class CustomerController {
    private List<Customer> customerList;

    public CustomerController(List<Customer> customerList) {
		this.customerList= customerList;
	}

	public CustomerController() {
		customerList = new ArrayList<>();
    }

    // Add a new product
    public void addCustomer(Customer customer) {
    	customerList.add(customer);
    }

    // Update an existing product
    public void updateCustomer(int id, Customer updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId()==id) {
            	customerList.set(i, updatedCustomer);
                break;
            }
        }
    }

    // Delete a product by ID
    public void deleteCustomer(int id) {
    	customerList.removeIf(customer -> customer.getId()==id);
    }

    // Get all products
    public List<Customer> getAllCustomers() {
        return customerList;
    }
}
