package views ;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import controllers.CustomerController ;
import dao.CustomerDao;
import models.Customer;
import java.util.List;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditCustomerView extends JFrame {
    private JTextField txtId, txtName, txtPhonenumber, txtAddress, txtDate, txtRank;
    private JComboBox<String> cbUnit;
    private CustomerController cusotmerController;
    private Customer customer;
    private DefaultTableModel tableModel;
    private JTable table; // Thêm biến bảng JTable
    private int selectedCustomerId = -1;

    
    public EditCustomerView () throws SQLException {
    	loadCustomerData();
    }
    private void clearForm(){
        txtName.setText("");
        txtPhonenumber.setText("");
        txtAddress.setText("");
        txtDate.setText("");
        txtRank.setText("");

    }
    
    private void loadCustomerData() throws SQLException{
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customer = customerDao.getAll();
        String[][] data = new String[customer.size()][7];
        
        for(int i=0; i < customer.size(); i++){
           data[i][0] = String.valueOf(customer.get(i).getId());
           data[i][1] = customer.get(i).getName();
           data[i][2] = customer.get(i).getPhonenumber();
           data[i][3] = customer.get(i).getAddress();
           data[i][4] = customer.get(i).getDate();
           data[i][5] = customer.get(i).getRank();

        }
        String[] columnName = {"ID", "Tên khách hàng", "Số điện thoại", "Địa chỉ", "Ngày sinh", "Loại khách hàng" };
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
        
        
        
    }
    
   

    public EditCustomerView(CustomerController customerController, Customer cusotmer, DefaultTableModel tableModel, JTable table) {
        this.cusotmerController = customerController;
        this.customer = cusotmer;
        this.tableModel = tableModel;
        this.table = table; // Gán bảng JTable từ MainView

        // Frame settings
        setTitle(cusotmer == null ? "Thêm khách hàng" : "Sửa khách hàng - " + cusotmer.getId());
        setSize(400, 400);
        setLayout(new GridLayout(8, 2, 10, 10));
        setLocationRelativeTo(null);

        // Form fields
        txtName = new JTextField(cusotmer != null ? cusotmer.getName() : "");
        txtPhonenumber = new JTextField(cusotmer != null ? cusotmer.getPhonenumber() : "");
        txtAddress = new JTextField(cusotmer != null ? cusotmer.getAddress() : "");
        txtDate = new JTextField(cusotmer != null ? cusotmer.getDate() : "");
        txtRank = new JTextField(cusotmer != null ? cusotmer.getRank() : "");
        cbUnit = new JComboBox<>(new String[]{"Cái", "Ly", "Phần", "Cốc"});

        // Buttons
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        // Add components
        
        add(new JLabel("Tên khách hàng:"));
        add(txtName);
        add(new JLabel("Số điện thoại:"));
        add(txtPhonenumber);
        add(new JLabel("Địa chỉ:"));
        add(txtAddress);
        add(new JLabel("Ngày sinh:"));
        add(txtDate);
        add(new JLabel("Loại khách hàng  :"));
        add(txtRank);
        add(btnSave);
        add(btnCancel);
        
        
        // Action listeners
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            if (cusotmer == null) {
            	if(txtName.getText().isEmpty() || txtPhonenumber.getText().isEmpty() || txtAddress.getText().isEmpty() || txtDate.getText().isEmpty()  || txtRank.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            	else {
                Customer newCustomer = new Customer(0,txtName.getText(),txtPhonenumber.getText(),
                		txtAddress.getText(), txtDate.getText(),
                         txtRank.getText());
                
                
                customerController.addCustomer(newCustomer);
                tableModel.addRow(new Object[]{newCustomer.getId(), newCustomer.getName(), newCustomer.getPhonenumber(),
                        newCustomer.getAddress(), newCustomer.getDate(), newCustomer.getRank()});
                try {
                    CustomerDao customerDao = new CustomerDao();
                    int res = customerDao.insert(newCustomer);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Sản phẩm được tạo thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadCustomerData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            	}  
            }
            
        
             else {
            	 if(txtName.getText().isEmpty() || txtPhonenumber.getText().isEmpty() || txtAddress.getText().isEmpty() || txtDate.getText().isEmpty()  || txtRank.getText().isEmpty() ){
                     JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                 }
            	 else {
                cusotmer.setName(txtName.getText());
                cusotmer.setPhonenumber(txtPhonenumber.getText());
                cusotmer.setAddress(txtAddress.getText());
                cusotmer.setDate(txtDate.getText());
                cusotmer.setRank(txtRank.getText());
               
                customerController.updateCustomer(cusotmer.getId(), cusotmer);
                
                try {
                    CustomerDao customerDao = new CustomerDao();
                    int res = customerDao.update(cusotmer);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, " sửa thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadCustomerData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa 2 ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            
               
            
               
                int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn từ JTable
                tableModel.setValueAt(cusotmer.getName(), selectedRow, 1);
                tableModel.setValueAt(cusotmer.getPhonenumber(), selectedRow, 2);
                tableModel.setValueAt(cusotmer.getAddress(), selectedRow, 3);
                tableModel.setValueAt(cusotmer.getDate(), selectedRow, 4);
                tableModel.setValueAt(cusotmer.getRank(), selectedRow, 5);
            	}
            	 }
             
            dispose();
        });

    }
}