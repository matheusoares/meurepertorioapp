package com.meurepertorio.meurepertorio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.meurepertorio.meurepertorio.R;
import com.meurepertorio.meurepertorio.adapter.TabsAdapter;

public class MusicasActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TabLayout.OnTabSelectedListener{

    private ViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_musicas);


        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MusicasActivity.this, MusicaActivity.class);

                intent.putExtra("qualFragmentAbrir", "MusicaNovaFragment");

                startActivity(intent);
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();



        viewPager = (ViewPager) findViewById(R.id.tabanim_viewpager);

        TabsAdapter adapter = new TabsAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(3);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setOnTabSelectedListener(this);
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

        switch(item.getItemId()){
            case R.id.nav_fragment_list_musicas:
                break;
            case R.id.nav_fragment_sobre:
                Toast.makeText(MusicasActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(MusicasActivity.this, "Em desenvolvimento", Toast.LENGTH_SHORT).show();
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}