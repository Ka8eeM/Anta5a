package com.example.karim.anta5a.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.anta5a.R;
import com.example.karim.anta5a.adapters.ViewPagerAdapter;
import com.example.karim.anta5a.database.SharedPrefManager;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    LinearLayout dotsLinear;
    private ArrayList<TextView> dots;
    AsyncTask<String, String, JSONArray> jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.slide_view_pager);
        dotsLinear = findViewById(R.id.dots_layout);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        addDotsIndicatior(0);
        viewPager.addOnPageChangeListener(listener);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    OnPageChangeListener listener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicatior(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void addDotsIndicatior(int pos) {
        dots = new ArrayList<>();
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            TextView txt = new TextView(this);
            txt.setText(Html.fromHtml("&#8226;"));
            txt.setTextSize(35);
            txt.setTextColor(getResources().getColor(R.color.black));
            dots.add(txt);
            dotsLinear.addView(txt);
        }
        if (dots.size() > 0) {
            dots.get(pos).setTextColor(getResources().getColor(R.color.bkColor));
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.my_account) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
        } else if (id == R.id.my_notification) {
            intent = new Intent(MainActivity.this, NotificationsActivity.class);
        } else if (id == R.id.report_problem) {
            intent = new Intent(MainActivity.this, ProfileActivity.class);
        } else if (id == R.id.log_out) {
            logOut();
            Toast.makeText(this, "you logged out", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.about_us) {
            intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.my_orders) {
            intent = new Intent(MainActivity.this, OrderActivity.class);
        }
        if (intent != null)
            startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        SharedPrefManager.getInstance(MainActivity.this).logOut();
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }*/
}
