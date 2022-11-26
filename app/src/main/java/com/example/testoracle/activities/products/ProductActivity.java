package com.example.testoracle.activities.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testoracle.DAO.ProductDAO;
import com.example.testoracle.R;
import com.example.testoracle.adapters.ProductListAdapter;
import com.example.testoracle.entity.Product;
import com.example.testoracle.utils.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private GridView productGridView;

    private Connection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);
        try {
            connection = DatabaseHandler.createDbConn();
            initComponents();

            Intent intent = getIntent();
            String type = intent.getStringExtra("productType");
            if (type == null) {
                Toast.makeText(this, "Type missing", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
            List<Product> productsByProductType = getProductsByProductType(type);
            inflateProductTypeView(productsByProductType);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void inflateProductTypeView(List<Product> products) {
        ProductListAdapter productListAdapter = new ProductListAdapter(this, products);

        productGridView.setAdapter(productListAdapter);
    }

    private void initComponents() {
        productGridView = findViewById(R.id.productGridView);
    }

    private List<Product> getProductsByProductType(String type) throws SQLException {
        ProductDAO productDAO = new ProductDAO(connection);

        return productDAO.getProductsByProductType(type);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.header, menu);

        return true;
    }
}
