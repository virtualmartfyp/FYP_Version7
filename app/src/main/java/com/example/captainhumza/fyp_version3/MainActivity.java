package com.example.captainhumza.fyp_version3;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.Classes.ConstantClass;
import com.example.captainhumza.fyp_version3.Classes.GetSyncTaskForSubProduct;
import com.example.captainhumza.fyp_version3.Classes.ProductCategory;
import com.example.captainhumza.fyp_version3.Classes.Products;
import com.example.captainhumza.fyp_version3.Customer.Fragments.AllProductListFragmentCustomer;
import com.example.captainhumza.fyp_version3.Customer.Fragments.CartCustomer;
import com.example.captainhumza.fyp_version3.Customer.Fragments.CustomerMapFragment;
import com.example.captainhumza.fyp_version3.Customer.Fragments.ExpandableListDirectory.ExpandableListDataPump;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , CustomerMapFragment.OnMapFragmentInteractionListener ,
        AllProductListFragmentCustomer.OnFragmentAllProductInteractionListener {

    private static MainActivity inst;
    public static MainActivity instance() {
        return inst;
    }
    private ViewPager viewPager;
    private static android.support.v4.app.Fragment fragment = new CustomerMapFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inst = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragment = new AllProductListFragmentCustomer(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager , fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void setupViewPager(ViewPager viewPager , android.support.v4.app.Fragment fragment) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new ProductListFragmentCustomer(), "One");
        adapter.addFragment(fragment, "Two");
        //adapter.addFragment(new DatesListFragmentCustomer(), "Three");
        viewPager.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentAllProductMethodInteraction(ProductCategory st , ExpandableListDataPump _expandableListDataPump) {
        int id = st.ProductSubCatId;
        //Toast.makeText(this,""+id,Toast.LENGTH_SHORT).show();
        GetSyncTaskForSubProduct gc = new GetSyncTaskForSubProduct(st , _expandableListDataPump);
        //gc.SetValue(st , _expandableListDataPump);
        gc.execute(ConstantClass.Product+id,null,null);

    }
    @Override
    public void onFragmentAllProductToCart(List<Products> ls )
    {
        fragment = new CartCustomer(ls);
        setupViewPager(viewPager , fragment);
    }

    public void AddToCarOnly(View view) {
        TextView ab = (TextView)findViewById(R.id.expandedListItem);
        Toast.makeText(inst,ab.getText()+"button", Toast.LENGTH_SHORT).show();
    }
}
