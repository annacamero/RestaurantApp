package com.example.annacamero.restaurantapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<InfoPlat> llista;
    private List<Comanda> llista2;
    private Adapter adapter;
    private int quantPlat=1;
    int taula=3;
    Double totalPreu=0.0;
    private ImageView logoview;
    private ImageView iconosushiview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        llista=new ArrayList<>();
        llista2=new ArrayList<>();


        logoview = findViewById(R.id.logoview);
        iconosushiview = findViewById(R.id.iconosushiview);

        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                        .into(logoview);

        Glide.with(this)
                .load("///android_asset/SUSHI.png")
                .into(iconosushiview);





        //definicions necesaries per al RecyclerView

        RecyclerView recyclerViewMenu=findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerViewMenu.setAdapter(adapter);


        //Sincronització amb FireBase
        db.collection("plats").document("02").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                InfoPlat info = documentSnapshot.toObject(InfoPlat.class);
            }
        });

        db.collection("plats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                llista.clear();
                for (DocumentSnapshot doc : documentSnapshots) {
                    try {
                        InfoPlat plat = doc.toObject(InfoPlat.class);
                        llista.add(plat);
                    } catch (RuntimeException err) {
                        Log.e("RestaurantApp",
                                String.format("Error de conversió al plat %s: %s", doc.getId(), err.toString()));
                    }
                    Log.i("RestaurantApp", doc.getString("nom"));
                }
                adapter.notifyDataSetChanged();
            }
        });

        //linquem imatges al Layout
        ImageView logoview = findViewById(R.id.logoview);
        ImageView iconosushiview= findViewById(R.id.iconosushiview);
        iconosushiview.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));

        /*Glide.with(this)
                .load("file:///android_asset/search.png")
                .apply(RequestOptions.centerInsideTransform())
                .into(iconosushiview);*/

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
                TextView totalPreuView=findViewById(R.id.totalview);
                totalPreuView.setText(String.format("%.2f€", totalPreu));
                adapter.notifyDataSetChanged();
            }
        });

    }




    //creem la clase Viewholder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView headerView;
        TextView preuView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.diagNomView);
            this.headerView=itemView.findViewById(R.id.header_view);
            this.preuView=itemView.findViewById(R.id.preuView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    onClickPlat(pos);
                }
            });
        }
    }

    //creem la clase Adapter
    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public int getItemCount() {
            return llista.size();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=getLayoutInflater().inflate(R.layout.plat_view,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            InfoPlat infoPlatItem=llista.get(position);
            holder.nomView.setText(infoPlatItem.getNom());
            holder.headerView.setText(infoPlatItem.getTipus());
            holder.headerView.setVisibility(View.GONE);
            if(position==0){
                holder.headerView.setVisibility(View.VISIBLE);
            }
            else if(!llista.get(position-1).getTipus().equals(infoPlatItem.getTipus()))  {
                holder.headerView.setVisibility(View.VISIBLE);
            }
            // holder.ingredientsView.setText(infoPlatItem.getIngredients());
            holder.preuView.setText(Double.toString(infoPlatItem.getPreu()));
        }
    }

    //Linquem boto amb ResumenActivity
    public void onCliclVerPedido(View view) {
        Intent intent =new Intent(this, ResumenActivity.class);
        startActivity(intent);
    }

    //permetem clicar el layout
    private void onClickPlat(int pos) {
        quantPlat=1;
        //Toast.makeText(this, "Has clicat " + pos, Toast.LENGTH_SHORT).show();

        final AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_plat,null);
        dialogBuilder.setView(dialogView);

        final String nom=llista.get(pos).getNom();
        String descripcio=llista.get(pos).getIngredients();
        final String id=llista.get(pos).getId();
        final Double preu=llista.get(pos).getPreu();

        TextView diagPlatView = (TextView)dialogView.findViewById(R.id.diagNomView);
        TextView diagDescrView= (TextView)dialogView.findViewById(R.id.diagDescrView);
        final TextView diagQuantView= (TextView)dialogView.findViewById(R.id.diagQuantView);
        diagPlatView.setText(nom);
        diagDescrView.setText(descripcio);
        diagQuantView.setText(String.valueOf(quantPlat));
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        Button btnPlus=(Button)dialogView.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantPlat=quantPlat+1;
                diagQuantView.setText(String.valueOf(quantPlat));
            }
        });

        Button btnMinus=(Button)dialogView.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantPlat>1)quantPlat=quantPlat-1;
                diagQuantView.setText(String.valueOf(quantPlat));
            }
        });

        Button btnCanc=(Button)dialogView.findViewById(R.id.btnCanc);
        btnCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button btnAcc=(Button)dialogView.findViewById(R.id.btnAcc);
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comanda comanda1=new Comanda(nom,taula,id,quantPlat,preu*quantPlat,false);
                db.collection("comandes").add(comanda1);
                alertDialog.dismiss();
            }
        });

    }



}
