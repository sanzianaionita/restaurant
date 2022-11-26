package com.example.testoracle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testoracle.R;

import java.util.List;
import java.util.Objects;

public class ProductTypeAdapter extends ArrayAdapter<String> {

    public ProductTypeAdapter(Context context, List<String> productTypes) {
        super(context, 0, productTypes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String string = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.productTypeItem_tf);
        textView.setText(string);
        ImageView imageView = convertView.findViewById(R.id.productTypeItem_image);
        switch (Objects.requireNonNull(string)) {
            case "PIZZA":
                imageView.setBackgroundResource(R.drawable.ic_pizza);
                break;
            case "PASTE":
                imageView.setBackgroundResource(R.drawable.ic_spaghetti);
                break;
            case "CARNE":
                imageView.setBackgroundResource(R.drawable.ic_chicken);
                break;
            case "SALATE":
                imageView.setBackgroundResource(R.drawable.ic_salad);
                break;
            case "BAUTURI":
                imageView.setBackgroundResource(R.drawable.ic_drink);
                break;
        }



        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }
}
