package com.example.captainhumza.fyp_version3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.captainhumza.fyp_version3.Classes.CartRequestWebServiceHandler;
import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.CommonFragments.ProfileFragmant;
import com.example.captainhumza.fyp_version3.Customer.Fragments.AllProductListFragmentCustomer;
import com.example.captainhumza.fyp_version3.Customer.Fragments.CartCustomer;
import com.example.captainhumza.fyp_version3.Customer.Fragments.MapFragment;
import com.example.captainhumza.fyp_version3.DataBase.DataBaseHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AllProductListFragmentCustomer.OnFragmentAllProductInteractionListener
        , CartCustomer.CartInterfaceListner ,MapFragment.MapInteractionInterface ,
        CartRequestWebServiceHandler.CartRequestIntreaction {

    private static MainActivity inst;
    public static MainActivity instance() {
        return inst;
    }
    private ViewPager viewPager;
    private android.support.v4.app.Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inst = this;
        if(Person.GetInstance() == null)
        {
            Intent intent = new Intent(this,AccountActivity.class);
            startActivity(intent);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fragment = new AllProductListFragmentCustomer(this);
        fragment = new MapFragment(this);
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
            fragment = new MapFragment(this);
            setupViewPager(viewPager , fragment);
        } else if (id == R.id.nav_oders) {
            CartCustomer.backButtonLock = 0;
            fragment = new AllProductListFragmentCustomer(this);
            setupViewPager(viewPager,fragment);
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragmant();
            setupViewPager(viewPager,fragment);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.logOut) {
            Person.DestroyInstance();
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentAllProductToCart(CartCustomer cartCustomer)
    {
        fragment = cartCustomer;
        setupViewPager(viewPager , fragment);
    }

    public void AddToCarOnly(View view) {
        TextView ab = (TextView)findViewById(R.id.expandedListItem);
        Toast.makeText(inst,ab.getText()+"button", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CartInteractionMethod(AllProductListFragmentCustomer _allProductListFragmentCustomer) {
        fragment = _allProductListFragmentCustomer;
        setupViewPager(viewPager,fragment);
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance().PersonTypeId != 3)
            finish();
    }

    @Override
    public void MapMarkedSearchLocation(Double lat, Double lng) {

    }

    @Override
    public void requestOrdered(String msg) {
        Toast.makeText(inst, msg +"/n Request Sent", Toast.LENGTH_SHORT).show();
    }
}
