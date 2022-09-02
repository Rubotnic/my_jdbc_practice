package jbdc;

import data.ProductDAO;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductJdbcImpl extends AbstractDAO implements ProductDAO {



    @Override
    public Product save(Product product) {

        final String CREATE = "INSERT INTO product (id, name, price) VALUES (?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet keySet = null;

        Product result = null;

        try {connection = getConnection();

            preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());

            preparedStatement.execute();

            keySet = preparedStatement.getGeneratedKeys();

            while(keySet.next()){
                result = new Product(keySet.getInt(1),
                        product.getName(),
                        product.getPrice());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }finally {
            closeAll(keySet, preparedStatement, connection);
        }
        return result;
    }


    @Override
    public Optional<Product> findById(int id) {
        Optional<Product> found = Optional.empty();

        try{Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                found = Optional.of(mapToProduct(resultSet));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return found;
    }


    @Override
    public List<Product> findAll() {

        List<Product> allProduct = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product");
             ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while (resultSet.next()){
                allProduct.add(mapToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProduct;
    }


    @Override
    public List<Product> findByName(String name) {

        List<Product> matchingName = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE name LIKE");
             ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while (resultSet.next()){
                matchingName.add(mapToProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchingName;
    }

    @Override
    public List<Product> findByPriceBetween(double low, double high) {
        return null;
    }

    @Override
    public void delete(int id) {
    }
}

