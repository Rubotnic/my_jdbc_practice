package jbdc;

import model.Product;
import model.ShoppingCart;
import model.ShoppingCartItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO {

    public void closeAll(AutoCloseable...closeables){
        if (closeables != null) {
            for (AutoCloseable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseCredentials.getInstance().getURL(),
                DatabaseCredentials.getInstance().getUSER(),
                DatabaseCredentials.getInstance().getPASSWORD());
    }

    protected static Product mapToProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"));
    }

    protected static ShoppingCart mapToShoppingCart(ResultSet resultSet) throws SQLException {
        return new ShoppingCart(

                resultSet.getInt("id"),
                resultSet.getTimestamp("last_update").toLocalDateTime(),// timeDate?
                resultSet.getString("order_status"),
                resultSet.getString("delivery_address"),
                resultSet.getString("customer_reference"));

    }

    protected static ShoppingCartItem mapToShoppingCartItem(ResultSet resultSet) throws SQLException {
        return new ShoppingCartItem(

                resultSet.getInt("id"),
                resultSet.getInt("amount"),
                resultSet.getDouble("total_price"),
                resultSet.getInt("product_id"),
                resultSet.getInt("shopping_cart_id"));
    }
}
