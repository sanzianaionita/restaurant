package com.example.testoracle.activities.products;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.testoracle.DAO.ProductDAO;
import com.example.testoracle.R;
import com.example.testoracle.adapters.ProductTypeAdapter;
import com.example.testoracle.enums.ProductType;
import com.example.testoracle.utils.DatabaseHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class ProductTypeActivity extends AppCompatActivity {
    GridView productTypesGridView;
    CardView productTypeListCards;

    private Connection connection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_type_activity);

        initComponents();

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        try {
            connection = DatabaseHandler.createDbConn();

            populateProductTable();
            List<String> allProductTypes = getAllProductTypes();

            inflateProductTypeView(allProductTypes);

            productTypesGridView.setOnItemClickListener((adapterView, view, i, l) -> {
                ProductTypeAdapter adapter = (ProductTypeAdapter) adapterView.getAdapter();
                String productType = adapter.getItem(i);

                Intent intent = new Intent(this, ProductActivity.class);
                intent.putExtra("productType", productType);
                startActivity(intent);
            });

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                stringBuffer.append(resultSet.getString(1) + " "
                        + resultSet.getString(2) + " "
                        + resultSet.getString(3) + " "
                        + resultSet.getString(4) + " "
                        + resultSet.getString(5) + "\n");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateProductTable() {

        ProductDAO productDAO = new ProductDAO(connection);

        productDAO.createProducts();
    }

    public List<String> getAllProductTypes() {
        List<ProductType> enumValues = new ArrayList<>(EnumSet.allOf(ProductType.class));

        return enumValues.stream().map(ProductType::getCode).collect(Collectors.toList());

    }

    private void inflateProductTypeView(List<String> productTypeList) {
        ProductTypeAdapter productTypeAdapter = new ProductTypeAdapter(this, productTypeList);

        productTypesGridView.setAdapter(productTypeAdapter);
    }

    private void initComponents() {
        productTypesGridView = findViewById(R.id.productTypeListView);
        productTypeListCards = findViewById(R.id.productTypeListCard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.header, menu);

        return true;
    }

}
