package com.example.restaurant18;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapterFavoriteProducts extends RecyclerView.Adapter<RecycleViewAdapterFavoriteProducts.ViewHolder> {

    private ArrayList<Product> favoriteProductsArrayList;
    private OnItemClickListner onItemClickListner;

    public RecycleViewAdapterFavoriteProducts(ArrayList<Product> productArrayList) {
        this.favoriteProductsArrayList = productArrayList;
    }

    public void setOnItemClickListener(OnItemClickListner listener)
    {
        this.onItemClickListner = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewDeleteButton;
        public ImageView imageViewProductImage;
        public TextView textViewProductName;
        public TextView textViewProductPrice;

        public ViewHolder(@NonNull View view, final OnItemClickListner onItemClickListner) {
            super(view);
            imageViewDeleteButton = view.findViewById(R.id.iv_deleteButton);
            imageViewProductImage = view.findViewById(R.id.iv_favoriteProductImage);
            textViewProductName = view.findViewById(R.id.tv_favoriteProductName);
            textViewProductPrice = view.findViewById(R.id.tv_favoriteProductPrice);

            imageViewDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListner != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            onItemClickListner.deleteFavoriteProductOnClick(position);
                        }
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite_product, parent, false);
        return new ViewHolder(view, onItemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        Product product = favoriteProductsArrayList.get(position);
        viewHolder.textViewProductName.setText(product.getProductName());
        viewHolder.textViewProductPrice.setText(product.getProductPrice());
        viewHolder.imageViewProductImage.setImageResource(R.mipmap.png_man);
    }

    @Override
    public int getItemCount() {
        return favoriteProductsArrayList.size();
    }

    public interface OnItemClickListner{
        void deleteFavoriteProductOnClick(int position);
    }

}
