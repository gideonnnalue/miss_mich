package com.example.tojuzone.nphcdasmsapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.tojuzone.nphcdasmsapp.R;
import com.example.tojuzone.nphcdasmsapp.SettingsActivity;
import com.example.tojuzone.nphcdasmsapp.fragments.FixedSessionFragment;
import com.example.tojuzone.nphcdasmsapp.fragments.HomeFragment;
import com.example.tojuzone.nphcdasmsapp.fragments.OutreachSessionFragment;
import com.example.tojuzone.nphcdasmsapp.fragments.WeeklySessionsFragment;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            setTitle("NPHCDA RI SMS Reporting");
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                setTitle("NPHCDA RI SMS Reporting");
                break;
            case R.id.weekly_session:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WeeklySessionsFragment()).commit();
                setTitle("Weekly session plan");
                break;
            case R.id.fixed_session:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FixedSessionFragment()).commit();
                setTitle("Fixed session report");
                break;
            case R.id.outreach_session:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OutreachSessionFragment()).commit();
                setTitle("Outreach session report");
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
