package com.example.joanfluviamarin.restaurantappcuina;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ComandaActivity extends AppCompatActivity {

    private List<Comanda> llista5;
    private int pos;
    Adapter adapter;
    private ImageView logoview;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);



        logoview = findViewById(R.id.logoview);

        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                .into(logoview);



        //Comanda comanda1=new Comanda("espagueti","2","5","2",false);
        //Comanda comanda2=new Comanda("espagueti2","1","9","2",true);
        //Comanda comanda3=new Comanda("espagueti3","4","1","2",false);
        //Comanda comanda4=new Comanda("espagueti4","7","6","2",false);
        //Comanda comanda5=new Comanda("espagueti5","9","3","2",false);

        llista5=new ArrayList<>();

        db.collection("comandes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                llista5.clear();
                for (DocumentSnapshot doc : documentSnapshots) {
                    try {
                        Comanda comanda = doc.toObject(Comanda.class);
                        llista5.add(comanda);
                    } catch (RuntimeException err) {
                        Log.e("RestaurantApp",
                                String.format("Error de conversió a la comanda %s: %s", doc.getId(), err.toString()));
                    }
                    Log.i("RestaurantApp", doc.getString("nom"));
                }
                adapter.notifyDataSetChanged();
            }
        });


        logoview = findViewById(R.id.logoView);
        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                .into(logoview);

        RecyclerView recyclerViewMenu=findViewById(R.id.RecyclerViewComanda);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        recyclerViewMenu.setAdapter(adapter);
    }




    //creem la clase Viewholder
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView taulaView;
        TextView codiPlatView;
        TextView nomPlatView;
        TextView quantPlatView;
        CheckBox checkBoxView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.taulaView=itemView.findViewById(R.id.taulaView);
            this.codiPlatView=itemView.findViewById(R.id.codiPlatView);
            this.nomPlatView=itemView.findViewById(R.id.nomPlatView);
            this.quantPlatView=itemView.findViewById(R.id.quantPlatView);
            this.checkBoxView=itemView.findViewById(R.id.checkBoxView);

            this.checkBoxView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = getAdapterPosition();
                    llista5.get(pos).setFet(isChecked);
                    //adapter.notifyDataSetChanged();
                }
            });
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public int getItemCount() {
            return llista5.size();
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=getLayoutInflater().inflate(R.layout.hold_comanda,parent,false);
            //itemView.setVisibility(View.GONE);
            return new ViewHolder(itemView);
        }


        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                Comanda comandaItem = llista5.get(position);
                //permet amagar els plats marcats com a fets.
                //algo que mosqueja, esque encara que des de firebase es marquin com "no fets", si no es reinicia la App, no tornen a apareixer.
                if(comandaItem.isFet()){
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }
                else {
                    holder.taulaView.setText(Integer.toString(comandaItem.getTaula()));
                    holder.codiPlatView.setText(comandaItem.getReferencia());
                    holder.nomPlatView.setText(comandaItem.getNom());
                    holder.quantPlatView.setText(Integer.toString(comandaItem.getQuantitat()));
                    holder.checkBoxView.setChecked(comandaItem.isFet());
                }
        }


    }

    //al clicar, sobrescriu els fitxers de firebase. la modificació sols afecta a la variable "fet"
    public void onClickAmagar(View view) {

        db.collection("comandes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    pos=0;
                    for(DocumentSnapshot doc : task.getResult()){
                        String position=doc.getId();
                        db.collection("comandes").document(position).set(llista5.get(pos));
                        pos=pos+1;
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        //Toast.makeText(this, Integer.toString(val), Toast.LENGTH_SHORT).show();
    }

    public void onClickCarta(View view) {
        Intent intent =new Intent(this, CartaActivity.class);
        startActivity(intent);
    }
}
