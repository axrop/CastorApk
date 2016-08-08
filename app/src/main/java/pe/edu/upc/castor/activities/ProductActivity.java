package pe.edu.upc.castor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.util.Constants;

public class ProductActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    TextView brandTextView;
    TextView localPriceTextView;
    TextView dolarPriceTextView;
    TextView formatTextView;
    TextView thicknessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nameTextView = (TextView)findViewById(R.id.nameTextView);
        brandTextView = (TextView)findViewById(R.id.brandTextView);
        descriptionTextView = (TextView)findViewById(R.id.descriptionTextView);
        localPriceTextView = (TextView)findViewById(R.id.localPriceTextView);
        dolarPriceTextView = (TextView)findViewById(R.id.dolarPriceTextView);
        formatTextView = (TextView)findViewById(R.id.formatTextView);
        thicknessTextView = (TextView)findViewById(R.id.thicknessTextView);

        Bundle bundle = getIntent().getExtras();
        String productId = bundle.getString("productId");

        loadProductsCategory(productId);

    }

    private void loadProductsCategory(String productId){

        final Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response == null) {
                        System.out.println("NO AUTHORIZED");
                    } else {
                        String name = String.valueOf(response.get("name"));
                        String brand = String.valueOf(response.get("brand"));
                        String description = String.valueOf(response.get("description"));
                        String localPrice = String.valueOf(response.get("localPrice"));
                        String dolarPrice = String.valueOf(response.get("dolarPrice"));
                        String format = String.valueOf(response.get("format"));
                        String formatUnit = String.valueOf(response.get("formatUnit"));
                        String thickness = String.valueOf(response.get("thickness"));
                        String thicknessUnit = String.valueOf(response.get("thicknessUnit"));

                        nameTextView.setText(name);
                        brandTextView.setText(brand);
                        descriptionTextView.setText(description);
                        dolarPriceTextView.setText("$"+dolarPrice);
                        localPriceTextView.setText("S/."+localPrice);
                        formatTextView.setText("Format: "+format+" "+formatUnit );
                        thicknessTextView.setText("thickness :"+thickness+" "+thicknessUnit);

                        System.out.println("AUTHORIZED");

                    }
                } catch (JSONException e) {
                    System.out.println("error 2a");
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("error 3: "+error.getMessage());
            }
        };

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                Constants.PRODUCT_URL+productId,
                null,
                responseListener,
                errorListener   );

        Volley.newRequestQueue(this).add(jsonRequest);

    }

}
