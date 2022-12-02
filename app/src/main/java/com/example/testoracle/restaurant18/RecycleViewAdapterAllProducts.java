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

public class RecycleViewAdapterAllProducts extends RecyclerView.Adapter<RecycleViewAdapterAllProducts.ViewHolder> {

    private Context context;
    public static buttonsAdapterListener onClickListener;
    private ArrayList<Product> productArrayList;

    public RecycleViewAdapterAllProducts(Context context, ArrayList<Product> productArrayList,
                                         buttonsAdapterListener listener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.onClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewAddButton;
        public ImageView imageViewRemoveButton;
        public ImageView imageViewProductImage;
        public TextView textViewProductName;
        public TextView textViewProductPrice;
        public TextView textViewProductQuantity;
        public RelativeLayout relativeLayoutProductFrame;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageViewAddButton = view.findViewById(R.id.iv_addButton);
            imageViewRemoveButton = view.findViewById(R.id.iv_removeButton);
            imageViewProductImage = view.findViewById(R.id.iv_productImage);
            textViewProductName = view.findViewById(R.id.tv_PlaceOrderProductName);
            textViewProductPrice = view.findViewById(R.id.tv_placeOrderProductPrice);
            textViewProductQuantity = view.findViewById(R.id.tv_productQuantity);
            relativeLayoutProductFrame = view.findViewById(R.id.rl_productFrame);

            imageViewAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.addOnClick(v, getAdapterPosition());
                }
            });
            imageViewRemoveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.removeOnClick(v, getAdapterPosition());
                }
            });
            relativeLayoutProductFrame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.itemLongClick(v, getAdapterPosition());
                    return true;
                }
            });

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_new_order_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        Product product = productArrayList.get(position);
        viewHolder.textViewProductName.setText(product.getProductName());
        viewHolder.textViewProductPrice.setText(product.getProductPrice());
        viewHolder.textViewProductQuantity.setText("0");
        viewHolder.textViewProductQuantity.setVisibility(View.INVISIBLE);
        viewHolder.imageViewRemoveButton.setVisibility(View.INVISIBLE);
        viewHolder.imageViewProductImage.setImageResource(R.mipmap.png_man);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public interface buttonsAdapterListener {

        void addOnClick(View v, int position);

        void removeOnClick(View v, int position);

        void itemLongClick(View v, int position);
    }

}
