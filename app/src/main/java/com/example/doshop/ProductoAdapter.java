package com.example.doshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doshop.domain.Grupo;
import com.example.doshop.domain.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoHolder>{
    private Context context;
    private List<Producto> mProductDataset;
    public static final String _ABMC_PRODUCTO_MODO_KEY = "_ABMC_PRODUCTO_MODO_KEY";
    public static final int _KEY_CREAR_PRODUCTO = 1;
    public static final int _KEY_BUSCAR_PRODUCTO = 2;
    public static final String _PRODUCTO_KEY = "_PRODUCTO_KEY";

    public ProductoAdapter(List<Producto> ProductDataset){
        this.mProductDataset = ProductDataset;
    }

    @NonNull
    @Override
    public ProductoAdapter.ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_producto, parent, false);
        ProductoAdapter.ProductoHolder vh = new ProductoAdapter.ProductoHolder(v);
        context=parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {
        final Producto product = mProductDataset.get(position);
        holder.tvProductNombre.setText(product.getProductoNombre());
        holder.tvProductPrecio.setText(product.getProductoPrecio().toString());
        holder.tvProductoDescr.setText(product.getProductoDescripcion());
        //holder.imgProduct



    }

    @Override
    public int getItemCount() {
        return mProductDataset.size();
    }

    public class ProductoHolder extends RecyclerView.ViewHolder{
        TextView tvProductNombre;
        TextView tvProductPrecio;
        TextView tvProductoDescr;
        ImageView imgProduct;
        Button btnEditar;
        Button btnBorrar;

        public ProductoHolder(@NonNull View itemView) {
            super(itemView);
            this.tvProductNombre = (TextView) itemView.findViewById(R.id.txtvNombreProd);
            this.tvProductoDescr = (TextView) itemView.findViewById(R.id.txtvDescriProd);
            this.tvProductPrecio = (TextView) itemView.findViewById(R.id.txtvPrecioProd);
            this.imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            this.btnBorrar = (Button) itemView.findViewById(R.id.btnQuitarProducto);
            this.btnEditar = (Button) itemView.findViewById(R.id.btnEditarProducto);

        }
    }

}


