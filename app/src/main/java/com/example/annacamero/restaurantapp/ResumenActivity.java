package com.example.annacamero.restaurantapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ResumenActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Comanda> llista2;
    private Adapter adapter;
    int taula=3;
    Double totalPreu=0.0;
    private ImageView logoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);

        logoview = findViewById(R.id.logoview);

        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                .into(logoview);

        RecyclerView recyclerViewResum=findViewById(R.id.recyclerViewResum);
        recyclerViewResum.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerViewResum.setAdapter(adapter);

        //Comanda comanda1=new Comanda("espagueti",2,"5",2,5.5,false);
        //Comanda comanda2=new Comanda("espagueti2",2,"9",1,5.2,true);
        //Comanda comanda3=new Comanda("espagueti3",3,"1",1,2.5,false);
        //Comanda comanda4=new Comanda("espagueti4",3,"6",3,1.9,false);
        //Comanda comanda5=new Comanda("espagueti5",7,"3",2,6.1,false);

        llista2=new ArrayList<>();
        //llista2.add(comanda1);
        //llista2.add(comanda2);
        //llista2.add(comanda3);
        //llista2.add(comanda4);
        //llista2.add(comanda5);

        db.collection("comandes").whereEqualTo("taula", taula).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                llista2.clear();
                totalPreu=0.0;
                for (DocumentSnapshot doc : documentSnapshots) {
                    Comanda comanda = doc.toObject(Comanda.class);
                    llista2.add(comanda);
                    Log.i("RestaurantApp", doc.getString("nom"));
                }
                //mogut dins del db.collection. ara com a mínim funciona...
                for (Comanda num : llista2){
                    totalPreu=totalPreu+num.getPreu();
                }
                TextView totalPreuView=findViewById(R.id.totalPreuView);
                totalPreuView.setText(String.format("%.2f€", totalPreu));
                adapter.notifyDataSetChanged();
            }
        });

        // perque em diu que la llista esta buida????
        /*

            Perquè us deveu pensar que el codi del Firebase s'executa i després fas el teu 'for'
            però no és així. Quan poses un listener, el codi del listener s'executará després,
            és a dir que el db.collection("...").addSnapshotListener(...) triga en fer-se un
            nanosegon (perquè només es deixar preparat el listener, i també s'engega la consulta,
            però fins que les dades no tornin no s'executarà el listener), i immediatament
            s'executa lo següent, que és el teu bucle que mira la llista. Com que la llista
            es crea buida, doncs llavors està buida fins que no s'ompli en el listener, es clar.

            2018-12-27 -pauek

         */

        //for (Comanda num : llista2){
               //totalPreu=totalPreu+num.getPreu();
       // }
        //int val=llista2.size();
        //TextView totalPreuView=findViewById(R.id.totalPreuView);
        //totalPreuView.setText(Double.toString(totalPreu));
    }


    //creem clase ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView quantView;
        TextView preuView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.diagNomView);
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
        //Intent intent=new Intent(this,CartaActivity.class);
        //startActivity(intent);
        finish();

    }

    //afegim boto per a borrar comanda. accés sols del personal del Restaurant.
    public void onClickBorrar(View view){
        db.collection("comandes").whereEqualTo("taula",taula)
        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc : task.getResult()){
                        String borrar=doc.getId();
                        //Toast.makeText(ResumenActivity.this, doc.getId(), Toast.LENGTH_SHORT).show();
                        db.collection("comandes").document(borrar).delete();
                    }
                }
            }
        });
        //Toast.makeText(this, Integer.toString(val), Toast.LENGTH_SHORT).show();

    }
}

























