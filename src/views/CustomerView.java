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

import controllers.CustomerController;
import dao.CustomerDao;
import models.Customer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerView {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private CustomerController customerController;
    private int selectedCustomerId = -1; // Biến lưu trữ ID của sản phẩm được chọn
    
    private void loadCustomerData() throws SQLException {
        // Lấy dữ liệu từ cơ sở dữ liệu
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customers = customerDao.getAll();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getId(),
                    customer.getName(),
                    customer.getPhonenumber(),
                    customer.getAddress(),
                    customer.getDate(),
                    customer.getRank()
            });
        }
    }

    public CustomerView() throws SQLException {
        customerController = new CustomerController();

        // Tạo JPanel chính
        contentPanel = new JPanel(new BorderLayout());

        // Tạo cột cho bảng
        String[] columns = {"ID", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày sinh", "Loại khách hàng"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Thêm sự kiện chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy ID từ cột đầu tiên của dòng được chọn
                    selectedCustomerId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                } else {
                    selectedCustomerId = -1; // Nếu không có dòng nào được chọn
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
        loadCustomerData();

        // Xử lý nút tìm kiếm
        btnSearch.addActionListener(e -> {
            String searchId = txtSearch.getText().trim();
            if (!searchId.isEmpty()) {
                try {
                    int id = Integer.parseInt(searchId);
                    CustomerDao customerDao = new CustomerDao(); // Tạo đối tượng ProductDao
                    Customer customer = customerDao.getById(id); // Gọi phương thức tìm kiếm theo ID

                    if (customer != null) {
                        // Hiển thị sản phẩm lên bảng
                        tableModel.setRowCount(0); // Xóa dữ liệu cũ
                        tableModel.addRow(new Object[]{
                            customer.getId(), customer.getName(), customer.getPhonenumber(),
                            customer.getAddress(), customer.getDate(),
                            customer.getRank()
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng với ID: " + id);
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
                loadCustomerData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage());
            }
        });

        // Nút thêm sản phẩm
        btnAdd.addActionListener(e -> new EditCustomerView(customerController, null, tableModel, table).setVisible(true));

        // Nút sửa sản phẩm
        btnEdit.addActionListener(e -> {
            if (selectedCustomerId != -1) {
                try {
                    CustomerDao customerDao = new CustomerDao(); // Kết nối cơ sở dữ liệu
                    Customer selectedCustomer = customerDao.getById(selectedCustomerId); // Lấy sản phẩm theo ID

                    if (selectedCustomer != null) {
                        // Mở giao diện EditProductView với sản phẩm được chọn
                        new EditCustomerView(customerController, selectedCustomer, tableModel, table).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy khách hàng để sửa!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải thông tin khách hàng: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để sửa!");
            }
        });


        // Nút xóa sản phẩm
        btnDelete.addActionListener(e -> {
            if (selectedCustomerId != -1) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa khách hàng này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        CustomerDao customerDao = new CustomerDao();
                        if (customerDao.delete(selectedCustomerId)) {
                            JOptionPane.showMessageDialog(null, "Xóa khách hàng thành công!");
                            loadCustomerData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không thể xóa khách hàng. Vui lòng thử lại.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa khách hàng: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một khách hàng để xóa!");
            }
        });
        
        btnSync.addActionListener(e -> {
            table.clearSelection(); // Bỏ chọn dòng hiện tại
            selectedCustomerId = -1; // Đặt lại giá trị ID sản phẩm được chọn
        });
    }
    private void loadTableData(List<Customer> customers) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        // Thêm dữ liệu mới vào bảng
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getId(), customer.getName(), customer.getPhonenumber(),
                    customer.getAddress(), customer.getDate(),
                    customer.getRank()
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


