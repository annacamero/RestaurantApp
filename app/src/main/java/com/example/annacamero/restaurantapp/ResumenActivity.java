package com.example.annacamero.restaurantapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ResumenActivity extends AppCompatActivity {
    private List<Comanda> llista2;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        RecyclerView recyclerViewResum=findViewById(R.id.recyclerViewResum);
        recyclerViewResum.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerViewResum.setAdapter(adapter);

        Comanda comanda1=new Comanda("espagueti",2,"5",2,5.5,false);
        Comanda comanda2=new Comanda("espagueti2",2,"9",1,5.2,true);
        Comanda comanda3=new Comanda("espagueti3",3,"1",1,2.5,false);
        Comanda comanda4=new Comanda("espagueti4",3,"6",3,1.9,false);
        Comanda comanda5=new Comanda("espagueti5",7,"3",2,6.1,false);


        llista2=new ArrayList<>();
        llista2.add(comanda1);
        llista2.add(comanda2);
        llista2.add(comanda3);
        llista2.add(comanda4);
        llista2.add(comanda5);
    }


    //creem clase ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView quantView;
        TextView preuView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.quantView=itemView.findViewById(R.id.quantView);
            this.preuView=itemView.findViewById(R.id.preuView);
        }
    }

    //creem clase Adapter
    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public int getItemCount() {
            return llista2.size();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=getLayoutInflater().inflate(R.layout.resumen_view,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Comanda comandaItem=llista2.get(position);

            //holder.headerView.setText("hola");
            holder.quantView.setText(String.valueOf(comandaItem.getQuantitat())+"x");

            holder.nomView.setText(comandaItem.getNom());
            // holder.ingredientsView.setText(infoPlatItem.getIngredients());
            holder.preuView.setText(Double.toString(comandaItem.getPreu())+"€");
        }
    }

    //linquem boto a CartaActivity
    public void onClickCarta(View view) {
        Intent intent=new Intent(this,CartaActivity.class);
        startActivity(intent);

    }
}
