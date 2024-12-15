package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Order;

public class OrderController {
    private List<Order> orderList;

    public OrderController(List<Order> orderList) {
		this.orderList= orderList;
	}

	public OrderController() {
		orderList = new ArrayList<>();
    }

    public void addOrder(Order order) {
    	orderList.add(order);
    }

    public void updateOrder(int id, Order updatedOrder) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getId()==id) {
            	orderList.set(i, updatedOrder);
                break;
            }
        }
    }

    public void deleteOrder(int id) {
    	orderList.removeIf(order -> order.getId()==id);
    }

    public List<Order> getAllOrder() {
        return orderList;
    }
}
