package pe.edu.upc.castor.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.adapters.SectionAdapter;
import pe.edu.upc.castor.fragments.LoginFragment;
import pe.edu.upc.castor.fragments.RegisterFragment;

public class SigninActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        insertarTabs();
        viewPager = (ViewPager) findViewById(R.id.pager);
        poblarViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void poblarViewPager(ViewPager viewPager) {
        SectionAdapter adapter = new SectionAdapter(getSupportFragmentManager());
        adapter.addFragment(LoginFragment.nuevaInstancia(0), getString(R.string.titulo_tab_login));
        adapter.addFragment(RegisterFragment.nuevaInstancia(1), getString(R.string.titulo_tab_register));
        viewPager.setAdapter(adapter);
    }

    private void insertarTabs() {
        appBar = (AppBarLayout) findViewById(R.id.appbar);
        tabLayout = new TabLayout(this);
        tabLayout.setTabTextColors(Color.parseColor("#BCBCBC"), Color.parseColor("#149488"));
        appBar.addView(tabLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBar.removeView(viewPager);
    }
}
