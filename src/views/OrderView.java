package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controllers.OrderController;
import dao.OrderDao;
import models.Order;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderView {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private OrderController orderController;
    private int selectedOrderId = -1; // Biến lưu trữ ID của sản phẩm được chọn
    
    private void loadOrderData() throws SQLException {
        // Lấy dữ liệu từ cơ sở dữ liệu
        OrderDao orderDao = new OrderDao();
        List<Order> orders = orderDao.getAll();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getId(),
                    order.getFounder(),
                    order.getType(),
                    order.getConditions(),
                    order.getApplicationdate(),
                    order.getPaymentdate(),
                    order.getPaid()
            });
        }
    }

    public OrderView() throws SQLException {
        orderController = new OrderController();

        // Tạo JPanel chính
        contentPanel = new JPanel(new BorderLayout());

        // Tạo cột cho bảng
        String[] columns = {"ID", "Người lập", "Loại sản phẩm", "Trạng thái", "Ngày lập đơn", "Ngày thanh toán" , "Đã thanh toán"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Thêm sự kiện chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy ID từ cột đầu tiên của dòng được chọn
                    selectedOrderId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                } else {
                    selectedOrderId = -1; // Nếu không có dòng nào được chọn
                }
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);

        // Panel tìm kiếm
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSearch = new JLabel("Tìm kiếm theo ID:");
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnShowAll = new JButton("Hiển thị tất cả");

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnShowAll);

        // Panel nút chức năng
        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnAdd = createButtonWithIcon("Thêm", "src/resources/icons/add.png");
        JButton btnEdit = createButtonWithIcon("Sửa", "src/resources/icons/edit.png");
        JButton btnDelete = createButtonWithIcon("Xóa", "src/resources/icons/delete.png");
        JButton btnSync = createButtonWithIcon("Đồng bộ", "src/resources/icons/sync.png");

        rightPanel.add(btnAdd);
        rightPanel.add(btnEdit);
        rightPanel.add(btnDelete);
        rightPanel.add(btnSync);


        // Thêm thành phần vào contentPanel
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);

        // Nạp dữ liệu ban đầu vào bảng
        loadOrderData();

        // Xử lý nút tìm kiếm
        btnSearch.addActionListener(e -> {
            String searchId = txtSearch.getText().trim();
            if (!searchId.isEmpty()) {
                try {
                    int id = Integer.parseInt(searchId);
                    OrderDao orderDao = new OrderDao(); // Tạo đối tượng ProductDao
                    Order order = orderDao.getById(id); // Gọi phương thức tìm kiếm theo ID

                    if (order != null) {
                        // Hiển thị sản phẩm lên bảng
                        tableModel.setRowCount(0); // Xóa dữ liệu cũ
                        tableModel.addRow(new Object[]{
                            order.getId(), order.getFounder(), order.getType(),
                            order.getConditions(), order.getApplicationdate(), order.getPaymentdate(),
                            order.getPaid()
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy đơn hàng với ID: " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID phải là một số nguyên hợp lệ!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập ID để tìm kiếm!");
            }
        });


        // Nút hiển thị tất cả
        btnShowAll.addActionListener(e -> {
            try {
                loadOrderData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage());
            }
        });

        // Nút thêm sản phẩm
        btnAdd.addActionListener(e -> {
			try {
				new EditOrderView(orderController, null, tableModel, table).setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        // Nút sửa sản phẩm
        btnEdit.addActionListener(e -> {
            if (selectedOrderId != -1) {
                try {
                    OrderDao orderDao = new OrderDao(); // Kết nối cơ sở dữ liệu
                    Order selectedOrder = orderDao.getById(selectedOrderId); // Lấy sản phẩm theo ID

                    if (selectedOrder != null) {
                        // Mở giao diện EditProductView với sản phẩm được chọn
                        new EditOrderView(orderController, selectedOrder, tableModel, table).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy đơn hàng để sửa!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải thông tin đơn hàng: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn hàng để sửa!");
            }
        });


        // Nút xóa sản phẩm
        btnDelete.addActionListener(e -> {
            if (selectedOrderId != -1) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa đơn hàng này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        OrderDao orderDao = new OrderDao();
                        if (orderDao.delete(selectedOrderId)) {
                            JOptionPane.showMessageDialog(null, "Xóa đơn hàng thành công!");
                            loadOrderData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không thể xóa đơn hàng. Vui lòng thử lại.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa đơn hàng: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn hàng để xóa!");
            }
        });
        
        btnSync.addActionListener(e -> {
            table.clearSelection(); // Bỏ chọn dòng hiện tại
            selectedOrderId = -1; // Đặt lại giá trị ID sản phẩm được chọn
        });
    }
    private void loadTableData(List<Order> orders) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        // Thêm dữ liệu mới vào bảng
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getId(), order.getFounder(), order.getType(),
                    order.getConditions(), order.getApplicationdate(), order.getPaymentdate(),
                    order.getPaid()
            });
        }
    }
    private JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);

        // Thêm icon
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Điều chỉnh kích thước icon
            button.setIcon(new ImageIcon(scaledIcon));
        }
        return button;
    }
    public JPanel getContentPanel() {
        return contentPanel;
    }
}


