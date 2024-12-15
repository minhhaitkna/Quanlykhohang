package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Customer;


public class CustomerDao extends Dao<Customer>  {
    private Connection connection;
    public CustomerDao() throws SQLException{
        connection = DBConnection.getConnection();
        
    }
    
         @Override
        public int insert(Customer customer) throws SQLException {
        try{
            String sql = "INSERT INTO customer ( name, phonenumber, address, date, rank) VALUES(?,?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhonenumber());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getDate());
            stmt.setString(5, customer.getRank());

            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
        @Override
        public List<Customer> getAll() throws SQLException{
        List<Customer> customer = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM customer";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int Id = rs.getInt("id");
                String Name = rs.getString("name");
                String Phonenumber = rs.getString("phonenumber");
                String Address = rs.getString("address");
                String Date = rs.getString("date");
                String Rank = rs.getString("rank");

                
                customer.add(new Customer(Id, Name, Phonenumber, Address, Date, Rank));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return customer;
    }
    
    @Override
    public int update(Customer customer) throws SQLException{
        try{
            String sql = "UPDATE customer SET  name=?, phonenumber=?, address=?, date=? , rank=? WHERE id=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhonenumber());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getDate());
            stmt.setString(5, customer.getRank());
            stmt.setInt(6, customer.getId());


            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
            String query = "DELETE FROM customer WHERE id = ?"; // Cập nhật tên bảng nếu cần
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
      public Customer getById(int id) throws SQLException {
            Customer customer = null;
            try {
                String sql = "SELECT * FROM customer WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phonenumber = rs.getString("phonenumber");
                    String address = rs.getString("address");
                    String date = rs.getString("date");
                    String rank = rs.getString("rank");

                    customer = new Customer(id, name, phonenumber, address, date, rank);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return customer;
        }

		
		

		
        
    }
    
    

