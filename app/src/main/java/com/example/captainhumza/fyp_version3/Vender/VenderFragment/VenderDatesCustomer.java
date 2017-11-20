package com.example.captainhumza.fyp_version3.Vender.VenderFragment;

import android.content.Context;
import android.content.Intent;
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

import com.example.captainhumza.fyp_version3.Classes.Orders;
import com.example.captainhumza.fyp_version3.Classes.VenderClass.SingleInstanceOrder;
import com.example.captainhumza.fyp_version3.R;
import com.example.captainhumza.fyp_version3.Vender.VenderFragment.RecycleViewAdapter.CustomerDatesListAdapter;

import java.util.ArrayList;
import java.util.List;


public class VenderDatesCustomer extends Fragment  implements SearchView.OnQueryTextListener{
    private RecyclerView recyclerview;
    private List<Orders> mOrderDetails;
    private CustomerDatesListAdapter adapter;
    OnListFragmentInteractionListener mListener = null;
    Orders order;
    public VenderDatesCustomer(Orders or){ order = or;}
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mOrderDetails = new ArrayList<>();
       /* for (Orders entry: SingleInstanceOrder.GetInctance().orders)
        {
            if(SingleInstanceOrder.GetInctance().orders) {
                mOrderDetails.add(entry);
            }
        }*/
        for(int i = 0; i < SingleInstanceOrder.GetInctance().orders.size(); i++)
        {
            if(SingleInstanceOrder.GetInctance().orders.get(i).CustomerId == order.CustomerId) {
                mOrderDetails.add(SingleInstanceOrder.GetInctance().orders.get(i));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vender_dates_customer, container, false);

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
      //  mOrderDetails = new ArrayList<>();

        adapter = new CustomerDatesListAdapter(mOrderDetails , mListener);

        recyclerview.setAdapter(adapter);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(mOrderDetails);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
        item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.badge1);
        final View cartIcon = menu.findItem(R.id.action_cart).getActionView();
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = "hello world";

                //Toast.makeText(this,a,Toast.LENGTH_SHORT).show();
                /*Intent i = new Intent(v.getContext(), AddToCartActivityCustomer.class);
                startActivity(i);*/
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Orders> filteredModelList = filter(mOrderDetails, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Orders> filter(List<Orders> models, String query) {
        query = query.toLowerCase();

        final List<Orders> filteredModelList = new ArrayList<>();
        for (Orders model : models) {
            final String text = model.OrderDate;
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onButtonClicked(String mItem);
    }
}
