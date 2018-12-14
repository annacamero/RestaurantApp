package com.example.joanfluviamarin.restaurantappcuina;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class ComandaActivity extends AppCompatActivity {

    private List<Comanda> llista5;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        Comanda comanda1=new Comanda("espagueti","2","5","2",false);
        Comanda comanda2=new Comanda("espagueti2","1","9","2",true);
        Comanda comanda3=new Comanda("espagueti3","4","1","2",false);
        Comanda comanda4=new Comanda("espagueti4","7","6","2",false);
        Comanda comanda5=new Comanda("espagueti5","9","3","2",false);

        llista5=new ArrayList<>();
        llista5.add(comanda1);
        llista5.add(comanda2);
        llista5.add(comanda3);
        llista5.add(comanda4);
        llista5.add(comanda5);



        RecyclerView recyclerViewMenu=findViewById(R.id.RecyclerViewComanda);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenu.setAdapter(new Adapter());
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
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Comanda comandaItem=llista5.get(position);

            holder.taulaView.setText(comandaItem.getTaula());
            holder.codiPlatView.setText(comandaItem.getReferencia());
            holder.nomPlatView.setText(comandaItem.getNom());
            holder.quantPlatView.setText(comandaItem.getQuantitat());
            holder.checkBoxView.setChecked(comandaItem.isFet());
        }


    }

}
