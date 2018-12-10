package com.example.annacamero.restaurantapp;

import android.icu.text.IDNA;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
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
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        llista=new ArrayList<>();

        ImageView logoview = findViewById(R.id.logoview);
        ImageView iconosushiview= findViewById(R.id.iconosushiview);
        iconosushiview.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));

        /*Glide.with(this)
                .load("file:///android_asset/search.png")
                .apply(RequestOptions.centerInsideTransform())
                .into(iconosushiview);*/





        //db.collection("plats").add(plat2);
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

        RecyclerView recyclerViewMenu=findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerViewMenu.setAdapter(adapter);

        /*

        final TextView test = findViewById(R.id.test);

        db.collection("plats").document("plattest").addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                test.setText(documentSnapshot.getString("platid"));
            }
        });

<<<<<<< Updated upstream
        db.collection("plats").add(plat1);*/
//=======
        //db.collection("plats").document("macarrons").set(plat1);

//>>>>>>> Stashed changes

        //db.collection("plats").addSnapshotListener(new EventListener<QuerySnapshot>() {
       //     @Override
       //     public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

       //         for (DocumentSnapshot doc : documentSnapshots) {
       //             InfoPlat plat = doc.toObject(InfoPlat.class);
//
  //              }
 //           }
  //      })
    }

    //creem la clase Viewholder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView headerView;
        TextView preuView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.headerView=itemView.findViewById(R.id.header_view);
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
            View itemView=getLayoutInflater().inflate(R.layout.plat_view,parent,false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            InfoPlat infoPlatItem=llista.get(position);
            holder.nomView.setText(infoPlatItem.getNom());
            //holder.headerView.setText("hola");
            holder.headerView.setVisibility(View.GONE);
            // holder.ingredientsView.setText(infoPlatItem.getIngredients());
            holder.preuView.setText(Double.toString(infoPlatItem.getPreu()));
        }


    }
}
