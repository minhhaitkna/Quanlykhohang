package views ;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import controllers.ProductController ;
import dao.ProductDao;
import models.Product;
import java.util.List;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

public class EditProductView extends JFrame {
    private JTextField txtId, txtName, txtDescription, txtImageLink, txtQuantity, txtPrice, txtDate;
    private JComboBox<String> cbUnit;
    private ProductController productController;
    private Product product;
    private DefaultTableModel tableModel;
    private JTable table; // Thêm biến bảng JTable
    private int selectedProductId = -1;

    
    public EditProductView () throws SQLException {
    	loadProductData();
    }
    private void clearForm(){
        txtName.setText("");
        txtDescription.setText("");
        txtImageLink.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        txtDate.setText("");

    }
    
    private void loadProductData() throws SQLException{
        ProductDao productDao = new ProductDao();
        List<Product> product = productDao.getAll();
        String[][] data = new String[product.size()][7];
        
        for(int i=0; i < product.size(); i++){
           data[i][0] = String.valueOf(product.get(i).getId());
           data[i][1] = product.get(i).getName();
           data[i][2] = product.get(i).getDescription();
           data[i][3] = product.get(i).getImageLink();
           data[i][4] = String.valueOf(product.get(i).getQuantity());
           data[i][5] = String.valueOf(product.get(i).getPrice());
           data[i][6] = product.get(i).getDate();

        }
        String[] columnName = {"ID", "Tên sản phẩm", "Mô tả", "Link ảnh", "Số lượng", "Giá", "Ngày nhập" };
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
        
        
        
    }
    
   

    public EditProductView(ProductController productController, Product product, DefaultTableModel tableModel, JTable table) {
        this.productController = productController;
        this.product = product;
        this.tableModel = tableModel;
        this.table = table; // Gán bảng JTable từ MainView

        // Frame settings
        setTitle(product == null ? "Thêm sản phẩm" : "Sửa sản phẩm - " + product.getId());
        setSize(400, 400);
        setLayout(new GridLayout(8, 2, 10, 10));
        setLocationRelativeTo(null);

        // Form fields
        txtName = new JTextField(product != null ? product.getName() : "");
        txtDescription = new JTextField(product != null ? product.getDescription() : "");
        txtImageLink = new JTextField(product != null ? product.getImageLink() : "");
        txtQuantity = new JTextField(product != null ? String.valueOf(product.getQuantity()) : "");
        txtPrice = new JTextField(product != null ? String.valueOf(product.getPrice()) : "");
        txtDate = new JTextField(product != null ? product.getDate() : "");
        cbUnit = new JComboBox<>(new String[]{"Cái", "Ly", "Phần", "Cốc"});

        // Buttons
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");
        JButton btnChooseImage = new JButton("Chọn ảnh");


        // Add components
        
        add(new JLabel("Tên sản phẩm:"));
        add(txtName);
        add(new JLabel("Mô tả:"));
        add(txtDescription);
        
        add(new JLabel("Link ảnh:"));
        add(txtImageLink);
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(txtImageLink, BorderLayout.CENTER);
        imagePanel.add(btnChooseImage, BorderLayout.EAST);
        add(imagePanel);

        add(new JLabel("Số lượng:"));
        add(txtQuantity);
        add(new JLabel("Giá:"));
        add(txtPrice);
        add(new JLabel("Ngày nhập:"));
        add(txtDate);
        add(btnSave);
        add(btnCancel);
        
        
        // Action listeners
        btnChooseImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseImageView chooseImageView = new ChooseImageView();
                int returnValue = chooseImageView.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooseImageView.getSelectedFile();
                    txtImageLink.setText(selectedFile.getAbsolutePath());
                }
            }
        });
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            if (product == null) {
            	if(txtName.getText().isEmpty() || txtDescription.getText().isEmpty() || txtImageLink.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtPrice.getText().isEmpty() || txtPrice.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            	else {
                Product newProduct = new Product(0,txtName.getText(), txtDescription.getText(),
                        txtImageLink.getText(), Integer.parseInt(txtQuantity.getText()),
                        Integer.parseInt(txtPrice.getText()), txtDate.getText());
                
                
                productController.add(newProduct);
                tableModel.addRow(new Object[]{newProduct.getId(), newProduct.getName(), newProduct.getDescription(),
                        newProduct.getImageLink(), newProduct.getQuantity(), newProduct.getPrice(), newProduct.getDate()});
                try {
                    ProductDao productDao = new ProductDao();
                    int res = productDao.insert(newProduct);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm được tạo thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadProductData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            	}  
            }
            
        
             else {
            	 if(txtName.getText().isEmpty() || txtDescription.getText().isEmpty() || txtImageLink.getText().isEmpty() || txtQuantity.getText().isEmpty() || txtPrice.getText().isEmpty() || txtPrice.getText().isEmpty() ){
                     JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            	 else {
                product.setName(txtName.getText());
                product.setDescription(txtDescription.getText());
                product.setImageLink(txtImageLink.getText());
                product.setQuantity(Integer.parseInt(txtQuantity.getText()));
                product.setPrice(Integer.parseInt(txtPrice.getText()));
                product.setDate(txtDate.getText());
               
                productController.update(product.getId(), product);
                
                try {
                    ProductDao productDao = new ProductDao();
                    int res = productDao.update(product);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm được sửa thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadProductData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa 2 ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            
               
            
               
                int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn từ JTable
                tableModel.setValueAt(product.getName(), selectedRow, 1);
                tableModel.setValueAt(product.getDescription(), selectedRow, 2);
                tableModel.setValueAt(product.getImageLink(), selectedRow, 3);
                tableModel.setValueAt(product.getQuantity(), selectedRow, 4);
                tableModel.setValueAt(product.getPrice(), selectedRow, 5);
                tableModel.setValueAt(product.getDate(), selectedRow, 6);
            	}
            	 }
             
            dispose();
        });

    }
}