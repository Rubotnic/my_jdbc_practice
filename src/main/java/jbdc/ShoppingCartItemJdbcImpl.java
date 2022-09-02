package jbdc;

import data.ShoppingCartItemDAO;
import model.Product;
import model.ShoppingCartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCartItemJdbcImpl extends AbstractDAO implements ShoppingCartItemDAO{

    @Override
    public Product save(ShoppingCartItem cartItem) {

        final String CREATE = "INSERT INTO shopping_cart_item (id, amount, total_price, product_id, shopping_cart_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet keySet = null;

        Product result = null;

        try {connection = getConnection();

            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, cartItem.getId());
            preparedStatement.setInt(2, cartItem.getAmount());
            preparedStatement.setDouble(3, cartItem.getTotalPrice());
            preparedStatement.setInt(4, cartItem.getItem().getId());
            preparedStatement.setInt(5, cartItem.getCart().getId());

            preparedStatement.execute();

            keySet = preparedStatement.getGeneratedKeys();

            while( keySet.next()){
                result = new ShoppingCartItem(keySet.getInt(1),
                        cartItem.getAmount(),
                        cartItem.getTotalPrice(),
                        cartItem.getItem().getId(),
                        cartItem.getCart().getId()).getItem();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll(keySet,preparedStatement,connection);
        }
        return result;
    }



    @Override
    public Optional<ShoppingCartItem> findById(int id) {
        Optional<ShoppingCartItem> found = Optional.empty();

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shopping_cart_item WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                found = Optional.of(mapToShoppingCartItem(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return found;
    }

    @Override
    public List<ShoppingCartItem> findAll() {

        final String FINDALL = "SELECT * FROM shopping_cart_item";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ShoppingCartItem> foundMatches = new ArrayList<>();

        try {connection = getConnection();
            preparedStatement = connection.prepareStatement(FINDALL);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                foundMatches.add(
                        new ShoppingCartItem(
                                resultSet.getInt("id"),
                                resultSet.getInt("amount"),
                                resultSet.getDouble("totalPrice"),
                                resultSet.getInt("item"),
                                resultSet.getInt("cart")
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(resultSet,preparedStatement,connection);
        }
        return foundMatches;
    }



    @Override
    public Optional<ShoppingCartItem> finByCartId(int cartId) {

        Optional<ShoppingCartItem> found2 = Optional.empty();

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shopping_cart WHERE id = ?");
            preparedStatement.setInt(1, cartId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                found2 = Optional.of(mapToShoppingCartItem(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return found2;
    }


    @Override
    public Optional<Product> findByProductId(int productId) {

        Optional<Product> found3 = Optional.empty();

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                found3 = Optional.of(mapToProduct(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return found3;
    }

    @Override
    public void delete(int id) {

    }
}
