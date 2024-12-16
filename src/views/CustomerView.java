package views;

import controllers.CustomerController;
import dao.CustomerDao;
import models.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class CustomerView extends View<Customer> {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private CustomerController customerController;
    private int selectedCustomerId = -1;

    public CustomerView() throws SQLException {
        customerController = new CustomerController();

        contentPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày sinh", "Loại khách hàng"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedCustomerId = selectedRow != -1 ? Integer.parseInt(table.getValueAt(selectedRow, 0).toString()) : -1;
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
                    CustomerDao customerDao = new CustomerDao();
                    Customer customer = customerDao.getById(id);
                    if (customer != null) {
                        tableModel.setRowCount(0);
                        tableModel.addRow(new Object[]{
                                customer.getId(), customer.getName(), customer.getPhonenumber(),
                                customer.getAddress(), customer.getDate(), customer.getRank()
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
            selectedCustomerId = -1;
        });
    }

    @Override
    protected void loadData() throws SQLException {
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customers = customerDao.getAll();
        tableModel.setRowCount(0);
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                    customer.getId(), customer.getName(), customer.getPhonenumber(),
                    customer.getAddress(), customer.getDate(), customer.getRank()
            });
        }
    }

    @Override
    protected void addEntity() {
        new EditCustomerView(customerController, null, tableModel, table).setVisible(true);
    }

    @Override
    protected void editEntity() {
        if (selectedCustomerId != -1) {
            try {
                CustomerDao customerDao = new CustomerDao();
                Customer selectedCustomer = customerDao.getById(selectedCustomerId);
                if (selectedCustomer != null) {
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
    }

    @Override
    protected void deleteEntity() {
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
                        loadData();
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
