package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity  implements  SensorEventListener {
    int bandera=0;
    int conter=0;
    int datosGrafica[]=new int[6];
    Date date ;
    DecimalFormat formateador= new DecimalFormat("#.00");
    DateFormat hourFormat ;
    DateFormat dateFormat ;
    SensorTres sensorTres;
    SensorUno sensorUno;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    SensorManager sensorManager;
    EditText cajaSearch;
    List<String>listaSensores=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    List<String>listaDatosEntreActities;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //se crea un objeto de tipo Bundle, que recibe los datos de
        //la otra actividad
        Bundle parametros=this.getIntent().getExtras();
        //se guardan en una lista
        listaDatosEntreActities= parametros.getStringArrayList("Lista");
        //se enlaza la listview con el codigo
        listView=findViewById(R.id.listViewObjetos);
        //mandamos llamar el metodo de inicializarFirebase, el cual, inicia
        //el servicio de Firebase
        inicializarFirebase();
        //Se traen los datos de Firebase dependiendo la "tabla"
        //con el metodo listaDatos
        listaDatos("Gravedad");
        listaDatos("Acelerometro");
        listaDatos("Luminosidad");
        listaDatos("Magnetico");
        listaDatos("Pasos");
        listaDatos("Proximidad");
        //esta bandera se utilizara en otro metodo
        bandera=1;
        //se enlaza la caja de texto con codigo
        cajaSearch=findViewById(R.id.txt_busqueda);
        //se le añade un KeyListener para filtrar en la lista
        cajaSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //se usa este metodo, ya que es el necesario
            //por que realiza el filtro cada vez que el texto cambia
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //infla el menu para ponerlo en la interfaz
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //metodo para controlar el evento de back del celular
    @Override
    public void onBackPressed() {
        // si no se ha presionado entra aqui
        if(conter==0){
            //avisa si es que se quiere salir y aumenta el contador
            Toast.makeText(getApplicationContext(),"Presione nuevamente para salir",Toast.LENGTH_LONG).show();
            conter++;
            //sino
        }else{
            // se crea un intent para abrir la activity anterior
            Intent i=new Intent(this,PrincipalActivity.class);
            startActivity(i);
        }
        //es necesario añadir un contador, el cual contara 3000 milisegundos en intervalos de 1000
        //esto con el fin de que si no se volvio a dar back antes de 3 segundos
        // se vuelva a reiniciar el contador
        new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) { }
            @Override
            public void onFinish() {
                // al finalizar el conteo, el contador regresa a 0
                conter=0;
            }
        }.start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //es un switch, el cual, selecciona el boton
        // que se ha presionado y realiza ciertas acciones
        switch (item.getItemId()){
            case R.id.icon_add:
                Toast.makeText(getApplicationContext(),"Comenzo la lectura de datos",Toast.LENGTH_LONG).show();
                agregar();
                break;
            case R.id.icon_stop:
                detener();
                Toast.makeText(getApplicationContext(),"Se detuvo la lectura de datos",Toast.LENGTH_LONG).show();
                break;
            case R.id.icon_clean:
                Toast.makeText(getApplicationContext(),"Lista limpia",Toast.LENGTH_LONG).show();
                limpiar();
                break;
            case R.id.icon_update:
                actualizar();
                Toast.makeText(getApplicationContext(),"Se actualizo la lista de datos",Toast.LENGTH_LONG).show();
                break;
            case R.id.icon_chart:
                Toast.makeText(getApplicationContext(),"Se abrirá la gráfica",Toast.LENGTH_LONG).show();
                abrirGraf();
                break;
            default:
                break;
        }
        return true;
    }

    public void abrirGraf(){
        Intent i=new Intent(this,Grafica.class);
        i.putExtra("datos",datosGrafica);
        startActivity(i);

    }


    private void listaDatos(final String sensor) {
        //se añade un listener para iterar sobre una de las "tablas"
        databaseReference.child(sensor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //se itera en esa tabla
                for (DataSnapshot obj: dataSnapshot.getChildren()){
                    //se compara si la "tabla" es la de Gravedad
                    if(sensor.equalsIgnoreCase("Gravedad")){
                        //se obtiene el dato y se guarda en la clase SensorTres
                        SensorTres s=obj.getValue(SensorTres.class);
                        //se añade un item a la lista del adaptador
                        listaSensores.add(s.toStringGravedad());
                        // se crea el adaptador del listview
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        // se añade el adaptador a la lista
                        listView.setAdapter(arrayAdapter);
                        // se añade uno mas al contador, para saber cuantos datos
                        //hay de este sensor y poderse graficar
                        datosGrafica[1]++;
                        //se hace lo mismo con todos los sensores
                    }if(sensor.equalsIgnoreCase("Acelerometro")){
                        SensorTres s=obj.getValue(SensorTres.class);
                        listaSensores.add(s.toStringAcele());
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        listView.setAdapter(arrayAdapter);
                        datosGrafica[0]++;
                    }if(sensor.equalsIgnoreCase("Luminosidad")){
                        SensorUno s=obj.getValue(SensorUno.class);
                        listaSensores.add(s.toStringLuminosidad());
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        listView.setAdapter(arrayAdapter);
                        datosGrafica[2]++;
                    }if(sensor.equalsIgnoreCase("Magnetico")){
                        SensorTres s=obj.getValue(SensorTres.class);
                        listaSensores.add(s.toStringMagnetico());
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        listView.setAdapter(arrayAdapter);
                        datosGrafica[3]++;
                    }if(sensor.equalsIgnoreCase("Pasos")){
                        SensorUno s=obj.getValue(SensorUno.class);
                        listaSensores.add(s.toStringPasos());
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        listView.setAdapter(arrayAdapter);
                        datosGrafica[4]++;
                    }if(sensor.equalsIgnoreCase("Proximidad")){
                        SensorUno s=obj.getValue(SensorUno.class);
                        listaSensores.add(s.toStringProximidad());
                        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
                        listView.setAdapter(arrayAdapter);
                        datosGrafica[5]++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void limpiar(){
        listaSensores.clear();
        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
        listView.setAdapter(arrayAdapter);
    }
    public void actualizar(){
        listaSensores.clear();
        arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, listaSensores);
        listView.setAdapter(arrayAdapter);
        listaDatos("Gravedad");
        listaDatos("Acelerometro");
        listaDatos("Luminosidad");
        listaDatos("Magnetico");
        listaDatos("Pasos");
        listaDatos("Proximidad");
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    public void detener(){
        sensorManager.unregisterListener(this);
    }


    public void agregar(){
        //se crea un sensor manager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores;
        //se recorre la lista a traves de un for
        for(int i=0; i< listaDatosEntreActities.size();i++){
                //si en la lista esta este sensor, se le registra el Listener
            if(listaDatosEntreActities.get(i).equalsIgnoreCase("Acelerometro")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
                if (!listaSensores.isEmpty()) {
                    Sensor acelerometerSensor = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, acelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);}
                //si en la lista esta este sensor, se le registra el Listener
            }if(listaDatosEntreActities.get(i).equalsIgnoreCase("Gravedad")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_GRAVITY);
                if (!listaSensores.isEmpty()) {
                    Sensor gravedad = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, gravedad, SensorManager.SENSOR_DELAY_NORMAL);}
                //si en la lista esta este sensor, se le registra el Listener
            }if(listaDatosEntreActities.get(i).equalsIgnoreCase("Luminosidad")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
                if (!listaSensores.isEmpty()) {
                    Sensor lightSensor = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);}
                //si en la lista esta este sensor, se le registra el Listener
            }if(listaDatosEntreActities.get(i).equalsIgnoreCase("Magnetico")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
                if (!listaSensores.isEmpty()) {
                    Sensor magneticSensor = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);}
            }if(listaDatosEntreActities.get(i).equalsIgnoreCase("Pasos")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER);
                if (!listaSensores.isEmpty()) {
                    Sensor pasos = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, pasos, SensorManager.SENSOR_DELAY_NORMAL);}
                //si en la lista esta este sensor, se le registra el Listener
            }if(listaDatosEntreActities.get(i).equalsIgnoreCase("Proximidad")){
                listaSensores = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
                if (!listaSensores.isEmpty()) {
                    Sensor proximitySensor = listaSensores.get(0);
                    sensorManager.registerListener((SensorEventListener) this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);}
            }
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //la bandera (que maneja el ciclo de vida de la app)
        //está en 1
        if(bandera==1) {
            //se sincroniza
            synchronized (this) {
                //en un switch que verifica si el sensor tiene evento o no
                String x, y, z;
                date = new Date();
                hourFormat = new SimpleDateFormat("HH:mm:ss");
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                formateador = new DecimalFormat("#.00");
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        //se toman los datos del sensor
                        x = formateador.format(event.values[0]);
                        y = formateador.format(event.values[1]);
                        z = formateador.format(event.values[2]);
                        //se guardan en una clase llamada SensorTres,
                        //la cual nada mas almacena temporalmente los datos
                        sensorTres = new SensorTres(UUID.randomUUID().toString(), x, y, z, hourFormat.format(date), dateFormat.format(date));
                        //se añade el dato en la base de datos en la "tabla correspondiente"
                        databaseReference.child("Acelerometro").child(sensorTres.getId()).setValue(sensorTres);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        //de la misma manera que el case anterior, asi funcionan cada uno
                        x = formateador.format(event.values[0]);
                        y = formateador.format(event.values[1]);
                        z = formateador.format(event.values[2]);
                        sensorTres = new SensorTres(UUID.randomUUID().toString(), x, y, z, hourFormat.format(date), dateFormat.format(date));
                        databaseReference.child("Gravedad").child(sensorTres.getId()).setValue(sensorTres);
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        x = formateador.format(event.values[0]);
                        y = formateador.format(event.values[1]);
                        z = formateador.format(event.values[2]);
                        sensorTres = new SensorTres(UUID.randomUUID().toString(), x, y, z, hourFormat.format(date), dateFormat.format(date));
                        databaseReference.child("Magnetico").child(sensorTres.getId()).setValue(sensorTres);
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        x = formateador.format(event.values[0]);
                        sensorUno = new SensorUno(UUID.randomUUID().toString(), x, hourFormat.format(date), dateFormat.format(date));
                        databaseReference.child("Proximidad").child(sensorUno.getId()).setValue(sensorUno);
                        break;
                    case Sensor.TYPE_LIGHT:
                        x = formateador.format(event.values[0]);
                        sensorUno = new SensorUno(UUID.randomUUID().toString(), x, hourFormat.format(date), dateFormat.format(date));
                        databaseReference.child("Luminosidad").child(sensorUno.getId()).setValue(sensorUno);
                        break;
                    case Sensor.TYPE_STEP_COUNTER:
                        x = formateador.format(event.values[0]);
                        sensorUno = new SensorUno(UUID.randomUUID().toString(), x, hourFormat.format(date), dateFormat.format(date));
                        databaseReference.child("Pasos").child(sensorUno.getId()).setValue(sensorUno);
                        break;
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        bandera=1;
    }

    @Override
    protected void onPause() {
        super.onPause();
        bandera=0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        bandera=0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        bandera=1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bandera=0;
    }
}
