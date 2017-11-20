package com.example.captainhumza.fyp_version3.Customer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.MainActivity;
import com.example.captainhumza.fyp_version3.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double  lat = 24.823741700000003;
    Double  lng = 67.11515829999999;
    public PlaceAutocompleteFragment autocompleteFragment;
    MapInteractionInterface mapInteractionInterface;
    public MapFragment(MapInteractionInterface _mapInteractionInterface) {
        mapInteractionInterface = _mapInteractionInterface;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        autocompleteFragment = (PlaceAutocompleteFragment)
                MainActivity.instance().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        try
        {
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    lat = place.getLatLng().latitude;
                    lng = place.getLatLng().longitude;
                    onMapReady(mMap);
                    //mapInteractionInterface.MapMarkedSearchLocation();
                }

                @Override
                public void onError(Status status) {
                    Toast.makeText(MainActivity.instance(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception ex)
        {
            Toast.makeText(MainActivity.instance(), ex.getMessage() ,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public interface MapInteractionInterface
    {
        void MapMarkedSearchLocation(Double  lat, Double  lng);
    }
}





