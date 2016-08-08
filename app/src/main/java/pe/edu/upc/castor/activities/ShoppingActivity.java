package pe.edu.upc.castor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.castor.R;
import pe.edu.upc.castor.adapters.ProductAdapter;
import pe.edu.upc.castor.adapters.ShoppingAdapter;
import pe.edu.upc.castor.models.Product;

public class ShoppingActivity extends AppCompatActivity {
    /*
    Declarar instancias globales
    */
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Inicializar Animes
        List items = new ArrayList();

        items.add(new Product(5, "Camarones Tismados", R.drawable.camarones));
        items.add(new Product(3.2f, "Rosca Herbárea", R.drawable.rosca));
        items.add(new Product(12f, "Sushi Extremo", R.drawable.sushi));
        items.add(new Product(9, "Sandwich Deli", R.drawable.sandwich));
        items.add(new Product(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.shoppingRecycler);
        //recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ShoppingAdapter(items);
        recycler.setAdapter(adapter);

    }

}
