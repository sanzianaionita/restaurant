package com.example.restaurant18.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant18.Category;
import com.example.restaurant18.DAO.ProductDAO;
import com.example.restaurant18.entity.Product;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterCategory;
import com.example.restaurant18.databinding.FragmentMenuBinding;
import com.example.restaurant18.enums.ProductType;
import com.example.restaurant18.utils.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    Connection connection = DatabaseHandler.createDbConn();

    public MenuFragment() throws SQLException, ClassNotFoundException {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MenuViewModel homeViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String desc = "Antricot de vită, ceapă roșie, sos tzatziki, castravete proaspăt, cașcaval Cheddar";


        RecyclerView recyclerView = root.findViewById(R.id.rv_categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecycleViewAdapterCategory recycleViewAdapterCategory = null;
        try {
            recycleViewAdapterCategory = new RecycleViewAdapterCategory(buildCategoryList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(recycleViewAdapterCategory);
        recyclerView.setLayoutManager(layoutManager);

        return root;




    /*
        ArrayList<Product> listProduse = new ArrayList<>();
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));
        listProduse.add(new Product("Hamburger", desc, "55.99"));


        RecyclerView recyclerView;
        RecycleViewAdapterProduct adapter;
        RecyclerView.LayoutManager layoutManager;

        recyclerView = root.findViewById(R.id.recycler_view_category_item);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RecycleViewAdapterProduct(listProduse);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);*/

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Category> buildCategoryList() throws SQLException {

        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for (ProductType productType :
                ProductType.values()) {
            Category category = new Category(productType.getCode(), buildProductList(productType));
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }

    public ArrayList<Product> buildProductList(ProductType productType) throws SQLException {

        ArrayList<Product> productArrayList = new ArrayList<>();
        ProductDAO productDAO = new ProductDAO(connection);
        productArrayList =  productDAO.getProductsByProductType(productType.getCode());

        return productArrayList;
    }

}