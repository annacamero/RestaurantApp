package com.example.joanfluviamarin.restaurantappcuina;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {

    private List<InfoPlat> llista;
    private Adapter adapter;
    private List<Integer> borrarPlat;
    private ImageView logoview;
    private ImageView comandaview;
    private ImageView cartaview;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        llista=new ArrayList<>();
        borrarPlat=new ArrayList<>();

        logoview = findViewById(R.id.logoview);
        comandaview = findViewById(R.id.imageview3);
        cartaview = findViewById(R.id.cartaview);

        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                .into(logoview);

        Glide.with(this)
                .load("///android_asset/iconocomgranate.png")
                .into(comandaview);

        Glide.with(this)
                .load("///android_asset/platogris.png")
                .into(cartaview);

        //definicions necesaries per al RecyclerView
        RecyclerView recyclerViewMenu=findViewById(R.id.recyclerViewMenu);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        recyclerViewMenu.setAdapter(adapter);

        //sincronització amb FireBase
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
    }




    //creem la clase Viewholder
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nomView;
        TextView headerView;
        //TextView preuView;


        public ViewHolder(final View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.headerView=itemView.findViewById(R.id.headerView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = getAdapterPosition();
                    onClickPlat(pos);
                    return false;
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
            //holder.preuView.setText(Double.toString(infoPlatItem.getPreu()));
        }
    }

    private void onClickPlat(int pos) {
        Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
        borrarPlat.add(pos);
        adapter.notifyDataSetChanged();
    }
    public void onClickComanda(View view) {
    finish();
    }

    public void onClickAddPlat(View view) {
        Intent intent =new Intent(this, AddPlatActivity.class);
        intent.putExtra("Id.",llista.get(llista.size()-1).getId());
        startActivity(intent);
    }

    public void onClickDelPlat(View view) {
        for(int value:borrarPlat){
            final String borrar=llista.get(value).getId();
            db.collection("plats").get().addOnCompleteListener (new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc : task.getResult()){
                            if(doc.getString("id")==borrar){
                                String borrar2=doc.getId();
                                db.collection("plats").document(borrar2).delete();
                            }
                            //Toast.makeText(ResumenActivity.this, doc.getId(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
