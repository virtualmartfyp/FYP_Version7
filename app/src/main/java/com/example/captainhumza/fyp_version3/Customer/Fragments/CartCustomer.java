package com.example.captainhumza.fyp_version3.Customer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.CartRequestWebServiceHandler;
import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.Customer.Fragments.RecyclerViewDirectory.CarListAdapter;
import com.example.captainhumza.fyp_version3.MainActivity;
import com.example.captainhumza.fyp_version3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CartCustomer extends Fragment implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerview;
    CarListAdapter adapter;
    private List<Products> productList;
    List<Products> filteredModelList;
    AllProductListFragmentCustomer allProductListFragmentCustomer;
    CartInterfaceListner cartInterfaceListner;
    public static int backButtonLock = 0;
    public CartCustomer(List<Products> lsPorduct ,
                        AllProductListFragmentCustomer allProductListFragmentCustomer , CartInterfaceListner cartInterfaceListner)
    {
        this.cartInterfaceListner = cartInterfaceListner;
        this.allProductListFragmentCustomer = allProductListFragmentCustomer;
        //productList = lsPorduct;
    }
    public void SetCartList(List<Products> lsPorduct )
    {
        productList = lsPorduct;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("My Cart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart_customer, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);


        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        adapter = new CarListAdapter(productList);
        recyclerview.setAdapter(adapter);
        TextView backText = (TextView)view.findViewById(R.id.backText);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonLock = 1;
                cartInterfaceListner.CartInteractionMethod(allProductListFragmentCustomer);
            }
        });
        RelativeLayout relativeLayoutCancel = (RelativeLayout)view.findViewById(R.id.cancleOrder);
        relativeLayoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        RelativeLayout relativeLayoutRequest = (RelativeLayout)view.findViewById(R.id.RequestOrder);
        relativeLayoutRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Products p = new Products();
               // p.MakeOrder(productList);
                new CartRequestWebServiceHandler(productList , ConstantClass.RequestProduct).execute();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cart_manu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(productList);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        Toast.makeText(MainActivity.instance(), item.toString()+"item click", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
       /* item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.badge1);
        final View cartIcon = menu.findItem(R.id.action_cart).getActionView();
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Products> filteredModelList = filter(productList, newText);
        adapter.setFilter(filteredModelList);
        return false;
    }
    private List<Products> filter(List<Products> models, String query) {
        query = query.toLowerCase();


        filteredModelList = new ArrayList<>();
        for (Products model : models) {
            final String text = model.ProductName.toLowerCase();
            if (text.contains(query) ) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
    public interface CartInterfaceListner {
        // TODO: Update argument type and name
        void CartInteractionMethod(AllProductListFragmentCustomer all);
    }
}
