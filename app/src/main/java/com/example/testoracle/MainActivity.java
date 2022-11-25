package com.example.testoracle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testoracle.DAO.UserDAO;
import com.example.testoracle.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  static final String URL = "jdbc:oracle:thin:@192.168.100.17:1521:XE";
    private static final String USERNAME = "raisa";
    private static final String PASSWORD = "Sasakisan";

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Toast.makeText(this, "CONNECTED", Toast.LENGTH_LONG).show();

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select * from utilizator");
            while (resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + " " + resultSet.getString(2) +"\n");
            }
            textView.setText(stringBuffer.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonAddUserTest(View view){
        try {

            UserDAO userDAO = new UserDAO(connection);

            userDAO.createUser(new User("testttt","test","test","testtt","test", "25-03-2022"));

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select * from utilizator");
            while (resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + " " + resultSet.getString(2) +"\n");
            }
            textView.setText(stringBuffer.toString());

        }
        catch (Exception e){
            textView.setText(e.toString());
        }
    }


}