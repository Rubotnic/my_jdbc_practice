package data;

import model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    Product save(Product product);

    Optional<Product> findById(int id);

    List<Product> findAll();

    List<Product> findByName(String name);

    List<Product> findByPriceBetween(double low, double high);

    void delete (int id);
}
