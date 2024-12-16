package views;

import controllers.ProductController;
import dao.ProductDao;
import models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ProductView extends View<Product> {
    private JPanel contentPanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private ProductController productController;
    private int selectedProductId = -1;

    public ProductView() throws SQLException {
        productController = new ProductController();

        contentPanel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Tên sản phẩm", "Mô tả", "Link ảnh", "Số lượng", "Giá", "Ngày nhập"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                selectedProductId = selectedRow != -1 ? Integer.parseInt(table.getValueAt(selectedRow, 0).toString()) : -1;
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
                    ProductDao productDao = new ProductDao();
                    Product product = productDao.getById(id);
                    if (product != null) {
                        tableModel.setRowCount(0);
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
            selectedProductId = -1;
        });
    }

    @Override
    protected void loadData() throws SQLException {
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();
        tableModel.setRowCount(0);
        for (Product product : products) {
            tableModel.addRow(new Object[]{
                    product.getId(), product.getName(), product.getDescription(),
                    product.getImageLink(), product.getQuantity(), product.getPrice(),
                    product.getDate()
            });
        }
    }

    @Override
    protected void addEntity() {
        new EditProductView(productController, null, tableModel, table).setVisible(true);
    }

    @Override
    protected void editEntity() {
        if (selectedProductId != -1) {
            try {
                ProductDao productDao = new ProductDao();
                Product selectedProduct = productDao.getById(selectedProductId);
                if (selectedProduct != null) {
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
    }

    @Override
    protected void deleteEntity() {
        if (selectedProductId != -1) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Bạn có chắc chắn muốn xóa sản phẩm này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    ProductDao productDao = new ProductDao();
                    if (productDao.delete(selectedProductId)) {
                        JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công!");
                        loadData();
                    } else {
                        JOptionPane.showMessageDialog(null, "Không thể xóa sản phẩm. Vui lòng thử lại.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi khi xóa sản phẩm: " + ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm để xóa!");
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
