package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import controllers.OrderController;
import dao.OrderDao;
import models.Order;

public class EditOrderView extends JFrame {
    private JTextField txtFounder, txtApplicationdate, txtPaymentdate, txtPaid, txtTotal;
    private JComboBox<String> cbType, cbConditions;
    private DefaultTableModel tableModel;
    private JTable table;
    private OrderController orderController;
    private Order order;
    private JLabel lblMessage;

    public EditOrderView(OrderController orderController, Order order, DefaultTableModel tableModel, JTable table) throws SQLException {
        this.orderController = orderController;
        this.order = order;
        this.tableModel = tableModel;
        this.table = table;

        // Frame settings
        setTitle("Chỉnh sửa đơn hàng");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        

        // Left panel: OrderProductView
        JPanel leftPanel = new JPanel(new BorderLayout());
        OrderProductView orderProductView = new OrderProductView();
        leftPanel.add(orderProductView.getContentPanel(), BorderLayout.CENTER);

        // Right panel: Form for editing/creating orders
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(9, 2, 10, 10));

        txtFounder = new JTextField(order != null ? order.getFounder() : "");
        cbType = new JComboBox<>(new String[]{"Sản phẩm Đồng", "Sản phẩm Bạc", "Sản phẩm Vàng" ,"Sản phẩm Bạch Kim" ,"Sản phẩm Kim Cương"});
        cbConditions = new JComboBox<>(new String[]{"Mới", "Đã sử dụng", "Hỏng"});
        if (order != null) {
            cbType.setSelectedItem(order.getType());
            cbConditions.setSelectedItem(order.getConditions());
        }
        txtApplicationdate = new JTextField(order != null ? order.getApplicationdate() : "");
        txtPaymentdate = new JTextField(order != null ? order.getPaymentdate() : "");
        txtPaid = new JTextField(order != null ? String.valueOf(order.getPaid()) : "");
        txtTotal = new JTextField();
        txtTotal.setEditable(false);

        JButton btnSave = new JButton("Thanh toán");
        JButton btnCancel = new JButton("Hủy");
        JButton btnTotal = new JButton("Tổng tiền");
        btnTotal.setBackground(Color.RED);
        btnTotal.setForeground(Color.WHITE);

        rightPanel.add(new JLabel("Người lập:"));
        rightPanel.add(txtFounder);
        rightPanel.add(new JLabel("Loại sản phẩm:"));
        rightPanel.add(cbType);
        rightPanel.add(new JLabel("Trạng thái:"));
        rightPanel.add(cbConditions);
        rightPanel.add(new JLabel("Ngày lập đơn:"));
        rightPanel.add(txtApplicationdate);
        rightPanel.add(new JLabel("Ngày thanh toán:"));
        rightPanel.add(txtPaymentdate);
        rightPanel.add(new JLabel("Đã thanh toán:"));
        rightPanel.add(txtPaid);
        rightPanel.add(new JLabel("Tổng tiền:"));
        rightPanel.add(txtTotal);
        rightPanel.add(btnSave);
        rightPanel.add(btnCancel);
        rightPanel.add(btnTotal);

        // Add panels to frame
        add(leftPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Action listeners
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(this::handleSave);
        btnTotal.addActionListener(e -> handleTotal(orderProductView));
    }

