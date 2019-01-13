package com.example.joanfluviamarin.restaurantappcuina;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPlatActivity extends AppCompatActivity {
    int id;
    InfoPlat newPlat;
    Button chooseType;
    String type="Plato";
    EditText editName;
    EditText editDescrp;
    EditText editPrice;
    private ImageView logoview;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plat);

        logoview = findViewById(R.id.logoview);

        Glide.with(this)
                .load ("///android_asset/UMAI2.png")
                .into(logoview);

        //obtenim l'Id del ultim plat de la llista.
        Intent intent=getIntent();
        if(intent!=null){
            id=Integer.parseInt(intent.getStringExtra("Id."))+1;
            //Toast.makeText(this, String.valueof(id), Toast.LENGTH_SHORT).show();

            TextView idView=findViewById(R.id.idView);
            idView.setText("REF.id "+String.valueOf(id));
        }

        editName=findViewById(R.id.editName);
        editDescrp=findViewById(R.id.editDescrp);
        editPrice=findViewById(R.id.editPrice);
        chooseType=findViewById(R.id.btn_chooseType);
        chooseType.setText(type);






    }

    public void onClickChooseType(View view) {
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_type,null);
        dialogBuilder.setView(dialogView);

        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        Button btnPlato=(Button)dialogView.findViewById(R.id.btn_plato);
        btnPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Plato";
                chooseType.setText(type);
                alertDialog.dismiss();
            }
        });
        Button btnBebida=(Button)dialogView.findViewById(R.id.btn_bebida);
        btnBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="Bebida";
                chooseType.setText(type);
                alertDialog.dismiss();
            }
        });


    }
    public void onClickSave(View view) {
        //String name= String.valueOf(editName.getText());

        newPlat=new InfoPlat(String.valueOf(id),String.valueOf(editName.getText()),String.valueOf(editDescrp.getText()),type,Double.valueOf(String.valueOf(editPrice.getText())));

        db.collection("plats").add(newPlat);
        finish();
    }
}
