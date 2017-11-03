package com.example.captainhumza.fyp_version3.Customer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.example.captainhumza.fyp_version3.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomerMapFragment extends Fragment implements  OnMapReadyCallback {
    private OnMapFragmentInteractionListener mListener;
    Double  lat = 24.823741700000003;
    Double  lng = 67.11515829999999;
    GoogleMap mMap;
    public CustomerMapFragment() {
        // Required empty public constructor
    }
    public CustomerMapFragment(Double  lat, Double  lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
       try
       {
           mMap = googleMap;
           LatLng sydney = new LatLng(lat , lng);
           mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( new LatLng (lat , lng) , 15));
           mMap.addMarker(new MarkerOptions().position(sydney).title("Your Selected Location "));
       }
       catch(Exception e)
       {
           Toast.makeText(MainActivity.instance(), e.getMessage(), Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_map, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMapFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMapFragmentInteractionListener) {
            mListener = (OnMapFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

   /* @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/


    public interface OnMapFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMapFragmentInteraction(Uri uri);
    }
}
