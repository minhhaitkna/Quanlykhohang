package controllers ;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProductDao;
import models.Product;

public class ProductController extends Controller<Product> {
    private List<Product> productList;

    public ProductController(List<Product> productList) {
		this.productList = productList;
	}

	public ProductController() {
        productList = new ArrayList<>();
    }

    @Override
    public void add(Product product) {
        productList.add(product);
    }

    @Override
    public void update(int id, Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getId()==id) {
                productList.set(i, updatedProduct);
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        productList.removeIf(product -> product.getId()==id);
    }

    @Override
    public List<Product> getAll() {
        return productList;
    }
}
