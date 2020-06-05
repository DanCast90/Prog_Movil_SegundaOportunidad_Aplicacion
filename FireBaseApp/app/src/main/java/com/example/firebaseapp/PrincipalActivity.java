package com.example.firebaseapp;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PrincipalActivity extends AppCompatActivity {
    CheckBox ac,grav,lum,mag,pas,prox;
    ArrayList<String> lista;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        // se enlazan los componentes (check box) con el codigo JAVA
        ac=findViewById(R.id.CB_Acelerometro);
        grav=findViewById(R.id.CB_Gravedad);
        lum=findViewById(R.id.CB_Luminosidad);
        mag=findViewById(R.id.CB_Magnetico);
        pas=findViewById(R.id.CB_Pasos);
        prox=findViewById(R.id.CB_Proximidad);
        lista=new ArrayList<>();

    }
    //Metodo OnClick, que se acciona al presionar el boton
    public void onClic(View view){
        //compara si no hay ningun checkBox seleccionado, si esto es verdad
        //manda una alerta haciendo mencion a eso
        if(!ac.isChecked() && !grav.isChecked() && !lum.isChecked() && !mag.isChecked() && !pas.isChecked() && !prox.isChecked()){
            Toast.makeText(getApplicationContext(),"Error, Minimo debe de haber algun sensor seleccionado",Toast.LENGTH_LONG).show();
        //sino, a traves de varios If, se comprueba si esta seleccionado
        }else{
                //depende de cuales sean los check box seleccionados, seran los datos mandados
                if(ac.isChecked()){
                    lista.add("Acelerometro");
                }if(grav.isChecked()){
                    lista.add("Gravedad");
                }if(lum.isChecked()){
                    lista.add("Luminosidad");
                }if(mag.isChecked()){
                    lista.add("Magnetico");
                }if(pas.isChecked()){
                    lista.add("Pasos");
                }if(prox.isChecked()){
                    lista.add("Proximidad");
                }
            //se crea el intent, haciendo referencia a la activity que se quiere abrir
            Intent intent=new Intent(this,MainActivity.class);
                //se le pasan la lista de datos
                intent.putExtra("Lista", lista);
                //se inicia la otra actividad
                startActivity(intent);
        }
    }



}
