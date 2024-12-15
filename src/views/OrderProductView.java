package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractCellEditor;
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
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import controllers.ProductController;
import dao.ProductDao;
import models.Product;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderProductView {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProductController productController;
    private int selectedProductId = -1; // Biến lưu trữ ID của sản phẩm được chọn
   
    

    

    
    private void loadProductData() throws SQLException {
        // Lấy dữ liệu từ cơ sở dữ liệu
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();

        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        // Thêm dữ liệu mới vào bảng
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getImageLink(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getDate()
            });
        }
    }

    public OrderProductView() throws SQLException {
        productController = new ProductController();

        // Tạo JPanel chính
        contentPanel = new JPanel(new BorderLayout());

        // Tạo cột cho bảng
        String[] columns = {"ID", "Tên sản phẩm", "Mô tả", "Link ảnh", "Số lượng", "Giá", "Ngày nhập"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        // Thêm sự kiện chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy ID từ cột đầu tiên của dòng được chọn
                    selectedProductId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                } else {
                    selectedProductId = -1; // Nếu không có dòng nào được chọn
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
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnEdit = createButtonWithIcon("Sửa", "src/resources/icons/edit.png");
        bottomPanel.add(btnEdit);

        
        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnShowAll);

        // Panel nút chức năng
        

        // Thêm thành phần vào contentPanel
        contentPanel.add(topPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Nạp dữ liệu ban đầu vào bảng
        loadProductData();
     // Thay đổi cột "Số lượng" thành nút + và - với số lượng
        

        // Xử lý nút tìm kiếm
        btnSearch.addActionListener(e -> {
            String searchId = txtSearch.getText().trim();
            if (!searchId.isEmpty()) {
                try {
                    int id = Integer.parseInt(searchId);
                    ProductDao productDao = new ProductDao(); // Tạo đối tượng ProductDao
                    Product product = productDao.getById(id); // Gọi phương thức tìm kiếm theo ID

                    if (product != null) {
                        // Hiển thị sản phẩm lên bảng
                        tableModel.setRowCount(0); // Xóa dữ liệu cũ
                        tableModel.addRow(new Object[]{
                            product.getId(), product.getName(), product.getDescription(),
                            product.getImageLink(), product.getQuantity(), product.getPrice(),
                            product.getDate()
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm với ID: " + id);
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
                loadProductData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + ex.getMessage());
            }
        });
        
        btnEdit.addActionListener(e -> {
            if (selectedProductId != -1) {
                try {
                    ProductDao productDao = new ProductDao(); // Kết nối cơ sở dữ liệu
                    Product selectedProduct = productDao.getById(selectedProductId); // Lấy sản phẩm theo ID

                    if (selectedProduct != null) {
                        // Mở giao diện EditProductView với sản phẩm được chọn
                        new EditProductView(productController, selectedProduct, tableModel, table).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm để sửa!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi tải thông tin sản phẩm: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để sửa!");
            }
        });
        
        
    }
    
    public JTable getTable() {
        return table;
    }

        // Nút thêm sản phẩm
       
    private void loadTableData(List<Product> products) {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);
        // Thêm dữ liệu mới vào bảng
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(), product.getName(), product.getDescription(),
                    product.getImageLink(), product.getQuantity(), product.getPrice(),
                    product.getDate()
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