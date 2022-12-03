package com.example.restaurant18.ui.new_order;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.restaurant18.MainActivity;
import com.example.restaurant18.OrderComponent;
import com.example.restaurant18.Product;
import com.example.restaurant18.R;
import com.example.restaurant18.RecycleViewAdapterAllProducts;
import com.example.restaurant18.databinding.FragmentNewOrderBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class NewOrderFragment extends Fragment {

    private FragmentNewOrderBinding binding;
    double total;
    Dialog dialog;
    ArrayList<Product> productsList = new ArrayList<>();
    ArrayList<OrderComponent> orderProductsList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewOrderViewModel homeViewModel =
                new ViewModelProvider(this).get(NewOrderViewModel.class);

        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dialog = new Dialog(getContext());

        productsList.add(new Product("Hamburger1", "desc", "10.0"));
        productsList.add(new Product("Hamburger2", "desc", "10.0"));
        productsList.add(new Product("Hamburger3", "desc", "10.0"));
        productsList.add(new Product("Hamburger4", "desc", "10.0"));
        productsList.add(new Product("Hamburger5", "desc", "10.0"));
        productsList.add(new Product("Hamburger6", "desc", "10.0"));
        productsList.add(new Product("Hamburger7", "desc", "10.0"));

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

                total = total+Double.parseDouble(currentProduct.getProductPrice());
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

                total = total-Double.parseDouble(currentProduct.getProductPrice());
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
                openFavoriteStoreDialog(currentProduct);
                //Toast.makeText(getContext(), "Couldn't save your changes", Toast.LENGTH_SHORT).show();
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

    public void openFavoriteStoreDialog(Product product)
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
                String message = ((MainActivity)getActivity()).addProductToFavoriteProductsList(product);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}