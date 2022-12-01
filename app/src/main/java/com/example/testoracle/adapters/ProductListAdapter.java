package com.example.testoracle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testoracle.R;
import com.example.testoracle.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListAdapter extends ArrayAdapter<Product> {
    Map<String, Boolean> favouriteItems = new HashMap<>();
    ImageButton buttonAddToCart;

    public ProductListAdapter(Context context, List<Product> productTypes) {
        super(context, 0, productTypes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product item = getItem(position);
        favouriteItems.putIfAbsent(item.getProductName(), false);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_list_view, parent, false);
        }

        TextView productNameText = convertView.findViewById(R.id.product_name);
        TextView productDescriptionText = convertView.findViewById(R.id.product_description);
        TextView productPriceText = convertView.findViewById(R.id.product_price);

        productNameText.setText(item.getProductName());
        productDescriptionText.setText(item.getProductDescription());
        productPriceText.setText(String.format("%s LEI ", item.getProductPrice()));

        ImageButton favouriteImageButton = convertView.findViewById(R.id.favouriteImageButton);

        favouriteImageButton.setOnClickListener(insideView -> {
            if (favouriteItems.containsKey(item.getProductName())) {
                if (favouriteItems.get(item.getProductName())) {
                    favouriteImageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    favouriteItems.put(item.getProductName(), false);
                } else {
                    favouriteImageButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                    favouriteItems.put(item.getProductName(), true);
                }
            }
        });

        buttonAddToCart = convertView.findViewById(R.id.cartImageButton);

        buttonAddToCart.setOnClickListener(view -> {

        });
        return convertView;
    }


    @Nullable
    @Override
    public Product getItem(int position) {
        return super.getItem(position);
    }


}
