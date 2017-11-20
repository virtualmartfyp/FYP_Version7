package com.example.captainhumza.fyp_version3.Customer.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.GetSync;
import com.example.captainhumza.fyp_version3.Classes.GetSyncTaskForSubProduct;
import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory.CustomExpandableListAdapter;
import com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory.ExpandableListDataPump;
import com.example.captainhumza.fyp_version3.DataBase.DataBaseHandler;
import com.example.captainhumza.fyp_version3.MainActivity;
import com.example.captainhumza.fyp_version3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllProductListFragmentCustomer.OnFragmentAllProductInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AllProductListFragmentCustomer extends Fragment implements SearchView.OnQueryTextListener ,
        GetSyncTaskForSubProduct.GetASyncTaskInterface , GetSync.GetASyncTaskTitleInterface {

    private final ProgressDialog dialog = new ProgressDialog(MainActivity.instance());
    ExpandableListView expandableListView;
    CustomExpandableListAdapter expandableListAdapter;

    List<ProductCategory> expandableListTitle;
    HashMap<ProductCategory, List<Products>> expandableListDetail;
    List<ProductCategory> filteredModelList;
    ExpandableListDataPump expandableListDataPump = new ExpandableListDataPump();
    public static AllProductListFragmentCustomer inst;
    CartCustomer cartCustomer = null;
    int lock = 0;

    private OnFragmentAllProductInteractionListener mListener;
    public static AllProductListFragmentCustomer instance(){return inst;}
    public AllProductListFragmentCustomer(OnFragmentAllProductInteractionListener _mListner)
    {
        mListener = _mListner;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Mart");
        inst = this;
        new GetSync(expandableListDataPump, this).execute(ConstantClass.subProduct, null, null);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_all_product_list_fragment_customer, container, false);



        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

               try
               {
                   if(lock == 0) {

                       new GetSyncTaskForSubProduct(expandableListTitle.get(groupPosition) , expandableListDataPump
                               , AllProductListFragmentCustomer.instance()).execute(ConstantClass.Product+expandableListTitle.get(groupPosition).ProductSubCatId,null,null);

                   }else if(lock == 1)
                   {
                       new GetSyncTaskForSubProduct(filteredModelList.get(groupPosition) , expandableListDataPump
                               ,  AllProductListFragmentCustomer.instance()).execute(ConstantClass.Product+filteredModelList.get(groupPosition).ProductSubCatId,null,null);
                   }
               }
               catch (Exception ex)
               {
                   Toast.makeText(view.getContext(),ex.getMessage()+"exapand error",Toast.LENGTH_SHORT).show();
               }

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Toast.makeText(
                        view.getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition).ProductName+" __expandable child", Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
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
                        lock = 0;
                        //expandableListAdapter.setFilter(expandableListTitle);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {

                        lock = 1;
                        return true;
                    }
                });
        item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.badge1);
        final View cartIcon = menu.findItem(R.id.action_cart).getActionView();
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartCustomer == null)
                {
                    cartCustomer = new CartCustomer(expandableListAdapter.cartList ,AllProductListFragmentCustomer.instance(),MainActivity.instance());
                    cartCustomer.SetCartList(expandableListAdapter.cartList);
                }else
                    {
                        cartCustomer.SetCartList(expandableListAdapter.cartList);
                    }
                mListener.onFragmentAllProductToCart(cartCustomer);
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ProductCategory> filteredModelList = filter(expandableListTitle, newText);
        expandableListAdapter.setFilter(filteredModelList);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<ProductCategory> filter(List<ProductCategory> models, String query) {
        query = query.toLowerCase();


        filteredModelList = new ArrayList<>();
        for (ProductCategory model : models) {
            final String text = model.ProductSubCat.toLowerCase();
            final String text1 = model.CategoryDescription.toLowerCase();
            if (text.contains(query) ||text1.contains(query) ) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void UpdateAdapter() {
        expandableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void UpdateTiteleAdapter() {
        if(expandableListAdapter == null)
        {
            expandableListDetail = expandableListDataPump.getData();
            expandableListTitle = new ArrayList<ProductCategory>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(MainActivity.instance(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
        }else
            {
                expandableListView.setAdapter(expandableListAdapter);
            }

    }

    public interface OnFragmentAllProductInteractionListener {
        // TODO: Update argument type and name
        void onFragmentAllProductToCart(CartCustomer cartCustomer );
    }
}
