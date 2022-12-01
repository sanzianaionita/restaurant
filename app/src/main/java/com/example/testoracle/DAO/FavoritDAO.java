package com.example.testoracle.DAO;

import com.example.testoracle.entity.Favorit;
import com.example.testoracle.entity.Order;
import com.example.testoracle.entity.Product;
import com.example.testoracle.entity.User;

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
}
