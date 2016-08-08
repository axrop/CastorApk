package pe.edu.upc.castor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.adapters.ProductAdapter;
import pe.edu.upc.castor.util.Constants;

public class CategoryActivity extends AppCompatActivity {

    private Map map = new HashMap();
    private Map subMap = new HashMap();
    private Map subProMap = new HashMap();
    private String[] array = {};
    private String[] subArray = {};
    private String[] subProArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle bundle = getIntent().getExtras();

        if( bundle!=null ) {
            String categoryId = bundle.getString("categoryId");

            if (categoryId != null && !categoryId.equals("")) {
                this.loadProductsByCategory(this, categoryId);
            } else {
                this.getCategories(this);
            }
        }else {
            this.getCategories(this);
        }
    }

    private void getCategories(final CategoryActivity categoryActivity) {

        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET, Constants.CATEGORY_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    array = new String[response.length()];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonobject = response.getJSONObject(i);
                        String name = jsonobject.getString("name");
                        map.put(jsonobject.getString("id"), jsonobject.getString("name"));
                        array[i] = name;
                    }

                    ListView listView = (ListView) findViewById(R.id.categoriesListView);
                    ArrayAdapter adapter = new ArrayAdapter(categoryActivity, R.layout.activity_listview, array);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            setContentView(R.layout.activity_sub_category);
                            ((TextView) findViewById(R.id.categoryTitleEditText)).setText(((TextView) view).getText());

                            String categoryId = "";
                            String categoryTitle = "";

                            Iterator it = map.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry pair = (Map.Entry)it.next();
                                if(((TextView) view).getText().toString().equals(pair.getValue())) {
                                    categoryId = pair.getKey().toString();
                                    categoryTitle = pair.getValue().toString();
                                    break;
                                }
                            }

                            getSubCategories(categoryActivity, categoryId, categoryTitle);

                            /*System.out.println("id: "+id);
                            Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();*/
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

    private void getSubCategories(final CategoryActivity categoryActivity, final String categoryId, final String categoryTitle) {

        /*String categoryParentId = "";

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(categoryTitle.equals(pair.getValue())){
                categoryParentId = pair.getKey().toString();
            }
            *//*System.out.println(pair.getKey() + " = " + pair.getValue());*//*
        }*/

        if( !categoryId.equals("") ) {

            JsonArrayRequest jsonRequest = new JsonArrayRequest(
                    Request.Method.GET, Constants.SUB_CATEGORY_URL + categoryId, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    try {

                        subArray = new String[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = response.getJSONObject(i);
                            String name = jsonobject.getString("name");
                            subMap.put(jsonobject.getString("id"), jsonobject.getString("name"));
                            subArray[i] = name;
                        }

                        ListView listView = (ListView) findViewById(R.id.subCategoriesListView);
                        ArrayAdapter adapter = new ArrayAdapter(categoryActivity, R.layout.activity_listview, subArray);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String subCategoryId = "";
                                String subCategoryTitle = "";

                                Iterator it = subMap.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry)it.next();
                                    if(((TextView) view).getText().toString().equals(pair.getValue())) {
                                        subCategoryId = pair.getKey().toString();
                                        subCategoryTitle = pair.getValue().toString();
                                        break;
                                    }
                                }

                                setContentView(R.layout.activity_category_product);
                                ((TextView)findViewById(R.id.categoryTitleEditText)).setText(categoryTitle);
                                ((TextView)findViewById(R.id.subCategoryTitleEditText)).setText(subCategoryTitle);
                                loadProductsByCategory(categoryActivity, subCategoryId);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);

        }
    }

    private void loadProductsByCategory(final CategoryActivity categoryActivity, String categoryId){

        if( !categoryId.equals("") ) {

            JsonArrayRequest jsonRequest = new JsonArrayRequest(
                    Request.Method.GET, Constants.CATEGORY_PRODUCT_URL + categoryId, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    try {

                        subProArray = new String[response.length()];

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = response.getJSONObject(i);
                            String name = jsonobject.getString("name");
                            subProMap.put(jsonobject.getString("id"), jsonobject.getString("name"));
                            subProArray[i] = name;
                        }

                        ListView listView = (ListView) findViewById(R.id.productCategoryListView);

                        ArrayAdapter adapter = new ArrayAdapter(categoryActivity, R.layout.activity_listview, subProArray);

                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                String productId = "";

                                Iterator it = subProMap.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry)it.next();
                                    if(((TextView) view).getText().toString().equals(pair.getValue())) {
                                        productId = pair.getKey().toString();
                                        break;
                                    }
                                }

                                Bundle bundle = new Bundle();
                                bundle.putString("productId", productId);

                                Intent intent = new Intent();
                                intent.setClass(CategoryActivity.this, ProductActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            Volley.newRequestQueue(this).add(jsonRequest);
        }

    }

}
