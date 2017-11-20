package com.example.captainhumza.fyp_version3.CommonFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragmant.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragmant#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragmant extends Fragment {


    private OnFragmentInteractionListener mListener;
    EditText username , email , contact;
    public ProfileFragmant() {
        // Required empty public constructor
    }
    public static ProfileFragmant newInstance(String param1, String param2) {
        ProfileFragmant fragment = new ProfileFragmant();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Profile");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragmant, container, false);
        contact = (EditText) view.findViewById(R.id.contct);
        email = (EditText) view.findViewById(R.id.email);
        username = (EditText) view.findViewById(R.id.user);
        username.setText(Person.GetInstance().PersonUserName);
        email.setText(Person.GetInstance().PersonEmail);
        contact.setText(Person.GetInstance().PersonCell);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
