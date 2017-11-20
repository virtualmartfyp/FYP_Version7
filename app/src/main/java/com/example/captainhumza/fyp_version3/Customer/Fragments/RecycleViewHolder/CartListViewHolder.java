package com.example.captainhumza.fyp_version3.Customer.Fragments.RecycleViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.R;
import com.example.captainhumza.fyp_version3.app.AppController;

/**
 * Created by Captain Humza on 10/31/2017.
 */

public class CartListViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public TextView name_TextView  , cart_product_quantity_tv , cartWeight , cartPrice  , totalPrice;
    public NetworkImageView producImageView;
    public ImageView imagePlus ,imageMinus;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CartListViewHolder(View itemView) {
        super(itemView);
        itemView.setClickable(true);
        view = itemView;

        cart_product_quantity_tv = (TextView)itemView.findViewById(R.id.cart_product_quantity_tv);
        imagePlus = (ImageView) itemView.findViewById(R.id.cart_plus_img);
        imageMinus = (ImageView) itemView.findViewById(R.id.cart_minus_img);
        name_TextView = (TextView) itemView.findViewById(R.id.ListItemName);
        producImageView = (NetworkImageView) itemView.findViewById(R.id.thumbnailProduct);
        cartWeight = (TextView)itemView.findViewById(R.id.weight);
        cartPrice = (TextView)itemView.findViewById(R.id.price);
        totalPrice = (TextView)itemView.findViewById(R.id.totalPrice);

    }

    public void bind(Products product) {
        name_TextView.setText(product.ProductName);
        cartPrice.setText(product.ProductRate+"");
        cartWeight.setText(product.Weight + " " +product.UnitDEscription);
        totalPrice.setText(product.totalPrice+"");
        cart_product_quantity_tv.setText(product.quantity+"");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        producImageView.setImageUrl(ConstantClass.productImagePath+product.ProductImage,imageLoader);

    }
}
