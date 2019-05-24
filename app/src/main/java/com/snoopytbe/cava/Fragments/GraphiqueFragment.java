package com.snoopytbe.cava.Fragments;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.snoopytbe.cava.Classes.commun.etat_angoisse;
import static com.snoopytbe.cava.Classes.commun.etat_energie;
import static com.snoopytbe.cava.Classes.commun.etat_humeur;
import static com.snoopytbe.cava.Classes.commun.etat_irritabilite;

public class GraphiqueFragment extends FragmentCaVa {

    @BindView(R.id.chart)
    LineChart chart;
    private XAxis xAxis;
    private YAxis yAxis;
    private List<etat> etats = new ArrayList<etat>();

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_graphique;
    }

    LineDataSet getLineDataSetHumeur(String humeur, int color) {

        ArrayList<Entry> values = new ArrayList<>();

        float xValue = -1;
        float yValue;

        for (int i = 0; i < etats.size(); i++) {

            xValue = xValue + 1;
            yValue = etats.get(i).getHumeurMatin().getHumeurFromTag(humeur);

            values.add(new Entry(xValue, yValue));

            xValue = xValue + 1;
            yValue = etats.get(i).getHumeurApresMidi().getHumeurFromTag(humeur);

            values.add(new Entry(xValue, yValue));

            xValue = xValue + 1;
            yValue = etats.get(i).getHumeurSoir().getHumeurFromTag(humeur);

            values.add(new Entry(xValue, yValue));
        }

        LineDataSet set = new LineDataSet(values, humeur);

        // draw dashed line
        set.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set.setColor(color);

        // line thickness and point size
        set.setLineWidth(1f);
        set.setDrawCircles(false);

        return set;
    }

    @Override
    protected void LoadEtatInUI() {
        chart.setTouchEnabled(false);

        chart.setAutoScaleMinMaxEnabled(true);

        xAxis = chart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);

        yAxis = chart.getAxisLeft();
        chart.getAxisRight().setEnabled(false);
        yAxis.enableGridDashedLine(10f, 10f, 0f);

        /*ArrayList<Entry> values = new ArrayList<>();
        float xValue = 0;
        float yValue;

        for (int i = 0; i < etats.size(); i++) {
            // Sommeil
            xValue=i;
            yValue= (float) etats.get(i).getQualiteSommeil().getHeuresSommeil().getHeures()+ (float) etats.get(i).getQualiteSommeil().getHeuresSommeil().getMinutes() / 60f;
            values.add(new Entry(xValue, yValue));
        }*/

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(getLineDataSetHumeur(etat_humeur, Color.BLUE));
        dataSets.add(getLineDataSetHumeur(etat_angoisse, Color.RED));
        dataSets.add(getLineDataSetHumeur(etat_energie, Color.GREEN));
        dataSets.add(getLineDataSetHumeur(etat_irritabilite, Color.BLACK));


        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        chart.setData(data);

    }

    public void setEtats(List<etat> etats) {
        this.etats = etats;
    }

    @Override
    protected void SaveEtatFromUI() {

    }
}
