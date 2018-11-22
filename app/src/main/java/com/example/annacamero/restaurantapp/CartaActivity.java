package com.example.annacamero.restaurantapp;

import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {

    private List<InfoPlat> llista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        //construim llista ficticia
        InfoPlat plat1=new InfoPlat("galetes","farina","0.99");
        InfoPlat plat2=new InfoPlat("orxata","xufla","1.35");
        InfoPlat plat3=new InfoPlat("MacMenu","Rates","1.55");
        InfoPlat plat4=new InfoPlat("Flan","Nata","9.99");
        llista=new ArrayList<>();
        llista.add(plat1);
        llista.add(plat2);
        llista.add(plat3);
        llista.add(plat4);

        RecyclerView recyclerViewMenu=findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenu.setAdapter(new Adapter());
    }

    //creem la clase Viewholder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView ingredientsView;
        TextView preuView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.ingredientsView=itemView.findViewById(R.id.ingredientsView);
            this.preuView=itemView.findViewById(R.id.preuView);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public int getItemCount() {
            return llista.size();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=getLayoutInflater().inflate(R.layout.holder_menu,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            InfoPlat infoPlatItem=llista.get(position);
            holder.nomView.setText(infoPlatItem.getNom());
            holder.ingredientsView.setText(infoPlatItem.getIngredients());
            holder.preuView.setText(infoPlatItem.getPreu());
        }


    }
}
