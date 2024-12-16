package views ;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.TableView.TableRow;

import controllers.EmployeeController ;
import dao.EmployeeDao;
import models.Employee;
import java.util.List;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditEmployeeView extends JFrame {
    private JTextField txtId, txtName, txtSalary, txtAccount, txtPassword, txtPhone_number, txtRank;
    private JComboBox<String> cbUnit;
    private EmployeeController employeeController;
    private Employee employee;
    private DefaultTableModel tableModel;
    private JTable table; // Thêm biến bảng JTable
    private int selectedEmployeeId = -1;

    
    public EditEmployeeView () throws SQLException {
    	loadEmployeeData();
    }
    private void clearForm(){
        txtName.setText("");
        txtSalary.setText("");
        txtAccount.setText("");
        txtPassword.setText("");
        txtPhone_number.setText("");
        txtRank.setText("");

    }
    
    private void loadEmployeeData() throws SQLException{
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employee = employeeDao.getAll();
        String[][] data = new String[employee.size()][7];
        
        for(int i=0; i < employee.size(); i++){
           data[i][0] = String.valueOf(employee.get(i).getId());
           data[i][1] = employee.get(i).getName();
           data[i][2] = String.valueOf(employee.get(i).getSalary());
           data[i][3] = employee.get(i).getAccount();
           data[i][4] = employee.get(i).getPassword();
           data[i][5] = employee.get(i).getPhone_number();
           data[i][6] = employee.get(i).getRank();

        }
        String[] columnName = {"ID", "Tên nhân viên", "Lương", "Tài khoản", "Mật khẩu", "Số điện thoại", "Loại nhân viên" };
        table.setModel(new javax.swing.table.DefaultTableModel(data, columnName));
        
        
        
    }
    
   

    public EditEmployeeView(EmployeeController employeeController, Employee emolyee, DefaultTableModel tableModel, JTable table) {
        this.employeeController = employeeController;
        this.employee = emolyee;
        this.tableModel = tableModel;
        this.table = table; // Gán bảng JTable từ MainView

        // Frame settings
        setTitle(emolyee == null ? "Thêm nhân viên" : "Sửa nhân viên - " + emolyee.getId());
        setSize(400, 400);
        setLayout(new GridLayout(8, 2, 10, 10));
        setLocationRelativeTo(null);

        // Form fields
        txtName = new JTextField(emolyee != null ? emolyee.getName() : "");
        txtSalary = new JTextField(emolyee != null ? String.valueOf (emolyee.getSalary()) : "");
        txtAccount = new JTextField(emolyee != null ? emolyee.getAccount() : "");
        txtPassword = new JTextField(emolyee != null ? emolyee.getPassword() : "");
        txtPhone_number = new JTextField(emolyee != null ? emolyee.getPhone_number() : "");
        txtRank = new JTextField(emolyee != null ? emolyee.getRank() : "");
        cbUnit = new JComboBox<>(new String[]{"Cái", "Ly", "Phần", "Cốc"});

        // Buttons
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        // Add components
        
        add(new JLabel("Tên nhân viên:"));
        add(txtName);
        add(new JLabel("Lương:"));
        add(txtSalary);
        add(new JLabel("Tài khoản:"));
        add(txtAccount);
        add(new JLabel("Mật khẩu:"));
        add(txtPassword);
        add(new JLabel("Số điện thoại:"));
        add(txtPhone_number);
        add(new JLabel("loại nhân viên :"));
        add(txtRank);
        add(btnSave);
        add(btnCancel);
        
        
        // Action listeners
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> {
            if (emolyee == null) {
            	if(txtName.getText().isEmpty() || txtSalary.getText().isEmpty() || txtAccount.getText().isEmpty() || txtPassword.getText().isEmpty() || txtPhone_number.getText().isEmpty() || txtRank.getText().isEmpty() ){
                    JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            	else {
                Employee newEmployee = new Employee(0,txtName.getText(), Double.parseDouble(txtSalary.getText()),
                		txtAccount.getText(), txtPassword.getText(),
                        txtPhone_number.getText(), txtRank.getText());
                
                
                employeeController.add(newEmployee);
                tableModel.addRow(new Object[]{newEmployee.getId(), newEmployee.getName(), newEmployee.getSalary(),
                        newEmployee.getAccount(), newEmployee.getPassword(), newEmployee.getPhone_number(), newEmployee.getRank()});
                try {
                    EmployeeDao employeeDao = new EmployeeDao();
                    int res = employeeDao.insert(newEmployee);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, "Nhân viên được tạo thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadEmployeeData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi thêm  ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            	}  
            }
            
        
             else {
            	 if(txtName.getText().isEmpty() || txtSalary.getText().isEmpty() || txtAccount.getText().isEmpty() || txtPassword.getText().isEmpty() || txtPhone_number.getText().isEmpty() || txtRank.getText().isEmpty() ){
                     JOptionPane.showMessageDialog(this, "Hãy nhập đủ dữ liệu ", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            	 else {
                emolyee.setName(txtName.getText());
                emolyee.setSalary(Double.parseDouble(txtSalary.getText())) ;
                emolyee.setAccount(txtAccount.getText());
                emolyee.setPassword(txtPassword.getText());
                emolyee.setPhone_number(txtPhone_number.getText());
                emolyee.setRank(txtRank.getText());
               
                employeeController.update(emolyee.getId(), emolyee);
                
                try {
                    EmployeeDao employeeDao = new EmployeeDao();
                    int res = employeeDao.update(emolyee);
                    if (res == 1) {
                        JOptionPane.showMessageDialog(this, " sửa thành công ", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                        this.clearForm();
                        loadEmployeeData();
                    }else {
                        JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(this, "Lỗi xảy ra khi sửa 2 ", "Thất bại", JOptionPane.ERROR_MESSAGE);
                }
            
               
            
               
                int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn từ JTable
                tableModel.setValueAt(emolyee.getName(), selectedRow, 1);
                tableModel.setValueAt(emolyee.getSalary(), selectedRow, 2);
                tableModel.setValueAt(emolyee.getAccount(), selectedRow, 3);
                tableModel.setValueAt(emolyee.getPassword(), selectedRow, 4);
                tableModel.setValueAt(emolyee.getPhone_number(), selectedRow, 5);
                tableModel.setValueAt(emolyee.getRank(), selectedRow, 6);
            	}
            	 }
             
            dispose();
        });

    }
}