package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Customer;

public class CustomerController extends Controller<Customer> {
    private List<Customer> customerList;

    public CustomerController(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public CustomerController() {
        customerList = new ArrayList<>();
    }

    @Override
    public void add(Customer customer) {
        customerList.add(customer);
    }

    @Override
    public void update(int id, Customer updatedCustomer) {
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId() == id) {
                customerList.set(i, updatedCustomer);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        customerList.removeIf(customer -> customer.getId() == id);
    }

    @Override
    public List<Customer> getAll() {
        return customerList;
    }
}
