package views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import controllers.EmployeeStatisticsController;

public class HomeView extends JFrame {
    private JPanel mainPanel; // Khu vực thay đổi giao diện chính
    private CardLayout cardLayout; // Quản lý các giao diện chính
    
    public HomeView() throws SQLException {
        // Cấu hình JFrame
        setTitle("Trang Quản Lý");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Sidebar (Menu bên trái)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(174, 223, 228));

        JButton btnAdmin = createSidebarButton("Admin", "src/resources/icons/admin.png");
        JButton btnManageEmployees = createSidebarButton("Quản lý nhân viên", "src/resources/icons/EmployeeManagement.png");
        JButton btnManageProducts = createSidebarButton("Quản lý sản phẩm", "src/resources/icons/category.png");
        JButton btnManageOrders = createSidebarButton("Quản lý đặt hàng >", "src/resources/icons/OrderManagement.png");
        JButton btnStatistics = createSidebarButton("Thống kê >", "src/resources/icons/statistics.png");
        JButton btnSettings = createSidebarButton("Thiết lập", "src/resources/icons/settings.png");
        JButton btnExit = createSidebarButton("Thoát", "src/resources/icons/exit.png");

        // Panel con cho "Quản lý đặt hàng"
        JPanel subOrderPanel = new JPanel();
        subOrderPanel.setLayout(new BoxLayout(subOrderPanel, BoxLayout.Y_AXIS));
        subOrderPanel.setBackground(new Color(174, 223, 228));
        JButton btnManageCustomers = createSidebarButton("Quản lý khách hàng", "src/resources/icons/customers.png");
        JButton btnManageOrderDetails = createSidebarButton("Quản lý đơn hàng", "src/resources/icons/order.png");
        subOrderPanel.add(btnManageCustomers);
        subOrderPanel.add(btnManageOrderDetails);
        subOrderPanel.setVisible(false); // Ban đầu ẩn
        
     // Panel con cho "Thống kê"
        
        JPanel subStatisticsPanel = new JPanel() ;
        subStatisticsPanel.setLayout(new BoxLayout(subStatisticsPanel, BoxLayout.Y_AXIS));
        subStatisticsPanel.setBackground(new Color(174, 223, 228));
        JButton btnEmployeeStatistics = createSidebarButton("Thống kê nhân viên", "src/resources/icons/admin.png");
        JButton btnRevenueStatistics = createSidebarButton("Thống kê doanh thu", "src/resources/icons/revenue.png");
        subStatisticsPanel.add(btnEmployeeStatistics);
        subStatisticsPanel.add(btnRevenueStatistics);
        subStatisticsPanel.setVisible(false); // Ban đầu ẩn

        // Thêm các nút vào Sidebar
        sidebar.add(btnAdmin);
        sidebar.add(btnManageEmployees);
        sidebar.add(btnManageProducts);
        sidebar.add(btnManageOrders);
        sidebar.add(subOrderPanel);
        sidebar.add(btnStatistics);
        sidebar.add(subStatisticsPanel) ;
        sidebar.add(btnSettings);
        sidebar.add(btnExit);

        // Khu vực hiển thị chính (Main Panel với CardLayout)
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Tạo các giao diện con và thêm vào mainPanel
        
        JPanel homePanel = createHomePanel();
        JPanel employeePanel = new EmployeeView().getContentPanel(); // Giao diện Quản lý nhân viên
        JPanel productPanel = new ProductView().getContentPanel(); // Giao diện Quản lý sản phẩm
        JPanel orderManagementPanel = new OrderManagementView(mainPanel, cardLayout).getContentPanel(); // Giao diện Quản lý đặt hàng
        JPanel customerPanel = new CustomerView().getContentPanel(); // Giao diện Quản lý khách hàng
        JPanel orderPanel = new OrderView().getContentPanel(); // Giao diện Quản lý đơn hàng
        JPanel employeeStatisticsPanel = new EmployeeStatisticsView().getContentPanel() ; // Giao diện Thống kê nhân viên
        JPanel revenueStatisticsPanel = new RevenueStatisticsView().getContentPanel() ; // Giao diện Thống kê doanh thu
        JPanel settingPanel = new SettingView().getContentPanel() ;
        
        
        mainPanel.add(homePanel, "Home"); // Giao diện Trang chủ
        mainPanel.add(employeePanel, "Employee"); // Giao diện Quản lý nhân viên
        mainPanel.add(productPanel, "Product"); // Giao diện Quản lý sản phẩm
        mainPanel.add(orderManagementPanel, "OrderManagement"); // Giao diện Quản lý đặt hàng
        mainPanel.add(customerPanel, "Customer"); // Giao diện Quản lý khách hàng
        mainPanel.add(orderPanel, "Order"); // Giao diện Quản lý đơn hàng        
        mainPanel.add(employeeStatisticsPanel, "EmployeeStatistics"); // Giao diện Thống kê nhân viên
        mainPanel.add(revenueStatisticsPanel, "RevenueStatistics"); // Giao diện Thống kê doanh thu
        mainPanel.add(settingPanel, "Settings"); // Giao diện Thống kê doanh thu



        // Thêm Sidebar và Main Panel vào JFrame
        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Xử lý sự kiện cho các nút trong Sidebar
        btnManageEmployees.addActionListener(e -> cardLayout.show(mainPanel, "Employee"));
        btnManageProducts.addActionListener(e -> cardLayout.show(mainPanel, "Product"));
        btnManageOrders.addActionListener(e -> {
            boolean isExpanded = subOrderPanel.isVisible();
            subOrderPanel.setVisible(!isExpanded);
            btnManageOrders.setText(isExpanded ? "Quản lý đặt hàng >" : "Quản lý đặt hàng ^");
            revalidate(); // Cập nhật layout của sidebar
            repaint();
        });
        btnManageCustomers.addActionListener(e -> cardLayout.show(mainPanel, "Customer"));
        btnManageOrderDetails.addActionListener(e -> cardLayout.show(mainPanel, "Order"));
        btnStatistics.addActionListener(e -> {
            boolean isExpanded = subStatisticsPanel.isVisible();
            subStatisticsPanel.setVisible(!isExpanded);
            btnStatistics.setText(isExpanded ? "Thống kê >" : "Thống kê ^");
            revalidate(); // Cập nhật layout của sidebar
            repaint();
        });
        btnEmployeeStatistics.addActionListener(e -> cardLayout.show(mainPanel, "EmployeeStatistics"));
        btnRevenueStatistics.addActionListener(e -> cardLayout.show(mainPanel, "RevenueStatistics")) ;
        btnSettings.addActionListener(e -> cardLayout.show(mainPanel, "Settings")) ;
        btnExit.addActionListener(e -> LogoutBtnActionPerformed(e));

    }


    // Tạo giao diện Trang chủ
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(SystemColor.info); // Màu hồng
        JLabel label = new JLabel("Trang Quản Lý", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(Color.RED);
        homePanel.add(label, BorderLayout.CENTER);
        return homePanel;
    }

    private JButton createSidebarButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));
        button.setFocusPainted(false);

        // Thêm icon vào nút
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
            button.setIcon(new ImageIcon(scaledImage));
            button.setHorizontalAlignment(SwingConstants.LEFT); // Căn trái cả text và icon
        }
        return button;
    }
    
    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
        this.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
			try {
				new HomeView().setVisible(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}); 
}
    
         
        
        
    
}
