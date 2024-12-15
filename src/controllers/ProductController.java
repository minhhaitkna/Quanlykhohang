package controllers ;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import models.Product;

public class ProductController {
    private List<Product> productList;

    public ProductController(List<Product> productList) {
		this.productList = productList;
	}

	public ProductController() {
        productList = new ArrayList<>();
    }

    // Add a new product
    public void addProduct(Product product) {
        productList.add(product);
    }

    // Update an existing product
    public void updateProduct(int id, Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId()==id) {
                productList.set(i, updatedProduct);
                break;
            }
        }
    }

    // Delete a product by ID
    public void deleteProduct(int id) {
        productList.removeIf(product -> product.getId()==id);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productList;
    }
}
