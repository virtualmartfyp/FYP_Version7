package com.example.captainhumza.fyp_version3.Vender;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.captainhumza.fyp_version3.AccountActivity;
import com.example.captainhumza.fyp_version3.Classes.Orders;
import com.example.captainhumza.fyp_version3.Classes.Person;
import com.example.captainhumza.fyp_version3.Classes.VenderClass.SingleInstanceOrder;
import com.example.captainhumza.fyp_version3.Classes.VenderClass.UpdateListReceiver;
import com.example.captainhumza.fyp_version3.CommonFragments.ProfileFragmant;
import com.example.captainhumza.fyp_version3.MainActivity;
import com.example.captainhumza.fyp_version3.R;
import com.example.captainhumza.fyp_version3.Vender.VenderFragment.RecycleViewAdapter.CustomerIdentityListAdapter;
import com.example.captainhumza.fyp_version3.Vender.VenderFragment.VenderDatesCustomer;
import com.example.captainhumza.fyp_version3.Vender.VenderFragment.VenderHomeFragment;
import com.example.captainhumza.fyp_version3.Vender.backEndServices.ServicesGetLatestOrder;
import com.example.captainhumza.fyp_version3.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class VenderMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , VenderDatesCustomer.OnListFragmentInteractionListener,
        Serializable ,UpdateListReceiver.UpdateRecieverIntraction,
        VenderHomeFragment.onFragmentIntractionListAdapterToVender
{
    private ViewPager viewPager;

    private android.support.v4.app.Fragment fragment;
    private static VenderMenuActivity inst;
    List<Orders> orders;
    //SingleInstanceOrder ss = SingleInstanceOrder.GetInctance();
    public static int orderId = 0;
    public static VenderMenuActivity instance() {
        return inst;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_menu);
        if(Person.GetInstance() == null)
        {
            Intent intent = new Intent(this,AccountActivity.class);
            startActivity(intent);
        }
        inst = this;
        orders  = SingleInstanceOrder.GetInctance().orders;
        CallService();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        fragment = new VenderHomeFragment(orders , this);
        setupViewPager(viewPager, fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.vender_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
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
        } else if (id == R.id.nav_wallet) {
            /*fragment = new VenderDatesCustomer();
            setupViewPager(viewPager,fragment);*/
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
    public void onRestart()
    {
        super.onRestart();
        if(Person.GetInstance().PersonTypeId != 2)
            finish();
    }

    public void CallService() {
       final Intent service = new Intent(VenderMenuActivity.this, ServicesGetLatestOrder.class);
        try {
            android.os.Handler handler = new android.os.Handler();
            UpdateListReceiver reciver = new UpdateListReceiver(handler);
            reciver.setReciver(this);
            service.putExtra("serialize",reciver);
            startService(service);
        }catch (Exception ex)
        {
            Toast.makeText(this, "sorry" +ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onButtonClicked(String mItem) {
        Toast.makeText(VenderMenuActivity.instance(), "Task is pending", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecieveUpdates(Bundle resultData) {
        String st = resultData.getString("ordersString");
        GenrateList(st , orders);


    }
    public void Notification()
    {
        Intent intent = new Intent(this,VenderMenuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,0);
        Notification notification = new Notification.Builder(this)
                .setContentText("i am humza")
                .setContentTitle("Notification Pop Up")
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_minus,"popUp",pendingIntent)
                .setSmallIcon(R.drawable.ic_action_cart)
                .build();
        NotificationManager noti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.notify(1,notification);
    }
    boolean check = false;
    public void GenrateList(String result , List<Orders> orders)
    {
        try {
            //if(result.length() > 10) {
                JSONArray st = new JSONArray(result);

                for (int i = 0; i < st.length(); i++) {
                    Orders pc = new Orders();
                    JSONObject str = st.getJSONObject(i);
                    pc.CustomerId = str.getInt("CustomerId");
                    pc.OrderId = str.getInt("OrderId");
                    pc.VendorId = str.getInt("VendorId");
                    pc.OrderDate = str.getString("OrderDate");
                    pc.StatusId = str.getInt("StatusId");
                    orders.add(pc);
                    if (pc.OrderId > orderId){
                        orderId = pc.OrderId;
                        check = true;
                    }
                    //  Toast.makeText(this,orderId+"orderId" , Toast.LENGTH_SHORT).show();
                }
                if(check == true) {
                    Notification();
                    fragment = new VenderHomeFragment(orders , this);
                    setupViewPager(viewPager, fragment);
                    check = false;
                }
            //}

        } catch (JSONException e) {
            Toast.makeText(MainActivity.instance(),e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        //return lsOrders;
    }

    @Override
    public void intractionMethod(Orders or) {

        fragment = new VenderDatesCustomer(or);
        setupViewPager(viewPager,fragment);
    }
}
