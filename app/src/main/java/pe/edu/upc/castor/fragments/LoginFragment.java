package pe.edu.upc.castor.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.activities.MainActivity;
import pe.edu.upc.castor.activities.PasswordActivity;

public class LoginFragment extends Fragment {

    private EditText accountEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private static String LOGIN_URL = "http://192.168.1.110:8081/castor/api/security/";
    private static final String INDICE_SECCION = "upc.edu.pe.castor.LoginFragmentTab.extra.INDICE_SECCION";

    private static final String SHARED_PREF_KEY = "prefs";
    private static final String PREF_USER_TYPE = "";
    private static final String PREF_USER_FIRSTNAME = "";
    private static final String PREF_USER_LASTNAME = "";
    private static final String PREF_USER_ID = "";

    // Storage Access Class
    SharedPreferences sharedPreferences;

    public static LoginFragment nuevaInstancia(int indiceSeccion) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putInt(INDICE_SECCION, indiceSeccion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        accountEditText = (EditText)view.findViewById(R.id.accountEditText);
        passwordEditText = (EditText)view.findViewById(R.id.passwordEditText);



        loginButton = (Button)view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( !accountEditText.getText().toString().equals("") &&
                        !passwordEditText.getText().toString().equals("")   ) {

                    final JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("account", String.valueOf(accountEditText.getText()));
                        jsonObject.put("password", String.valueOf(passwordEditText.getText()));
                    } catch (Exception ex) {
                        System.out.println("error 1");
                        ex.printStackTrace();
                    }

                    final Listener<JSONObject> responseListener = new Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response == null) {
                                    System.out.println("NO AUTHORIZED");
                                } else {

                                    sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);

                                    SharedPreferences.Editor e = sharedPreferences.edit();
                                    e.putString(PREF_USER_ID, response.get("id").toString());
                                    e.putString(PREF_USER_FIRSTNAME, response.get("firstName").toString());
                                    e.putString(PREF_USER_LASTNAME, response.get("lastName").toString());
                                    e.putString(PREF_USER_TYPE, response.get("profileId").toString());
                                    e.commit();

                                    startActivity(new Intent(getContext(), MainActivity.class));
                                }
                            } catch (JSONException e) {
                                System.out.println("error 2a");
                            }
                        }
                    };

                    ErrorListener errorListener = new ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        System.out.println("error 3");
                        }
                    };

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(  Request.Method.POST,
                                                                            LOGIN_URL,
                                                                            jsonObject,
                                                                            responseListener,
                                                                            errorListener   );

                    Volley.newRequestQueue(getContext()).add(jsonRequest);

                }
                /*JsonArrayRequest jsonRequest = new JsonArrayRequest(
                        Request.Method.GET, LOGIN_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println("EN EL ON RESPONSE!!!!");

                        try {
                            for(int i=0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                String description    = jsonobject.getString("description");
                                System.out.println("description: "+description);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });*/



            }
        });

        return view;
    }

}
