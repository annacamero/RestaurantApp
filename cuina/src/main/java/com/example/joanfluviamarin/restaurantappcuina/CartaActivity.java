package com.example.joanfluviamarin.restaurantappcuina;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {

    private List<InfoPlat> llista;
    private Adapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        llista=new ArrayList<>();

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


        public ViewHolder(View itemView) {
            super(itemView);
            this.nomView=itemView.findViewById(R.id.nomView);
            this.headerView=itemView.findViewById(R.id.headerView);
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
            //holder.preuView.setText(Double.toString(infoPlatItem.getPreu()));
        }
    }

    private void onClickPlat(int pos) {}
    public void onClickComanda(View view) {
    finish();
    }

}
