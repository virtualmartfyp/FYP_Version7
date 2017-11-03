package com.example.captainhumza.fyp_version3.Customer.Fragments.RecyclerViewDirectory;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.Customer.Fragments.RecycleViewHolder.CartListViewHolder;
import com.example.captainhumza.fyp_version3.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Captain Humza on 10/31/2017.
 */

public class CarListAdapter extends RecyclerView.Adapter<CartListViewHolder> {
    private List<Products> product;

    public CarListAdapter(List<Products> product) {
        this.product = product;
    }

    @Override
    public void onBindViewHolder(final CartListViewHolder itemViewHolder, int i) {
        final Products model = product.get(i);
        itemViewHolder.bind(model);
        itemViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(itemViewHolder.view.getContext());
                alert.setTitle(itemViewHolder.name_TextView.getText());
                alert.show();

            }
        });
        itemViewHolder.imagePlus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int count = Integer.parseInt((String) itemViewHolder.cart_product_quantity_tv.getText()) + 1;
                itemViewHolder.cart_product_quantity_tv.setText(count +"");
            }
        });
        itemViewHolder.imageMinus.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int count = Integer.parseInt((String) itemViewHolder.cart_product_quantity_tv.getText());
                if(count > 1) {
                    count -= 1;
                    itemViewHolder.cart_product_quantity_tv.setText(count + "");
                }

            }
        });
    }

    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_cart_customer_row, viewGroup, false);
        return new CartListViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public void setFilter(List<Products> countryModels){
        product = new ArrayList<>();
        product.addAll(countryModels);
        notifyDataSetChanged();
    }
}
