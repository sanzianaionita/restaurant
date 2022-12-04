package com.example.restaurant18.ui.favorite_products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant18.DAO.FavoritDAO;
import com.example.restaurant18.MainActivity;
import com.example.restaurant18.entity.Product;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterFavoriteProducts;
import com.example.restaurant18.databinding.FragmentFavoriteProductsBinding;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.utils.DatabaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class FavoriteProductsFragment extends Fragment {

    private FragmentFavoriteProductsBinding binding;
    Connection connection ;
    User user;

    ArrayList<Product> favoriteProductsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoriteProductsViewModel homeViewModel =
                new ViewModelProvider(this).get(FavoriteProductsViewModel.class);

        binding = FragmentFavoriteProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity main = (MainActivity) getActivity();
        user = main.getUser();
        favoriteProductsList = buildFavoriteProductsList(user);


        RecyclerView recyclerView = root.findViewById(R.id.rv_favoriteProductsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecycleViewAdapterFavoriteProducts recycleViewAdapterFavoriteProducts = new RecycleViewAdapterFavoriteProducts(favoriteProductsList);

        recyclerView.setAdapter(recycleViewAdapterFavoriteProducts);
        recyclerView.setLayoutManager(layoutManager);

        recycleViewAdapterFavoriteProducts.setOnItemClickListener(new RecycleViewAdapterFavoriteProducts.OnItemClickListner() {
            @Override
            public void deleteFavoriteProductOnClick(int position)
            {
                Product curenntProduct = favoriteProductsList.get(position);
                try
                {
                    connection = DatabaseHandler.createDbConn();
                    FavoritDAO favoritDAO = new FavoritDAO(connection);
                    if(favoritDAO.deleteFavoriteProductByUserIdProductId(user.getId(), curenntProduct.getId()))
                    {
                        favoriteProductsList.remove(position);
                        recycleViewAdapterFavoriteProducts.notifyItemRemoved(position);
                    }
                    else
                        Toast.makeText(getContext(), "Couldn't remove selected product from your favorite products list", Toast.LENGTH_SHORT).show();
                }
                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Couldn't remove selected product from your favorite products list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<Product> buildFavoriteProductsList(User user){

        ArrayList<Product> favoriteProductsArrayList = new ArrayList<>();
        try
        {
            connection = DatabaseHandler.createDbConn();
            FavoritDAO favoritDAO = new FavoritDAO(connection);
            favoriteProductsArrayList = favoritDAO.getAllFavoriteProductForUserId(user.getId());
            connection.close();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return favoriteProductsArrayList;
    }

}