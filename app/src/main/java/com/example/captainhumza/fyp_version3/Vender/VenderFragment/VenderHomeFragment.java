package com.example.captainhumza.fyp_version3.Vender.VenderFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captainhumza.fyp_version3.Classes.Orders;
import com.example.captainhumza.fyp_version3.R;
import com.example.captainhumza.fyp_version3.Vender.VenderFragment.RecycleViewAdapter.CustomerIdentityListAdapter;

import java.util.List;

public class VenderHomeFragment extends Fragment {
    private RecyclerView recyclerview;
    CustomerIdentityListAdapter adapter;

    List<Orders> orders;
    Orders model;
    onFragmentIntractionListAdapterToVender listner;
    public VenderHomeFragment( List<Orders> _model ,onFragmentIntractionListAdapterToVender _listner ) {
        orders = _model;
        listner = _listner;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CustomerIdentityListAdapter(listner);
        getActivity().setTitle("Customers");
    }
    public VenderHomeFragment context()
    {
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vender_home, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(layoutManager);


        return  view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        adapter.SetOrderList(orders );
        recyclerview.setAdapter(adapter);
    }

    public interface onFragmentIntractionListAdapterToVender
    {
        void intractionMethod(Orders or);
    }
}
