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

import controllers.EmployeeController;
import dao.EmployeeDao;
import models.Employee;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeView {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private EmployeeController employeeController;
    private int selectedEmployeeId = -1; // Biến lưu trữ ID của sản phẩm được chọn
    
    private void loadEmployeeData() throws SQLException {
        // Lấy dữ liệu từ cơ sở dữ liệu
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.getAll();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getId(),
                    employee.getName(),
                    employee.getSalary(),
                    employee.getAccount(),
                    employee.getPassword(),
                    employee.getPhone_number(),
                    employee.getRank()
            });
        }
    }

    public EmployeeView() throws SQLException {
        employeeController = new EmployeeController();

        // Tạo JPanel chính
        contentPanel = new JPanel(new BorderLayout());

        // Tạo cột cho bảng
        String[] columns = {"ID", "Tên nhân viên", "Lương", "Tài khoản", "Mật khẩu", "Số điện thoại", "Loại nhân viên"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Thêm sự kiện chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy ID từ cột đầu tiên của dòng được chọn
                    selectedEmployeeId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                } else {
                    selectedEmployeeId = -1; // Nếu không có dòng nào được chọn
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
        loadEmployeeData();

        // Xử lý nút tìm kiếm
        btnSearch.addActionListener(e -> {
            String searchId = txtSearch.getText().trim();
            if (!searchId.isEmpty()) {
                try {
                    int id = Integer.parseInt(searchId);
                    EmployeeDao employeeDao = new EmployeeDao(); // Tạo đối tượng ProductDao
                    Employee employee = employeeDao.getById(id); // Gọi phương thức tìm kiếm theo ID

                    if (employee != null) {
                        // Hiển thị sản phẩm lên bảng
                        tableModel.setRowCount(0); // Xóa dữ liệu cũ
                        tableModel.addRow(new Object[]{
                            employee.getId(), employee.getName(), employee.getSalary(),
                            employee.getAccount(), employee.getPassword(), employee.getPhone_number(),
                            employee.getRank()
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với ID: " + id);
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
                loadEmployeeData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage());
            }
        });

        // Nút thêm sản phẩm
        btnAdd.addActionListener(e -> new EditEmployeeView(employeeController, null, tableModel, table).setVisible(true));

        // Nút sửa sản phẩm
        btnEdit.addActionListener(e -> {
            if (selectedEmployeeId != -1) {
                try {
                    EmployeeDao employeeDao = new EmployeeDao(); // Kết nối cơ sở dữ liệu
                    Employee selectedEmployee = employeeDao.getById(selectedEmployeeId); // Lấy sản phẩm theo ID

                    if (selectedEmployee != null) {
                        // Mở giao diện EditProductView với sản phẩm được chọn
                        new EditEmployeeView(employeeController, selectedEmployee, tableModel, table).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên để sửa!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải thông tin nhân viên: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để sửa!");
            }
        });


        // Nút xóa sản phẩm
        btnDelete.addActionListener(e -> {
            if (selectedEmployeeId != -1) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn có chắc chắn muốn xóa nhân viên này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        EmployeeDao employeeDao = new EmployeeDao();
                        if (employeeDao.delete(selectedEmployeeId)) {
                            JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
                            loadEmployeeData();
                        } else {
                            JOptionPane.showMessageDialog(null, "Không thể xóa nhân viên. Vui lòng thử lại.");
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Lỗi khi xóa nhân viên: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhân viên để xóa!");
            }
        });
        
        btnSync.addActionListener(e -> {
            table.clearSelection(); // Bỏ chọn dòng hiện tại
            selectedEmployeeId = -1; // Đặt lại giá trị ID sản phẩm được chọn
        });
    }
    private void loadTableData(List<Employee> employees) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        // Thêm dữ liệu mới vào bảng
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getId(), employee.getName(), employee.getSalary(),
                    employee.getAccount(), employee.getPassword(), employee.getPhone_number(),
                    employee.getRank()
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


