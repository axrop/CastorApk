package pe.edu.upc.castor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.edu.upc.castor.R;

public class LoginFragment extends Fragment {

    private EditText accountEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private static String LOGIN_URL = "http://192.168.43.228:8081/castor/api/security/";
    private static final String INDICE_SECCION
            = "upc.edu.pe.castor.LoginFragmentTab.extra.INDICE_SECCION";

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

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        accountEditText = (EditText)view.findViewById(R.id.accountEditText);
        passwordEditText = (EditText)view.findViewById(R.id.passwordEditText);

        loginButton = (Button)view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                System.out.println("account: "+accountEditText.getText().toString());
                System.out.println("account: "+passwordEditText.getText().toString());

                try {
                    jsonObject.put("account", String.valueOf(accountEditText.getText()));
                    jsonObject.put("password", String.valueOf(passwordEditText.getText()));
                }catch (Exception ex){
                    System.out.println("error 1");
                    ex.printStackTrace();
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest(
                  Request.Method.POST, LOGIN_URL, jsonObject, new Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if( response==null ){
                                System.out.println("NO AUTHORIZED");
                            }else{
                                System.out.println("lastName:"+response.get("lastName"));
                            }
                        }catch (Exception ex){
                            System.out.println("error 2");
                            ex.printStackTrace();
                        }
                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error 3");
                        error.printStackTrace();
                    }
                });

                Volley.newRequestQueue(getContext()).add(jsonRequest);

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
