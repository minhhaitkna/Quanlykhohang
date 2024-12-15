package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Product;


public class ProductDao extends Dao<Product>  {
    private Connection connection;
    public ProductDao() throws SQLException{
        connection = DBConnection.getConnection();
        
    }
    
         @Override
        public int insert(Product product) throws SQLException {
        try{
            String sql = "INSERT INTO product ( name, description, imagelink, quantity, price, date) VALUES(?,?,?,?,?,?)";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getImageLink());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getPrice());
            stmt.setString(6, product.getDate());

            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
        @Override
        public List<Product> getAll() throws SQLException{
        List<Product> product = new ArrayList<>();
        
        try{
            String sql = "SELECT * FROM product";
            PreparedStatement stmt =connection.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int Id = rs.getInt("id");
                String Name = rs.getString("name");
                String Description = rs.getString("description");
                String ImageLink = rs.getString("imagelink");
                int Quantity = rs.getInt("quantity");
                int Price = rs.getInt("price");
                String Date = rs.getString("date");

                
                product.add(new Product(Id, Name, Description, ImageLink, Quantity, Price, Date));
                
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return product;
    }
    
    @Override
    public int update(Product product) throws SQLException{
        try{
            String sql = "UPDATE product SET  name=?, description=?, imagelink=?, quantity=? , price=? , date=? WHERE id=?";
            PreparedStatement stmt =connection.prepareStatement(sql);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getImageLink());
            stmt.setInt(4, product.getQuantity());
            stmt.setInt(5, product.getPrice());
            stmt.setString(6, product.getDate());
            stmt.setInt(7, product.getId());


            stmt.executeUpdate();
            return 1;
            
        }catch(SQLException e){
              return 0;
        }
        
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
            String query = "DELETE FROM product WHERE id = ?"; // Cập nhật tên bảng nếu cần
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
      public Product getById(int id) throws SQLException {
            Product product = null;
            try {
                String sql = "SELECT * FROM product WHERE id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String imageLink = rs.getString("imagelink");
                    int quantity = rs.getInt("quantity");
                    int price = rs.getInt("price");
                    String date = rs.getString("date");

                    product = new Product(id, name, description, imageLink, quantity, price, date);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return product;
        }

		
		

		
        
    }