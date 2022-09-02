package jbdc;

import data.ShoppingCartDAO;
import model.Product;
import model.ShoppingCart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static sun.plugin.javascript.navig.JSType.URL;

public class ShoppingCartJdbcImpl extends AbstractDAO implements ShoppingCartDAO{

    @Override
    public ShoppingCart save(ShoppingCart cart) {

        final String CREATE = "INSERT INTO shopping_cart (id, last_Update, order_status, delivery_address, customer_reference) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet keySet = null;

        ShoppingCart result = null;

        try {connection = getConnection();

            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, cart.getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(cart.getLastUpdate()));
            preparedStatement.setString(3, cart.getOrderStatus());
            preparedStatement.setString(4, cart.getDeliveryAddress());
            preparedStatement.setString(5, cart.getCustomerReference());


            preparedStatement.execute();

            keySet = preparedStatement.getGeneratedKeys();

            while( keySet.next()){
                result = new ShoppingCart (keySet.getInt(1),

                        cart.getLastUpdate(),
                        cart.getOrderStatus(),
                        cart.getDeliveryAddress(),
                        cart.getCustomerReference());

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            closeAll(keySet, preparedStatement, connection);
        }
        return result;
    }



    @Override
    public Optional<ShoppingCart> findById(int id) {
        Optional<ShoppingCart> found = Optional.empty();

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM shopping_cart WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                found = Optional.of(mapToShoppingCart(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return found;
    }


    @Override
    public List<ShoppingCart> findAll() {

        final String FINDALL = "SELECT * FROM shopping_cart";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ShoppingCart> foundMatches = new ArrayList<>();

        try {connection = getConnection();
            preparedStatement = connection.prepareStatement(FINDALL);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                foundMatches.add(
                        new ShoppingCart(
                                resultSet.getInt("id"),
                                resultSet.getTimestamp("last_update").toLocalDateTime(),
                                resultSet.getString("order_status"),
                                resultSet.getString("delivery_address"),
                                resultSet.getString("customer_reference")

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
    public List<ShoppingCart> findByOrderStatus(String status) {
        return null;
    }

    @Override
    public List<ShoppingCart> findByReference(String customer) {
        return null;
    }



    @Override
    public int delete(int id) {

        String DELETE_BY_ID_QUERY = "DELETE FROM shopping_cart WHERE id = " + id;

        int rowsAffected = 0;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            rowsAffected= statement.executeUpdate(DELETE_BY_ID_QUERY);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
