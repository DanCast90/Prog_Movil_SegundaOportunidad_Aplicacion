package com.example.firebaseapp;

public class SensorTres {
    private String id;
    private String x;
    private String y;
    private String z;
    private String hora;
    private String fecha;

    public SensorTres() {
    }

    public SensorTres(String id, String x, String y, String z, String hora, String fecha) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
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

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
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

    public String toStringAcele(){
        return "Tipo de sensor: Acelerómetro \n" +
                "Valor en X: "+getX()+" m/s^2\n"+
                "Valor en Y: "+getY()+" m/s^2\n"+
                "Valor en Z: "+getZ()+" m/s^2\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

    public String toStringGiro(){
        return "Tipo de sensor: Giróscopio \n" +
                "Valor en X: "+getX()+" rad/s\n"+
                "Valor en Y: "+getY()+" rad/s\n"+
                "Valor en Z: "+getZ()+" rad/s\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }

    public String toStringGravedad(){
        return "Tipo de sensor: Gravedad \n" +
                "Valor en X: "+getX()+" rad/s\n"+
                "Valor en Y: "+getY()+" rad/s\n"+
                "Valor en Z: "+getZ()+" rad/s\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }
    public String toStringMagnetico(){
        return "Tipo de sensor: Magnético \n" +
                "Valor en X: "+getX()+" rad/s\n"+
                "Valor en Y: "+getY()+" rad/s\n"+
                "Valor en Z: "+getZ()+" rad/s\n"+
                "Hora de la captura: "+getHora()+"\n" +
                "Fecha de la lectura: "+getFecha();
    }
}
