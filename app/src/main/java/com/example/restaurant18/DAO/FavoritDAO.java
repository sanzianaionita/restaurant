package com.example.restaurant18.DAO;

import com.example.restaurant18.entity.Favorit;
import com.example.restaurant18.entity.Product;
import com.example.restaurant18.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FavoritDAO {
    private Connection connection;

    public FavoritDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Favorit> getAllfav() throws SQLException {
        Statement statement = connection.createStatement();
        List<Favorit> getAllfav = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("select * from favorit");
        while (resultSet.next()) {
            //stringBuffer.append(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
        }
        return getAllfav;
    }

    public List<Favorit> getfavprod() throws SQLException {
        Statement statement = connection.createStatement();
        List<Favorit> getfavprod = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();
        ResultSet resultSet = statement.executeQuery("select p.product_name from product p, favorit f, utilizator u where u.id=1 and f.product_id=p.id");
        while (resultSet.next()) {
            //stringBuffer.append(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
        }
        return getfavprod;
    }

    public void createfav() {
        String insertQuery = "insert into favorit (product_id , user_id) " + "values (?,?)";
        //
        }


    public void deletefavprod(int id) throws SQLException {
        String query = "delete from favorit where id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(2, id);

        statement.executeUpdate();
        connection.commit();
    }

    public Favorit getFavoritByUserIdProductId(int idUser, int idProduct) throws SQLException
    {
        Favorit produsFavorit=null;
        String query = "SELECT * FROM favorit WHERE product_id = ? and user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idProduct);
        statement.setInt(2, idUser);

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next())
            produsFavorit = new Favorit(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
        return produsFavorit;
    }

    public ArrayList<Product> getAllFavoriteProductForUserId(int idUser) throws SQLException
    {
        ArrayList<Product> favoriteProductsList =new ArrayList<>();
        String query = "SELECT product.id, product.product_name, product.product_description, product.product_price, product.product_quantity, product.product_type\n" +
                "FROM product\n" +
                "INNER JOIN favorit ON favorit.product_id = product.id AND favorit.user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idUser);

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next())
        {
            Product product = new Product(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5),
                    resultSet.getString(6));

            favoriteProductsList.add(product);
        }
        return favoriteProductsList;
    }

    private int getLastID() throws SQLException {
        int id;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM favorit");
        resultSet.next();
        id = resultSet.getInt(1);
        id = id + 1;
        return id;
    }

    public boolean deleteFavoriteProductByUserIdProductId(int idUser, int idProduct) throws SQLException
    {
        String query = "delete from favorit where user_id = ? and product_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idUser);
        statement.setInt(2, idProduct);

        try {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertFavoriteProduct(int idUser, int idProduct) throws SQLException
    {
        String query = "insert into favorit values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        int id = getLastID();
        statement.setInt(1, id);
        statement.setInt(2, idProduct);
        statement.setInt(3, idUser);
        try
        {
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
