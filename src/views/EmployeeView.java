package views;

import controllers.EmployeeController;
import dao.EmployeeDao;
import models.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class EmployeeView extends View<Employee> {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private EmployeeController employeeController;
    private int selectedEmployeeId = -1;

    public EmployeeView() throws SQLException {
        employeeController = new EmployeeController();

        contentPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Tên nhân viên", "Lương", "Tài khoản", "Mật khẩu", "Số điện thoại", "Loại nhân viên"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedEmployeeId = selectedRow != -1 ? Integer.parseInt(table.getValueAt(selectedRow, 0).toString()) : -1;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSearch = new JLabel("Tìm kiếm theo ID:");
        JTextField txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnShowAll = new JButton("Hiển thị tất cả");

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnShowAll);

        JPanel rightPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JButton btnAdd = createButtonWithIcon("Thêm", "src/resources/icons/add.png");
        JButton btnEdit = createButtonWithIcon("Sửa", "src/resources/icons/edit.png");
        JButton btnDelete = createButtonWithIcon("Xóa", "src/resources/icons/delete.png");
        JButton btnSync = createButtonWithIcon("Đồng bộ", "src/resources/icons/sync.png");

        rightPanel.add(btnAdd);
        rightPanel.add(btnEdit);
        rightPanel.add(btnDelete);
        rightPanel.add(btnSync);

        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);

        loadData();

        btnSearch.addActionListener(e -> {
            String searchId = txtSearch.getText().trim();
            if (!searchId.isEmpty()) {
                try {
                    int id = Integer.parseInt(searchId);
                    EmployeeDao employeeDao = new EmployeeDao();
                    Employee employee = employeeDao.getById(id);
                    if (employee != null) {
                        tableModel.setRowCount(0);
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

        btnShowAll.addActionListener(e -> {
            try {
                loadData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage());
            }
        });

        btnAdd.addActionListener(e -> addEntity());

        btnEdit.addActionListener(e -> editEntity());

        btnDelete.addActionListener(e -> deleteEntity());

        btnSync.addActionListener(e -> {
            table.clearSelection();
            selectedEmployeeId = -1;
        });
    }

    @Override
    protected void loadData() throws SQLException {
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.getAll();
        tableModel.setRowCount(0);
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getId(), employee.getName(), employee.getSalary(),
                    employee.getAccount(), employee.getPassword(), employee.getPhone_number(),
                    employee.getRank()
            });
        }
    }

    @Override
    protected void addEntity() {
        new EditEmployeeView(employeeController, null, tableModel, table).setVisible(true);
    }

    @Override
    protected void editEntity() {
        if (selectedEmployeeId != -1) {
            try {
                EmployeeDao employeeDao = new EmployeeDao();
                Employee selectedEmployee = employeeDao.getById(selectedEmployeeId);
                if (selectedEmployee != null) {
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
    }

    @Override
    protected void deleteEntity() {
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
                        loadData();
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
    }

    @Override
    protected JButton createButtonWithIcon(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);

        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledIcon));
        }
        return button;
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }
}
