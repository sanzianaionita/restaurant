package com.example.restaurant18.ui.favorite_products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant18.MainActivity;
import com.example.restaurant18.Product;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterFavoriteProducts;
import com.example.restaurant18.databinding.FragmentFavoriteProductsBinding;

import java.util.ArrayList;

public class FavoriteProductsFragment extends Fragment {

    private FragmentFavoriteProductsBinding binding;

    ArrayList<Product> favoriteProductsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteProductsViewModel homeViewModel =
                new ViewModelProvider(this).get(FavoriteProductsViewModel.class);

        binding = FragmentFavoriteProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        favoriteProductsList = ((MainActivity)getActivity()).getFavoriteProductsList();

        RecyclerView recyclerView = root.findViewById(R.id.rv_favoriteProductsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecycleViewAdapterFavoriteProducts recycleViewAdapterFavoriteProducts = new RecycleViewAdapterFavoriteProducts(favoriteProductsList);

        recyclerView.setAdapter(recycleViewAdapterFavoriteProducts);
        recyclerView.setLayoutManager(layoutManager);

        recycleViewAdapterFavoriteProducts.setOnItemClickListener(new RecycleViewAdapterFavoriteProducts.OnItemClickListner() {
            @Override
            public void deleteFavoriteProductOnClick(int position) {
                favoriteProductsList.remove(position);
                recycleViewAdapterFavoriteProducts.notifyItemRemoved(position);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}