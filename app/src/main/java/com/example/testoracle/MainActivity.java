package com.example.testoracle;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testoracle.DAO.OrderDAO;
import com.example.testoracle.DAO.UserDAO;
import com.example.testoracle.activities.products.ProductTypeActivity;
import com.example.testoracle.entity.Order;
import com.example.testoracle.entity.User;
import com.example.testoracle.enums.OrderStatus;
import com.example.testoracle.utils.DatabaseHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Connection connection;
    private Button buttonOrder;
    private Button buttonFav;
//    private ImageButton buttonaddfav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LayoutInflater inflater = getLayoutInflater();
//        View product_list_view = inflater.inflate(R.layout.product_list_view, null);
        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.navigateToProductTypeButton);
        buttonOrder = findViewById(R.id.testAddOrder);
        buttonFav = findViewById(R.id.testfav);
        //buttonaddfav = product_list_view.findViewById(favouriteImageButton);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);


        button.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProductTypeActivity.class);
            startActivity(intent);
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("test","test1993");
                try {
                    connection = DatabaseHandler.createDbConn();
                    OrderDAO orderDAO = new OrderDAO(connection);

                    orderDAO.createOrder(new Order(1, "25-03-2022", "preparing", "Str. V, nr. 5, bl. 7, sc. D, ap. 55", "sunati-ma ca sa cobor"));

                    Statement statement = connection.createStatement();
                    StringBuffer stringBuffer = new StringBuffer();
                    ResultSet resultSet = statement.executeQuery("select * from comanda");

                    while (resultSet.next()) {
                        stringBuffer.append(resultSet.getInt(1) + " " + resultSet.getInt(2) + " " + resultSet.getString(4)  + "\n");
                    }
                    textView.setText(stringBuffer.toString());

                } catch (Exception e) {
                    textView.setText(e.toString());
                }
            }
        });
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.v("buttonaddfav","buton de adaugat la fav");
                try {
                    connection = DatabaseHandler.createDbConn();
                    Statement statement = connection.createStatement();
                    StringBuffer stringBuffer = new StringBuffer();
                    ResultSet resultSet = statement.executeQuery("select p.product_name from product p, favorit f, utilizator u where u.id=1 and f.product_id=p.id");
                    while (resultSet.next()) {
                        stringBuffer.append(resultSet.getString(1) + "\n");
                    }
                    //textView.setText(stringBuffer.toString());
                } catch (Exception e) {
                    //textView.setText(e.toString());
                }
            }
        });

    }

    public void buttonAddUserTest(View view) {
        try {
            connection = DatabaseHandler.createDbConn();
            UserDAO userDAO = new UserDAO(connection);

            userDAO.createUser(new User("testttt", "test", "test", "testtt", "test", "25-03-2022"));

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select * from utilizator");

            while (resultSet.next()) {
                stringBuffer.append(resultSet.getString(1) + " " + resultSet.getString(2) + "\n");
            }
            textView.setText(stringBuffer.toString());

        } catch (Exception e) {
            textView.setText(e.toString());
        }
    }

}