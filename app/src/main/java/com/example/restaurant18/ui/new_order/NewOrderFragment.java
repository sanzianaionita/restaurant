package com.example.restaurant18.ui.new_order;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import java.sql.Connection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.restaurant18.DAO.FavoritDAO;
import com.example.restaurant18.DAO.OrderDAO;
import com.example.restaurant18.DAO.OrderProductDAO;
import com.example.restaurant18.DAO.ProductDAO;
import com.example.restaurant18.MainActivity;
import com.example.restaurant18.OrderComponent;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterAllProducts;
import com.example.restaurant18.databinding.FragmentNewOrderBinding;
import com.example.restaurant18.entity.Favorit;
import com.example.restaurant18.entity.Order;
import com.example.restaurant18.entity.User;
import com.example.restaurant18.enums.OrderStatus;
import com.example.restaurant18.utils.DatabaseHandler;
import com.google.android.material.snackbar.Snackbar;
import com.example.restaurant18.entity.Product;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewOrderFragment extends Fragment {

    private FragmentNewOrderBinding binding;
    double total;
    Dialog dialog;
    ArrayList<Product> productsList = new ArrayList<>();
    ArrayList<OrderComponent> orderProductsList = new ArrayList<>();
    Connection connection;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewOrderViewModel homeViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog = new Dialog(getContext());

        try {
            connection = DatabaseHandler.createDbConn();

            productsList = ProductDAO.getAllProducts(connection);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        total = 0.0;
        Button button = root.findViewById(R.id.b_orderContent);
        button.setText("No products selected");

        RecyclerView recyclerView = root.findViewById(R.id.rv_newOrderProductList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        RecycleViewAdapterAllProducts recycleViewAdapterAllProducts = new RecycleViewAdapterAllProducts(getContext(),
                productsList, new RecycleViewAdapterAllProducts.buttonsAdapterListener() {
            @Override
            public void addOnClick(View v, int position) {
                Product currentProduct = productsList.get(position);

                View view = recyclerView.getLayoutManager().findViewByPosition(position);
                TextView textViewQuantity = view.findViewById(R.id.tv_productQuantity);
                ImageView imageViewRemoveButton = view.findViewById(R.id.iv_removeButton);
                RelativeLayout relativeLayoutProductFrame = view.findViewById(R.id.rl_productFrame);

                int currentQuantity;
                currentQuantity = returnQuantityFromListOfCurrentProduct(orderProductsList, currentProduct);
                currentQuantity = currentQuantity+1;
                textViewQuantity.setText(String.valueOf(currentQuantity));

                total = total+currentProduct.getProductPrice();
                if(checkIfProductExistsInList(orderProductsList,currentProduct))
                {
                    updateProductQuantityInList(orderProductsList,currentProduct,currentQuantity);
                }
                else {
                    textViewQuantity.setVisibility(View.VISIBLE);
                    imageViewRemoveButton.setVisibility(View.VISIBLE);
                    relativeLayoutProductFrame.setBackgroundColor(getResources().getColor(R.color.colorCornsilk));
                    orderProductsList.add(new OrderComponent(currentProduct, 1));
                }

                button.setText("Order "+String.valueOf(returnNumberOfProductsFromList(orderProductsList)+" for "+total));
            }

            @Override
            public void removeOnClick(View v, int position) {
                Product currentProduct = productsList.get(position);

                View view = recyclerView.getLayoutManager().findViewByPosition(position);
                TextView textViewQuantity = view.findViewById(R.id.tv_productQuantity);
                ImageView imageViewRemoveButton = view.findViewById(R.id.iv_removeButton);
                RelativeLayout relativeLayoutProductFrame = view.findViewById(R.id.rl_productFrame);

                int currentQuantity;
                currentQuantity = returnQuantityFromListOfCurrentProduct(orderProductsList, currentProduct);
                currentQuantity = currentQuantity-1;
                textViewQuantity.setText(String.valueOf(currentQuantity));

                total = total-currentProduct.getProductPrice();
                total = Double.parseDouble(String.format("%.2f", total));

                if(currentQuantity==0)
                {
                    updateProductQuantityInList(orderProductsList,currentProduct,currentQuantity);
                    textViewQuantity.setVisibility(View.INVISIBLE);
                    imageViewRemoveButton.setVisibility(View.INVISIBLE);
                    relativeLayoutProductFrame.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    if(returnPositionToRemoveFromList(orderProductsList)>=0)
                        orderProductsList.remove(returnPositionToRemoveFromList(orderProductsList));
                }
                else
                    updateProductQuantityInList(orderProductsList,currentProduct,currentQuantity);

                if(orderProductsList.size()==0)
                    button.setText("No products selected");
                else
                    button.setText("Order "+returnNumberOfProductsFromList(orderProductsList)+" for "+total);

            }

            @Override
            public void itemLongClick(View v, int position){
                Product currentProduct = productsList.get(position);
                openFavoriteProductDialog(currentProduct);
                //Toast.makeText(getContext(), "Couldn't save your changes", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    connection = DatabaseHandler.createDbConn();

                    // prima data se insereaza comanda in baza de date
                    OrderDAO orderDAO = new OrderDAO(connection);

                    // main to find userId for order
                    MainActivity main = (MainActivity) getActivity();
                    User user = main.getUser();

                    // date for order
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String formattedDate = dateFormat.format(date);

                    Order order = new Order(user.getId(), formattedDate, OrderStatus.PLACED.getCode(),
                            "a random address", null);
                    orderDAO.createOrder(order);

                    connection.close();

                    // apoi inseram si in tabelul asociativ
                    connection = DatabaseHandler.createDbConn();

                    OrderProductDAO orderProductDAO = new OrderProductDAO(connection);
                    orderProductDAO.createOrderProduct(orderProductsList, order);

                    connection.close();


                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


        recyclerView.setAdapter(recycleViewAdapterAllProducts);
        recyclerView.setLayoutManager(layoutManager);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean checkIfProductExistsInList(ArrayList<OrderComponent> orderProductsList, Product product){
        boolean productExists = false;
        for (OrderComponent orderComponent: orderProductsList) {
            if(orderComponent.getOrderProduct().getProductName().equals(product.getProductName()))
                productExists = true;
        }
        return productExists;
    }

    public void updateProductQuantityInList(ArrayList<OrderComponent> orderProductsList, Product product, int productQuantity)
    {
        for (OrderComponent orderComponent: orderProductsList)
            if(orderComponent.getOrderProduct().getProductName().equals(product.getProductName()))
                orderComponent.setOrderProductQuantity(productQuantity);
    }

    public Integer returnQuantityFromListOfCurrentProduct(ArrayList<OrderComponent> orderProductsList, Product product)
    {
        int currentQuantity = 0;
        for (OrderComponent orderComponent: orderProductsList)
            if(orderComponent.getOrderProduct().getProductName().equals(product.getProductName()))
                currentQuantity = orderComponent.getOrderProductQuantity();
        return currentQuantity;
    }

    public Integer returnNumberOfProductsFromList(ArrayList<OrderComponent> orderProductsList){
        int numberOfProducts=0;
        for (OrderComponent orderComponent: orderProductsList)
            numberOfProducts = numberOfProducts+orderComponent.getOrderProductQuantity();
        return numberOfProducts;
    }

    public int returnPositionToRemoveFromList(ArrayList<OrderComponent> orderProductsList)
    {
        int positionToRemove = -1;
        for (OrderComponent orderComponent: orderProductsList)
            if(orderComponent.getOrderProductQuantity() == 0)
                positionToRemove = orderProductsList.indexOf(orderComponent);
        return positionToRemove;
    }

    public void openFavoriteProductDialog(Product product)
    {
        dialog.setContentView(R.layout.dialog_favorite_product);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView textFavoriteStore = dialog.findViewById(R.id.tv_dialogQuestion);
        String text = (String)textFavoriteStore.getText();
        textFavoriteStore.setText(text.replaceAll("_",product.getProductName()));

        Button button_yes = dialog.findViewById(R.id.b_dialogYes);
        Button button_no = dialog.findViewById(R.id.b_dialogNo);

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                if(main.checkIfGuestUser())
                    Toast.makeText(getContext(), "You can't add products to favorite products list as guest", Toast.LENGTH_SHORT).show();
                else
                    addSelectedProductToFavoriteProductsList(product);
            }
        });
        dialog.show();
    }

    private void addSelectedProductToFavoriteProductsList(Product selectedProduct)
    {
        try
        {
            Favorit auxFavorit;
            connection = DatabaseHandler.createDbConn();
            FavoritDAO favoritDAO = new FavoritDAO(connection);

            MainActivity main = (MainActivity) getActivity();
            User user = main.getUser();

            auxFavorit = favoritDAO.getFavoritByUserIdProductId(user.getId(), selectedProduct.getId());
            if(auxFavorit != null)
            {
                Toast.makeText(getContext(), "This product already exists in your favorite products list", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(favoritDAO.insertFavoriteProduct(user.getId(), selectedProduct.getId()))
                {
                    Toast.makeText(getContext(), "Product added to your favorite products list", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), "Couldn't add selected product into your favorite products list", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}