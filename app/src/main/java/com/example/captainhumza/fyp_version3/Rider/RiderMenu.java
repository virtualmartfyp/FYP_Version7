package com.example.captainhumza.fyp_version3.Rider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.captainhumza.fyp_version3.AccountActivity;
import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.CommonFragments.ProfileFragmant;
import com.example.captainhumza.fyp_version3.Customer.Fragments.MapFragment;
import com.example.captainhumza.fyp_version3.R;
import com.example.captainhumza.fyp_version3.ViewPagerAdapter;

public class RiderMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , MapFragment.MapInteractionInterface {
    private ViewPager viewPager;
    private static android.support.v4.app.Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Person.GetInstance() == null)
        {
            Intent intent = new Intent(this,AccountActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_rider_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       // fragment = new MapFragment(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //setupViewPager(viewPager , fragment);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.rider_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager , android.support.v4.app.Fragment fragment) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //adapter.addFragment(new ProductListFragmentCustomer(), "One");
        adapter.addFragment(fragment, "Two");
        //adapter.addFragment(new DatesListFragmentCustomer(), "Three");
        viewPager.setAdapter(adapter);

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.wallet) {
            // Handle the camera action
        } else if (id == R.id.profile) {
            fragment = new ProfileFragmant();
            setupViewPager(viewPager,fragment);
        } else if (id == R.id.order) {

        } else if (id == R.id.contact) {

        } else if (id == R.id.abou) {

        } else if (id == R.id.logOut) {
            Person.DestroyInstance();
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance().PersonTypeId != 1)
            finish();
    }
    @Override
    public void MapMarkedSearchLocation(Double lat, Double lng) {

    }
}
