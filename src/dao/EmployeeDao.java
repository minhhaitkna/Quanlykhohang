package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Employee;


public class EmployeeDao extends Dao<Employee>  {
		private Connection connection;
		public EmployeeDao() throws SQLException{
			connection = DBConnection.getConnection();
        
    }
		
         @Override
        public int insert(Employee employee) throws SQLException {
        try{
            String sql = "INSERT INTO employee ( name, salary, account, password, phone_number, rank) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setDouble(2, employee.getSalary());
            stmt.setString(3, employee.getAccount());
            stmt.setString(4, employee.getPassword());
            stmt.setString(5, employee.getPhone_number());
            stmt.setString(6, employee.getRank());

            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
        @Override
        public List<Employee> getAll() throws SQLException{
        List<Employee> employee = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM employee";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int Id = rs.getInt("id");
                String Name = rs.getString("name");
                Double Salary = rs.getDouble("salary");
                String Account = rs.getString("account");
                String Password = rs.getString("password");
                String Phone_number = rs.getString("phone_number");
                String Rank = rs.getString("rank");

                
                employee.add(new Employee(Id, Name, Salary, Account, Password, Phone_number, Rank));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return employee;
    }
    
    @Override
    public int update(Employee employee) throws SQLException{
        try{
            String sql = "UPDATE employee SET  name=?, salary=?, account=?, password=? , phone_number=? , rank=? WHERE id=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, employee.getName());
            stmt.setDouble(2, employee.getSalary());
            stmt.setString(3, employee.getAccount());
            stmt.setString(4, employee.getPassword());
            stmt.setString(5, employee.getPhone_number());
            stmt.setString(6, employee.getRank());
            stmt.setInt(7, employee.getId());


            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
            String query = "DELETE FROM employee WHERE id = ?"; // Cập nhật tên bảng nếu cần
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu có dòng bị xóa
            }
        }
    	
      @Override
        // Các phương thức khác trong ProductDao...
     // Tìm kiếm sản phẩm theo ID
      public Employee getById(int id) throws SQLException {
            Employee employee = null;
            try {
                String sql = "SELECT * FROM employee WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    double salary = rs.getDouble("salary");
                    String account = rs.getString("account");
                    String password = rs.getString("password");
                    String phone_number = rs.getString("phone_number");
                    String rank = rs.getString("rank");

                    employee = new Employee(id, name, salary, account, password, phone_number, rank);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return employee;
        }
      
     
		
		

		
        
    }
    
    

