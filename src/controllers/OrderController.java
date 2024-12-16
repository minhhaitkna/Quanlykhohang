package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Order;

public class OrderController extends Controller<Order> {
    private List<Order> orderList;

    public OrderController(List<Order> orderList) {
		this.orderList= orderList;
	}

	public OrderController() {
		orderList = new ArrayList<>();
    }
    @Override
    public void add(Order order) {
    	orderList.add(order);
    }
    @Override
    public void update(int id, Order updatedOrder) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getId()==id) {
            	orderList.set(i, updatedOrder);
                break;
            }
        }
    }
    @Override
    public void delete(int id) {
    	orderList.removeIf(order -> order.getId()==id);
    }
    @Override
    public List<Order> getAll() {
        return orderList;
    }
}
