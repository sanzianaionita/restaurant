package com.example.restaurant18;

import static org.mockito.Mockito.when;

import com.example.restaurant18.DAO.ProductDAO;
import com.example.restaurant18.entity.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductTest {

    static ProductDAO productDAO;
    static Connection connection;

    @BeforeAll
    public static void setup() {

        connection = Mockito.mock(Connection.class);
        productDAO = new ProductDAO(connection);
    }

    @Test
    public void testCreateProduct_created() throws SQLException {
        String findProductQuery = "select product_name from product where product_name=?";
        String insertQuery = "insert into product (product_name, product_description, product_price, product_quantity, product_type) " +
                "values (?,?,?,?,?)";

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement preparedStatementMocked = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(findProductQuery)).thenReturn(preparedStatementMocked);
        when(preparedStatementMocked.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatementMocked);
        when(preparedStatementMocked.executeUpdate()).thenReturn(1);

        int productsModified = productDAO.createProduct(new Product(1, "test", "test", 123D, 10, "PASTE"));

        Assertions.assertTrue(productsModified > 0);
    }

    @Test
    public void testCreateProduct_notCreated() throws SQLException {

        String findProductQuery = "select product_name from product where product_name=?";
        String insertQuery = "insert into product (product_name, product_description, product_price, product_quantity, product_type) " +
                "values (?,?,?,?,?)";

        ResultSet resultSet = Mockito.mock(ResultSet.class);
        PreparedStatement preparedStatementMocked = Mockito.mock(PreparedStatement.class);

        when(connection.prepareStatement(findProductQuery)).thenReturn(preparedStatementMocked);
        when(preparedStatementMocked.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(connection.prepareStatement(insertQuery)).thenReturn(preparedStatementMocked);
        when(preparedStatementMocked.executeUpdate()).thenReturn(1);

        int productsModified = productDAO.createProduct(new Product(1, "test", "test", 123D, 10, "PASTE"));

        Assertions.assertEquals(0, productsModified);
    }
}
