package views;

import controllers.OrderController;
import dao.OrderDao;
import models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class OrderView extends View<Order> {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private OrderController orderController;
    private int selectedOrderId = -1;

    public OrderView() throws SQLException {
        orderController = new OrderController();

        contentPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Người lập", "Loại sản phẩm", "Trạng thái", "Ngày lập đơn", "Ngày thanh toán", "Đã thanh toán"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedOrderId = selectedRow != -1 ? Integer.parseInt(table.getValueAt(selectedRow, 0).toString()) : -1;
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
                    OrderDao orderDao = new OrderDao();
                    Order order = orderDao.getById(id);
                    if (order != null) {
                        tableModel.setRowCount(0);
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
            selectedOrderId = -1;
        });
    }

    @Override
    protected void loadData() throws SQLException {
        OrderDao orderDao = new OrderDao();
        List<Order> orders = orderDao.getAll();
        tableModel.setRowCount(0);
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getId(), order.getFounder(), order.getType(),
                    order.getConditions(), order.getApplicationdate(), order.getPaymentdate(),
                    order.getPaid()
            });
        }
    }

    @Override
    protected void addEntity() {
        try {
            new EditOrderView(orderController, null, tableModel, table).setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm đơn hàng: " + ex.getMessage());
        }
    }

    @Override
    protected void editEntity() {
        if (selectedOrderId != -1) {
            try {
                OrderDao orderDao = new OrderDao();
                Order selectedOrder = orderDao.getById(selectedOrderId);
                if (selectedOrder != null) {
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
    }

    @Override
    protected void deleteEntity() {
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
                        loadData();
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
