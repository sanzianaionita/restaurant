package com.example.restaurant18.DAO;

import com.example.restaurant18.entity.Product;
import com.example.restaurant18.enums.ProductType;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductDAO {

    private static Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createProduct(Product product){
        String insertQuery = "insert into product (product_name, product_description, product_price, product_quantity, product_type) " +
                "values (?,?,?,?,?)";
        String findProductQuery = "select product_name from product where product_name=?";
        try {
            PreparedStatement findProductStatement = connection.prepareStatement(findProductQuery);
            findProductStatement.setString(1, product.getProductName());
            ResultSet resultSet = findProductStatement.executeQuery();

            if (!resultSet.next()) {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                insertStatement.setString(1, product.getProductName());
                insertStatement.setString(2, product.getProductDescription());
                insertStatement.setDouble(3, product.getProductPrice());
                insertStatement.setInt(4, product.getProductQuantity());
                insertStatement.setString(5, product.getProductType());

                insertStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProducts() {
        String insertQuery = "insert into product (product_name, product_description, product_price, product_quantity, product_type) " +
                "values (?,?,?,?,?)";

        String findProductQuery = "select product_name from product where product_name=?";

        List<Product> products = generateInitialListOfProducts();

        products.forEach(product -> {
            try {
                PreparedStatement findProductStatement = connection.prepareStatement(findProductQuery);
                findProductStatement.setString(1, product.getProductName());
                ResultSet resultSet = findProductStatement.executeQuery();

                if (!resultSet.next()) {
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);

                    insertStatement.setString(1, product.getProductName());
                    insertStatement.setString(2, product.getProductDescription());
                    insertStatement.setDouble(3, product.getProductPrice());
                    insertStatement.setInt(4, product.getProductQuantity());
                    insertStatement.setString(5, product.getProductType());

                    insertStatement.executeUpdate();
                    connection.commit();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    public static ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> productsByType = new ArrayList<>();
        String query = "SELECT * FROM product";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Product product = new Product(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getString(6));

            productsByType.add(product);
        }
        return productsByType;
    }

    public static ArrayList<Product> getProductsByProductType(String productType) throws SQLException {
        ArrayList<Product> productsByType = new ArrayList<>();
        String query = "SELECT * FROM product WHERE product_type = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, productType);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Product product = new Product(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getString(6));

            productsByType.add(product);
        }
        return productsByType;
    }

    public Product getProductById(int id) throws SQLException {

        String query = "select * from product where id=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Product product = new Product(resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getString(6));
            return product;
        }
        return null;

    }

    public Product updateProductById(int id, Product product) throws SQLException {

        Product productById = getProductById(id);

        if (productById != null) {
            String query = "update product set id=?, product_name=?, product_description=?, product_price=?, product_quantity=?, product_type=? " +
                    "where id=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getProductDescription());
            statement.setDouble(4, product.getProductPrice());
            statement.setInt(5, product.getProductQuantity());
            statement.setString(6, product.getProductType());
            statement.setInt(7, id);

            statement.executeUpdate();
            connection.commit();

            return getProductById(id);
        }
        return null;

    }

    public void deleteProductById(int id) throws SQLException {

        Product productById = getProductById(id);

        if (productById != null) {
            String query = "delete from product where id=?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            statement.executeUpdate();
            connection.commit();

        }
    }
/*
    public void main(String[] args) {
        List<Product> products = generateInitialListOfProducts();
        for (Product product:
             products) {
            createProduct(product);
        }
    }
*/
    private List<Product> generateInitialListOfProducts() {

        Product product1 = new Product("Pizza Margherita", "sos de rosii si mozarella", 38.00, 25, ProductType.PIZZA.getCode());
        Product product2 = new Product("Pizza Tonno", "sos de rosii, mozarella, ton, ceapa", 42.00, 25, ProductType.PIZZA.getCode());
        Product product3 = new Product("Tortellini al Forno", "tortellini, șuncă, sos roze, ciuperci, usturoi, mozzarella, parmezan, gratinate", 38.00, 25, ProductType.PASTE.getCode());
        Product product4 = new Product("Penne Alfredo al Forno", "penne, pui, cremă vegetală pentru gătit, parmezan, pătrunjel, mozzarella, gratinate", 40.00, 25, ProductType.PASTE.getCode());
        Product product5 = new Product("Șnițel", "piept de pui, pesmet panko, ou, deasupra servit cu roșii cuburi, ceapă, usturoi si oregano", 40.00, 25, ProductType.CARNE.getCode());
        Product product6 = new Product("Tigaie picantă", "piept de pui fâșii, ardei gras, ceapă, ciuperci, rosii cherry, ardei iute, usturoi", 36.00, 25, ProductType.CARNE.getCode());
        Product product7 = new Product("Salată Cesar", "salată verde, piept de pui la grătar, parmezan, crutoane, dressing Cesare", 42.00, 25, ProductType.SALATE.getCode());
        Product product8 = new Product("Salată specială cu ton", "ton, maioneză, capere, lămâie, ceapă, focaccia", 35.00, 25, ProductType.SALATE.getCode());
        Product product9 = new Product("Fresh portocale", "100% natural", 18.00, 25, ProductType.BAUTURI.getCode());
        Product product10 = new Product("Limonadă home-made", "Apă plată/minerală, miere, mentă", 18.00, 25, ProductType.BAUTURI.getCode());

        return Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);
    }

}


