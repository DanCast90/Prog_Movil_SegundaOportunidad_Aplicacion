package com.example.firebaseapp;

public class SensorUno {
    private String id;
    private String x;

    private String hora;
    private String fecha;

    public SensorUno() {
    }

    public SensorUno(String id, String x,  String hora, String fecha) {
        this.id = id;
        this.x = x;

        this.hora = hora;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "";
    }
    public String toStringHumedad(){
        return "Tipo de sensor: Humedad \n" +
                "Valor de la Humedad: "+getX()+" %\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

    public String toStringLuminosidad(){
        return "Tipo de sensor: Luminosidad \n" +
                "Valor de la Luminosidad: "+getX()+" lx\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

    public String toStringPasos(){
        return "Tipo de sensor: Pasos \n" +
                "Pasos desde la ultima lectura: "+getX()+"\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

    public String toStringProximidad(){
        return "Tipo de sensor: Proximidad \n" +
                "Distancia: "+getX()+" mts\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

}
