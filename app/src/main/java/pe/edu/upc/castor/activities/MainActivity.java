package pe.edu.upc.castor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.fragments.FragmentoCategorias;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FloatingActionButton fab;

    private static final String SHARED_PREF_KEY = "prefs";
    private static final String PREF_USER_TYPE = "";

    // Storage Access Class
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        String userType = sharedPreferences.getString(PREF_USER_TYPE, "vacio");

        if( userType!=null||userType.equals("") ){
            if( userType.equals("1") ){//vendedor
                showOptionsSeller(navigationView);
            }else if( userType.equals("2") ){//cliente
                showOptionsClient(navigationView);
            }
        }else{
            showOptionsPublic(navigationView);
        }

        navigationView.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_sign_in) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        } else if (id == R.id.nav_sign_off) {
            sharedPreferences = this.getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.remove(PREF_USER_TYPE);
            e.commit();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        // Setear título actual
        setTitle(item.getTitle());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showOptionsClient(NavigationView navigationView){
        navigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_order).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_sign_in).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_sign_off).setVisible(true);
    }

    private void showOptionsSeller(NavigationView navigationView){
        navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_order).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_sign_in).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_sign_off).setVisible(true);
    }

    private void showOptionsPublic(NavigationView navigationView){
        navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_favorite).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_order).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_sign_in).setVisible(true);
        navigationView.getMenu().findItem(R.id.nav_sign_off).setVisible(false);
    }



}
