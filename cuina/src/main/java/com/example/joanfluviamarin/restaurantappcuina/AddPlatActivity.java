package com.example.joanfluviamarin.restaurantappcuina;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlatActivity extends AppCompatActivity {
    int id;
    Button chooseType;
    String type="PLATO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plat);

        //obtenim l'Id del ultim plat de la llista.
        Intent intent=getIntent();
        if(intent!=null){
            id=Integer.parseInt(intent.getStringExtra("Id."))+1;
            //Toast.makeText(this, String.valueof(id), Toast.LENGTH_SHORT).show();

            TextView idView=findViewById(R.id.idView);
            idView.setText("REF.id "+String.valueOf(id));
        }

        EditText editName=findViewById(R.id.editName);
        EditText editDescrp=findViewById(R.id.editDescrp);
        EditText editPrice=findViewById(R.id.editPrice);
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
                type="PLATO";
                chooseType.setText(type);
                alertDialog.dismiss();
            }
        });
        Button btnBebida=(Button)dialogView.findViewById(R.id.btn_bebida);
        btnBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type="BEBIDA";
                chooseType.setText(type);
                alertDialog.dismiss();
            }
        });


    }
}
