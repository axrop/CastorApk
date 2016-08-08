package pe.edu.upc.castor.models;

import java.util.ArrayList;
import java.util.List;
import pe.edu.upc.castor.R;

public class Product {

    private float precio;
    private String nombre;
    private int idDrawable;

    public Product(float precio, String nombre, int idDrawable) {
        this.precio = precio;
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public static final List<Product> PLATILLOS = new ArrayList<>();

    static {
        PLATILLOS.add(new Product(5, "Camarones Tismados", R.drawable.camarones));
        PLATILLOS.add(new Product(3.2f, "Rosca Herbárea", R.drawable.rosca));
        PLATILLOS.add(new Product(12f, "Sushi Extremo", R.drawable.sushi));
        PLATILLOS.add(new Product(9, "Sandwich Deli", R.drawable.sandwich));
        PLATILLOS.add(new Product(34f, "Lomo De Cerdo Austral", R.drawable.lomo_cerdo));

    }

    public float getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

}
