package views;

import javax.swing.*;
import java.awt.*;

public class OrderManagementView {
    private JPanel contentPanel;

    public OrderManagementView(JPanel mainPanel, CardLayout cardLayout) {
        contentPanel = new JPanel(new BorderLayout());

        // Sidebar nhỏ cho Quản lý đặt hàng
        JPanel sidebar = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton btnManageCustomers = new JButton("Quản lý khách hàng");
        JButton btnManageOrders = new JButton("Quản lý đơn hàng");
        JButton btnBack = new JButton("Quay lại");

        sidebar.add(btnManageCustomers);
        sidebar.add(btnManageOrders);
        sidebar.add(btnBack);

        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Chọn chức năng quản lý từ menu bên trái", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mainContent.add(label, BorderLayout.CENTER);

        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(mainContent, BorderLayout.CENTER);

        // Sự kiện nút
        btnManageCustomers.addActionListener(e -> cardLayout.show(mainPanel, "Customer"));
        btnManageOrders.addActionListener(e -> cardLayout.show(mainPanel, "Order"));
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}
