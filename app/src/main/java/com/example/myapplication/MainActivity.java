package com.example.myapplication;

import android.content.Context;

import android.os.Bundle;

import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private DrawerLayout drawerLayoutId;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationId;
    private Toolbar toolbar;
    private ImageView imageViewSearch;
    private EditText editTextSearch;
    private RelativeLayout relativeLayout;
    private LinearLayout linearLayoutEditText;
    private ImageView deletePin;
    private int counter = 0;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500; //delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;

    private String[] paths = {"Agla1", "Agla2", "Agla3", "Agla4", "Agla5"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recylcerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MainListAdapter mainListAdapter = new MainListAdapter(getMealList());
        recyclerView.setAdapter(mainListAdapter);

        initUI();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, paths);
        viewPager.setAdapter(myCustomPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == paths.length) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        setupNavigationDrawer();

        initCollapsingToolbar();

        setListener();

    }

    public List<String> getMealList() {
        List<String> ponnoList = new ArrayList<>();
        ponnoList.add("Zeorge Vai");
        ponnoList.add("Sinigdho");
        ponnoList.add("Ashik");
        ponnoList.add("Rupom");
        ponnoList.add("Ifti");
        ponnoList.add("Jeesan");
        ponnoList.add("Toufiq");
        ponnoList.add("Lion");
        ponnoList.add("Reza");
        ponnoList.add("Boggie");
        ponnoList.add("Shawn");
        return ponnoList;
    }

    private void setListener() {
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewSearch.setVisibility(View.GONE);
                editTextSearch.setVisibility(View.VISIBLE);
                counter++;
            }
        });

        deletePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter !=0){
                    editTextSearch.setVisibility(View.GONE);
                    imageViewSearch.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getResources().getString(R.string.sell_text));
                    collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" "); //carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    private void setupNavigationDrawer() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutId, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayoutId.addDrawerListener(actionBarDrawerToggle);
        navigationId.setNavigationItemSelectedListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        setDrawerState(true);
    }

    private void initUI() {
        drawerLayoutId = (DrawerLayout) findViewById(R.id.drawerlayoutMain);
        navigationId = (NavigationView) findViewById(R.id.mainNavigationView);
        imageViewSearch = (ImageView) findViewById(R.id.imageViewSearch);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        linearLayoutEditText = (LinearLayout) findViewById(R.id.linearLayoutEditText);
        deletePin = (ImageView) findViewById(R.id.deletePin);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerlayoutMain);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawerLayoutId.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            actionBarDrawerToggle.syncState();
        } else {
            drawerLayoutId.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
            //Drawable backButton = ContextCompat.getDrawable(this, R.mipmap.ic_back_button);
            getSupportActionBar().setHomeAsUpIndicator(null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.menu_sell:
                Toast.makeText(context, getString(R.string.app_name)+ " Clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_purchase:
                Toast.makeText(context, "Policy clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_product:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_expense:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_delivery:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;


            case R.id.menu_employee:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_due:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_loan:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_customer:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_dealar_company:
                Toast.makeText(context, "Agla clicked", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }


}
