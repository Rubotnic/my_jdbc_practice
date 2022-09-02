package data;

import model.Product;
import model.ShoppingCartItem;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemDAO {

    Product save(ShoppingCartItem cartItem);

    Optional<ShoppingCartItem> findById(int id);

    List<ShoppingCartItem> findAll();

    Optional<ShoppingCartItem> finByCartId(int cartId);

    Optional<Product> findByProductId(int productId);

    void delete (int id);
}
