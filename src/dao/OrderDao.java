package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Order;


public class OrderDao extends Dao<Order>  {
		private Connection connection;
		public OrderDao() throws SQLException{
			connection = DBConnection.getConnection();
        
    }
		
         @Override
        public int insert(Order order) throws SQLException {
        try{
            String sql = "INSERT INTO orderss (founder, type, conditions, applicationdate, paymentdate, paid) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, order.getFounder());
            stmt.setString(2, order.getType());
            stmt.setString(3, order.getConditions());
            stmt.setString(4, order.getApplicationdate());
            stmt.setString(5, order.getPaymentdate());
            stmt.setInt(6, order.getPaid());

            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
        @Override
        public List<Order> getAll() throws SQLException{
        List<Order> order = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM orderss";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int Id = rs.getInt("id");
                String Founder = rs.getString("founder");
                String Type = rs.getString("type");
                String Conditions = rs.getString("conditions");
                String Applicationdate = rs.getString("applicationdate");
                String Paymentdate = rs.getString("paymentdate");
                int Paid = rs.getInt("paid");

                
                order.add(new Order(Id , Founder, Type, Conditions, Applicationdate, Paymentdate, Paid));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return order;
    }
    
    @Override
    public int update(Order order) throws SQLException{
        try{
            String sql = "UPDATE orderss SET  founder=?, type=?, conditions=?, applicationdate=? , paymentdate=? , paid=? WHERE id=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, order.getFounder());
            stmt.setString(2, order.getType());
            stmt.setString(3, order.getConditions());
            stmt.setString(4, order.getApplicationdate());
            stmt.setString(5, order.getPaymentdate());
            stmt.setInt(6, order.getPaid());
            stmt.setInt(7, order.getId());


            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
            String query = "DELETE FROM orderss WHERE id = ?"; // Cập nhật tên bảng nếu cần
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
      public Order getById(int id) throws SQLException {
            Order order = null;
            try {
                String sql = "SELECT * FROM orderss WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String founder = rs.getString("founder");
                    String type = rs.getString("type");
                    String conditions = rs.getString("conditions");
                    String applicationdate = rs.getString("applicationdate");
                    String paymentdate = rs.getString("paymentdate");
                    int paid = rs.getInt("paid");

                    order = new Order(id, founder, type, conditions, applicationdate, paymentdate, paid);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return order;
        }
      
     
		
		

		
        
    }
    
    

