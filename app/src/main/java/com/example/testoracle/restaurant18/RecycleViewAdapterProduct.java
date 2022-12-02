package com.example.restaurant18;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapterProduct extends RecyclerView.Adapter<RecycleViewAdapterProduct.ViewHolder> {

    private ArrayList<Product> productArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewProductName;
        public TextView textViewProductDescription;
        public TextView textViewProductPrice;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewProductName = view.findViewById(R.id.tv_productName);
            textViewProductDescription = view.findViewById(R.id.tv_productDescription);
            textViewProductPrice = view.findViewById(R.id.tv_productPrice);
        }
    }

    public RecycleViewAdapterProduct(ArrayList<Product> list){
        productArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Product product = productArrayList.get(position);
        viewHolder.textViewProductName.setText(product.getProductName());
        viewHolder.textViewProductDescription.setText(product.getProductDescription());
        viewHolder.textViewProductPrice.setText(product.getProductPrice());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }
}
