package com.example.doshop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doshop.domain.Evento;
import com.example.doshop.domain.Grupo;
import com.example.doshop.domain.Producto;
import com.example.doshop.repository.GruposDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class AbmcProduct extends AppCompatActivity {
    private Button btnAddProducto;
    private ImageButton btnAddImgProduct;
    private EditText etNombreProducto;
    private EditText etPrecioProducto;
    private EditText etDescripcioProducto;
    private ImageView imgvFotoProducto;
    private String imagenProduct;


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abmc_evento, menu);
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

        final Bundle extras = getIntent().getExtras();

        //Definiciones
        etNombreProducto = (EditText) findViewById(R.id.editTextNombreProducto);
        etPrecioProducto = (EditText) findViewById(R.id.editTextPrecioProducto);
        btnAddImgProduct = (ImageButton) findViewById(R.id.btnAddFotoProducto);
        etDescripcioProducto = (EditText) findViewById(R.id.editTextDescripcion);
        btnAddProducto = (Button) findViewById(R.id.buttonAgregarProducto);
        imgvFotoProducto = (ImageView) findViewById(R.id.imgProduct);

        switch (extras.getInt(ProductoAdapter._ABMC_PRODUCTO_MODO_KEY)){
            case ProductoAdapter._KEY_CREAR_PRODUCTO:
                final Evento evento = extras.getParcelable(GrupoAdapter._ABMC_EVENTO_MODO_KEY);
                final Producto producto = new Producto();
                try{
                    btnAddImgProduct.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(android.view.View view) {
                            cargarImagenDeGaleria();
                            producto.setImagenProducto(imagenProduct);
                        }
                    });
                    producto.setProductoNombre(etNombreProducto.getText().toString());
                    producto.setProductoPrecio(Float.valueOf(etPrecioProducto.getText().toString()));
                    producto.setProductoId(String.valueOf(evento.getListaElementos().size()));
                    producto.setProductoDescripcion(etDescripcioProducto.getText().toString());
                    btnAddProducto.setOnClickListener(new Button.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            if(producto.getProductoNombre().isEmpty()){
                                Toast.makeText(AbmcProduct.this,"Debe ingresar por lo menos un nombre al producto",Toast.LENGTH_SHORT).show();
                            }else {
                                evento.addElemento(producto);
                                AbmcProduct.crearGrupoFirebase actualizarGrupoFireBase = new AbmcProduct.crearGrupoFirebase();
                                actualizarGrupoFireBase.execute(evento.getGrupoPerteneciente());
                                finish();
                            }


                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(AbmcProduct.this, "Error !!!",Toast.LENGTH_SHORT).show();
                    Log.e("Error ::::: ",e.getMessage());
                }
                break;

        }

    }

    private void cargarImagenDeGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),10);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            Uri path = data.getData();
            File file = new File(String.valueOf(path));
            Bitmap imageBitmap = null;
            try{
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile (file));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 5, stream);
                byte[] byteArray = stream.toByteArray();
                imagenProduct = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }catch (IOException e){
                e.printStackTrace();
            }if (imageBitmap != null){
                imgvFotoProducto.setImageBitmap (imageBitmap);
            }
        }
    }

    //Actualizar el grupo con el producto en el evento
    class crearGrupoFirebase extends AsyncTask<Grupo, Void, Void> {
        @Override
        protected Void doInBackground(Grupo... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.insertarGrupo(grupos[0]);
            return null;
        }
    }

    /* Borrar grupo en la base de datos Firebase
    class BorrarGrupoFirebase extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... grupos) {
            GruposDatabase gruposDatabase = GruposDatabase.getInstance();
            gruposDatabase.borrarGrupo(grupos[0]);
            return null;
        }
    }*/

}
