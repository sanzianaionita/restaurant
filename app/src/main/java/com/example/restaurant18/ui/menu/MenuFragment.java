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
import com.example.restaurant18.Product;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterCategory;
import com.example.restaurant18.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel homeViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String desc = "Antricot de vită, ceapă roșie, sos tzatziki, castravete proaspăt, cașcaval Cheddar";


        RecyclerView recyclerView = root.findViewById(R.id.rv_categoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecycleViewAdapterCategory recycleViewAdapterCategory = new RecycleViewAdapterCategory(buildCategoryList());
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

    public ArrayList<Category> buildCategoryList() {

        ArrayList<Category> categoryArrayList = new ArrayList<>();
        for (int i=0; i<3; i++) {
            Category category = new Category("Burger Categoria "+i, buildProductList());
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }

    public ArrayList<Product> buildProductList() {

        ArrayList<Product> productArrayList = new ArrayList<>();
        String desc = "Antricot de vită, ceapă roșie, sos tzatziki, castravete proaspăt, cașcaval Cheddar";
        for (int i=0; i<8; i++) {
            Product product = new Product("Hamburger", desc, "55.99 Lei");
            productArrayList.add(product);
        }
        return productArrayList;
    }

}