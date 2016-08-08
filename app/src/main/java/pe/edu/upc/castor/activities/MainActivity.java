package pe.edu.upc.castor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.adapters.ProductAdapter;
import pe.edu.upc.castor.models.Product;
import pe.edu.upc.castor.util.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FloatingActionButton fab;

    private TextView profileTextView;
    private TextView userNameTextView;

    // Storage Access Class
    SharedPreferences sharedPreferences;

    private RecyclerView reciclador;
    private GridLayoutManager layoutManager;
    private ProductAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reciclador = (RecyclerView)findViewById(R.id.reciclador);
        layoutManager = new GridLayoutManager(this, 2);
        reciclador.setLayoutManager(layoutManager);
        adaptador = new ProductAdapter(Product.PLATILLOS);
        reciclador.setAdapter(adaptador);


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

        sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_KEY, MODE_PRIVATE);
        String userType = sharedPreferences.getString(Constants.PREF_USER_TYPE, "");

        profileTextView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.profileTextView);
        userNameTextView = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userNameTextView);

        if( userType!=null&&!userType.equals("") ){
            if( userType.equals("1") ){//vendedor
                showOptionsSeller(navigationView);
                profileTextView.setText("Seller");
            }else if( userType.equals("2") ){//cliente
                showOptionsClient(navigationView);
                profileTextView.setText("Client");
            }

            String firstName = sharedPreferences.getString(Constants.PREF_USER_FIRSTNAME, "");
            String lastName = sharedPreferences.getString(Constants.PREF_USER_LASTNAME, "");

            userNameTextView.setText(firstName+" "+lastName);

        }else{
            profileTextView.setText("Public");
            userNameTextView.setText("");
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
        } else if (id == R.id.nav_categories) {
            startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(getApplicationContext(), AccountActivity.class));
        } else if (id == R.id.nav_favorite) {
            startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
        } else if (id == R.id.nav_order) {
            startActivity(new Intent(getApplicationContext(), OrderActivity.class));
        } else if (id == R.id.nav_shopping_cart) {
            startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
        } else if (id == R.id.nav_sign_in) {
            startActivity(new Intent(getApplicationContext(), SigninActivity.class));
        } else if (id == R.id.nav_sign_off) {
            sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREF_KEY, MODE_PRIVATE);
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.remove(Constants.PREF_USER_ID);
            e.remove(Constants.PREF_USER_TYPE);
            e.remove(Constants.PREF_USER_FIRSTNAME);
            e.remove(Constants.PREF_USER_LASTNAME);
            e.commit();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
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
