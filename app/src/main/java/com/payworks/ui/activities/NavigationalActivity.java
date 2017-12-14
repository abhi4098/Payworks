package com.payworks.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.payworks.R;
import com.payworks.ui.fragments.MyProfileFragment;
import com.payworks.ui.fragments.ProfileHomePageFragment;
import com.payworks.utils.PrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment profileHomePageFragment;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_app_title)
    TextView tvAppTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational);
        ButterKnife.bind(this);
        //setSupportActionBar(toolbar);
        setFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_Home);
        //navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        MenuItem itemid = navigationView.getMenu().findItem(R.id.nav_Home);

        if (getFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            onNavigationItemSelected(itemid);
        }

        setUserLoggedIn();
    }

    public void setFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        profileHomePageFragment = new ProfileHomePageFragment();
        fragmentTransaction.add(R.id.fragment_container, profileHomePageFragment, "PROFILE");
        fragmentTransaction.commit();
        tvAppTitle.setText("WELCOME");

    }

    private void setUserLoggedIn() {
        PrefUtils.storeUserLoggedIn(true, this);
    }

    private void callRestart() {
        //NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        //notificationManager.cancel(5555);
        //notificationManager.cancel(4444);
        PrefUtils.storeUserLoggedIn(false, this);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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
        getMenuInflater().inflate(R.menu.navigational, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        switch (id) {


            case R.id.nav_Home:
                fragment = new ProfileHomePageFragment();
                tvAppTitle.setText(item.getTitle());

                break;
            case R.id.nav_my_profile:
                fragment = new MyProfileFragment();
                tvAppTitle.setText(item.getTitle());

                break;

            case R.id.nav_wallet:
                //fragment = new NewlyUpdatedFragment();
                tvAppTitle.setText(item.getTitle());

                break;

            case R.id.nav_my_transactions:
                //fragment = new ManageCategoriesfragment();
                tvAppTitle.setText(item.getTitle());
                break;

            case R.id.nav_sent_money_request:
                //fragment = new MyProductsFragment();
                tvAppTitle.setText(item.getTitle());
                break;

            case R.id.nav_refer_a_friend:
                //fragment = new StockDetailsfragment();
                tvAppTitle.setText(item.getTitle());
                break;

            case R.id.nav_my_bank_account:
                //fragment = new StockDetailsfragment();
                tvAppTitle.setText(item.getTitle());
                break;

            case R.id.nav_notification:
               // fragment = new ChangePasswordFragment();
                tvAppTitle.setText(item.getTitle());
                break;

            case R.id.nav_logout:
                callRestart();
                break;

            default:
                fragment = new ProfileHomePageFragment();
                tvAppTitle.setText(item.getTitle());
                break;


        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
