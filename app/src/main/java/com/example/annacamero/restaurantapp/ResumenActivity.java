package com.example.annacamero.restaurantapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.List;


public class ResumenActivity extends AppCompatActivity {
    private List<InfoPlat> llista2;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView quantView;
        TextView preuView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.quantView=itemView.findViewById(R.id.header_view);
            this.preuView=itemView.findViewById(R.id.preuView);
        }
    }
}
