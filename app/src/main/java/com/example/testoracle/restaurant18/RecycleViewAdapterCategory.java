package com.example.restaurant18;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleViewAdapterCategory extends RecyclerView.Adapter<RecycleViewAdapterCategory.ViewHolder> {

    public ArrayList<Category> categoryArrayList;
    public RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public RecycleViewAdapterCategory(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewCategoryLogo;
        public View viewCategorySeparator;
        public TextView textViewCategoryName;
        public RecyclerView recyclerViewProductList;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageViewCategoryLogo = view.findViewById(R.id.iv_categoryLogo);
            viewCategorySeparator = view.findViewById(R.id.v_categorySeparator);
            textViewCategoryName = view.findViewById(R.id.tv_categoryName);
            recyclerViewProductList = view.findViewById(R.id.rv_productList);
        }
    }

    /*class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewCategoryLogo;
        public View viewCategorySeparator;
        public TextView textViewCategoryName;
        public RecyclerView recyclerViewProductList;

        ItemViewHolder(View itemView) {
            super(itemView);
            imageViewCategoryLogo = itemView.findViewById(R.id.iv_categoryLogo);
            viewCategorySeparator = itemView.findViewById(R.id.v_categorySeparator);
            textViewCategoryName = itemView.findViewById(R.id.tv_categoryName);
            recyclerViewProductList = itemView.findViewById(R.id.rv_productList);
        }
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Category category = categoryArrayList.get(position);
        viewHolder.imageViewCategoryLogo.setImageResource(R.mipmap.png_logo);
        viewHolder.textViewCategoryName.setText(category.getCategoryName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                viewHolder.recyclerViewProductList.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(category.getProductArrayList().size());

        // Create sub item view adapter
        RecycleViewAdapterProduct recycleViewAdapterProduct = new RecycleViewAdapterProduct(category.getProductArrayList());

        viewHolder.recyclerViewProductList.setLayoutManager(layoutManager);
        viewHolder.recyclerViewProductList.setAdapter(recycleViewAdapterProduct);
        viewHolder.recyclerViewProductList.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

}
