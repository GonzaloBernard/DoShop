package com.example.doshop;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doshop.domain.Producto;

public class AbmcProduct extends AppCompatActivity {
    private Button btnAddProducto;
    private ImageButton btnAddImgProduct;
    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etDescripcioProducto;
    private ImageView imgvFotoProducto;


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_productos, menu);
        return super.onCreateOptionsMenu(menu);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abmc_product);
        //TOOLBAR
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.tbAbmcProduct);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("AGREGAR PRODUCTO");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Bundle extras = getIntent().getExtras();

        //Definiciones
        etNombreProducto = (EditText) findViewById(R.id.editTextNombreProducto);
        etPrecioProducto = (EditText) findViewById(R.id.editTextPrecioProducto);
        btnAddImgProduct = (ImageButton) findViewById(R.id.btnAddFotoProducto);
        etDescripcioProducto = (EditText) findViewById(R.id.editTextDescripcion);
        btnAddProducto = (Button) findViewById(R.id.buttonAgregarProducto);

        switch (extras.getInt(ProductoAdapter._ABMC_PRODUCTO_MODO_KEY)){
            case ProductoAdapter._KEY_CREAR_PRODUCTO:
        }








    }



}