    private void handleSave(ActionEvent e) {
        try {
            if (order == null) {
                if (txtFounder.getText().isEmpty() || txtApplicationdate.getText().isEmpty() || txtPaymentdate.getText().isEmpty() ||
                        txtPaid.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Order newOrder = new Order(0, txtFounder.getText(), (String) cbType.getSelectedItem(),
                            (String) cbConditions.getSelectedItem(), txtApplicationdate.getText(), txtPaymentdate.getText(), Integer.parseInt(txtPaid.getText()));

                    orderController.add(newOrder);
                    tableModel.addRow(new Object[]{newOrder.getId(), newOrder.getFounder(), newOrder.getType(),
                            newOrder.getConditions(), newOrder.getApplicationdate(), newOrder.getPaymentdate(), newOrder.getPaid()});

                    OrderDao orderDao = new OrderDao();
                    int res = orderDao.insert(newOrder);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Đơn hàng được tạo thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                        loadOrderData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thanh toán", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                if (txtFounder.getText().isEmpty() || txtApplicationdate.getText().isEmpty() || txtPaymentdate.getText().isEmpty() ||
                        txtPaid.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    order.setFounder(txtFounder.getText());
                    order.setType((String) cbType.getSelectedItem());
                    order.setConditions((String) cbConditions.getSelectedItem());
                    order.setApplicationdate(txtApplicationdate.getText());
                    order.setPaymentdate(txtPaymentdate.getText());
                    order.setPaid(Integer.parseInt(txtPaid.getText()));

                    orderController.update(order.getId(), order);
                    OrderDao orderDao = new OrderDao();
                    int res = orderDao.update(order);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Sửa thành công", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        clearForm();
                        loadOrderData();
                    } else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }

                    int selectedRow = table.getSelectedRow();
                    tableModel.setValueAt(order.getFounder(), selectedRow, 1);
                    tableModel.setValueAt(order.getType(), selectedRow, 2);
                    tableModel.setValueAt(order.getConditions(), selectedRow, 3);
                    tableModel.setValueAt(order.getApplicationdate(), selectedRow, 4);
                    tableModel.setValueAt(order.getPaymentdate(), selectedRow, 5);
                    tableModel.setValueAt(order.getPaid(), selectedRow, 6);

                    // Check conditions for special message
                    if (!txtPaid.getText().isEmpty()) {
                        try {
                            int paidAmount = Integer.parseInt(txtPaid.getText());
                            if (paidAmount > 50000000 && "Sản phẩm 3".equals(cbType.getSelectedItem())) {
                                lblMessage.setText("<html>Bạn đã bán được một sản phẩm vàng hơn 50,000,000, Chúc mừng bạn!<br>Hãy đến phòng Giám đốc để nhận phần thưởng ^^</html>");
                            } else {
                                lblMessage.setText("");
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Giá trị đã thanh toán không hợp lệ, hãy nhập số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xảy ra: " + ex.getMessage(), "Thất bại", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá trị đã thanh toán không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleTotal(OrderProductView orderProductView) {
        
             
            JTable productTable = orderProductView.getTable();
            int selectedRow = productTable.getSelectedRow();

            if (selectedRow >= 0) {
                int quantity = Integer.parseInt(productTable.getValueAt(selectedRow, 4).toString());
                int price = Integer.parseInt(productTable.getValueAt(selectedRow, 5).toString());
                int total = quantity * price;
                txtTotal.setText(String.valueOf(total));
                
            	}
            
            else {
        try {	
            String paidText = txtPaid.getText();
            int paidAmount = Integer.parseInt(paidText);
            txtTotal.setText(String.valueOf(paidAmount));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá trị không hợp lệ, hãy nhập số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một dòng để tính tổng tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        
        }
    }

    private void clearForm() {
        txtFounder.setText("");
        cbType.setSelectedIndex(0);
        cbConditions.setSelectedIndex(0);
        txtApplicationdate.setText("");
        txtPaymentdate.setText("");
    }

    private void loadOrderData() throws SQLException {
        OrderDao orderDao = new OrderDao();
        List<Order> orders = orderDao.getAll();
        String[][] data = new String[orders.size()][7];

        for (int i = 0; i < orders.size(); i++) {
            data[i][0] = String.valueOf(orders.get(i).getId());
            data[i][1] = orders.get(i).getFounder();
            data[i][2] = orders.get(i).getType();
            data[i][3] = orders.get(i).getConditions();
            data[i][4] = orders.get(i).getApplicationdate();
            data[i][5] = orders.get(i).getPaymentdate();
            data[i][6] = String.valueOf(orders.get(i).getPaid());
        }

        String[] columnNames = {"ID", "Người lập", "Loại sản phẩm", "Trạng thái", "Ngày lập đơn", "Ngày thanh toán", "Đã thanh toán"};
        tableModel = new DefaultTableModel(data, columnNames);
        table.setModel(tableModel);
    }
}
