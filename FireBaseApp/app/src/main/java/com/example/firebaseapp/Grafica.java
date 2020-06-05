package com.example.firebaseapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class Grafica extends AppCompatActivity {
    private int conter=0;
    private int datos[];
    private BarChart barChart;
    private String []meses={"Acelerometro","Gravedad","Luminosidad","Magnetismo","Pasos","Proximidad"};

    private int[]colores={Color.RED,Color.BLUE,Color.GREEN,Color.BLACK,Color.YELLOW,Color.DKGRAY};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        datos=getIntent().getExtras().getIntArray("datos");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graficas);

        barChart=(BarChart)findViewById(R.id.Barchart);
        createCharts();
    }

    private Chart getSameChart(Chart chart, String description, int textColor, int backgroundColor, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(backgroundColor);
        chart.animateY(animateY);

        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0;i<meses.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=colores[i];
            entry.label=meses[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry>entries=new ArrayList<>();
        for (int i=0;i<datos.length;i++){
            entries.add(new BarEntry(i,datos[i]));
        }
        return entries;
    }
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(meses));
    }
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        barChart=(BarChart)getSameChart(barChart,"",Color.RED,Color.GRAY,3000);
        Log.i("m", Arrays.toString(datos));
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colores);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;

    }



}
