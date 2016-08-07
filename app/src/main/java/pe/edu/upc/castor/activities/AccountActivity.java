package pe.edu.upc.castor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.castor.R;

public class AccountActivity extends AppCompatActivity {

    private CardView changePasswordCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        changePasswordCardView = (CardView)findViewById(R.id.ChangePasswordCardView);
        changePasswordCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
            }
        });


        final List<String> list=new ArrayList<String>();
        list.add("DNI");
        list.add("CE");

        Spinner s = (Spinner) findViewById(R.id.DocumentTypeSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);

    }

}
