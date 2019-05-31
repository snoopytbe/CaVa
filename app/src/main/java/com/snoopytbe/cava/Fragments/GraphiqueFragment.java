package com.snoopytbe.cava.Fragments;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.snoopytbe.cava.AbstractFragments.FragmentCaVa;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private ArrayList<Float> MoyenneMobile(ArrayList<Float> valeurs, int taille) {
        ArrayList<Float> result = new ArrayList<>();

        if (taille > valeurs.size()) return valeurs;
        if (taille == 0) return valeurs;

        float somme = 0;

        for (int i = 0; i < taille; i++) {
            somme += valeurs.get(i);
            result.add(somme / (i + 1));
        }

        for (int i = taille; i < valeurs.size(); i++) {
            somme += valeurs.get(i) - valeurs.get(i - taille);
            result.add(somme / taille);
        }

        return result;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_graphique;
    }

    LineDataSet getLineDataSetHumeur(String humeur, int color) {

        int tailleMoyenneMobile = 6;

        ArrayList<Float> yValues = new ArrayList<>();
        ArrayList<Float> xValues = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = etats.size() - 1; i >= 0; i--) {
            calendar.setTimeInMillis(etats.get(i).getDate());

            calendar.set(Calendar.HOUR, 8);
            xValues.add((float) calendar.getTimeInMillis());
            yValues.add((float) etats.get(i).getHumeurMatin().getHumeurFromTag(humeur));

            calendar.set(Calendar.HOUR, 12);
            xValues.add((float) calendar.getTimeInMillis());
            yValues.add((float) etats.get(i).getHumeurApresMidi().getHumeurFromTag(humeur));

            calendar.set(Calendar.HOUR, 18);
            yValues.add((float) etats.get(i).getHumeurSoir().getHumeurFromTag(humeur));
            xValues.add((float) calendar.getTimeInMillis());
        }
        yValues = MoyenneMobile(yValues, tailleMoyenneMobile);

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = tailleMoyenneMobile - 1; i < yValues.size() - 3; i++) {
            values.add(new Entry(xValues.get(i), yValues.get(i)));
        }

        LineDataSet set = new LineDataSet(values, humeur);

        //set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        //set.setCubicIntensity(1.5f);

        // draw dashed line
        set.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set.setColor(color);

        // line thickness and point size
        set.setLineWidth(1f);
        set.setDrawCircles(false);
        //set.setAxisDependency(YAxis.AxisDependency.LEFT);

        return set;
    }

    LineDataSet getLineDataSetSommeil(int color) {

        int tailleMoyenneMobile = 3;

        ArrayList<Float> yValues = new ArrayList<>();
        ArrayList<Float> xValues = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (int i = etats.size() - 1; i >= 0; i--) {
            calendar.setTimeInMillis(etats.get(i).getDate());
            xValues.add((float) calendar.getTimeInMillis());
            yValues.add((float) etats.get(i).getQualiteSommeil().getHeuresSommeil().getHeures() + (float) etats.get(i).getQualiteSommeil().getHeuresSommeil().getMinutes() / 60f);
        }
        yValues = MoyenneMobile(yValues, tailleMoyenneMobile);

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = tailleMoyenneMobile - 1; i < yValues.size(); i++) {
            values.add(new Entry(xValues.get(i), yValues.get(i)));
        }

        LineDataSet set = new LineDataSet(values, "Sommeil");

        // draw dashed line
        set.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set.setColor(color);

        // line thickness and point size
        set.setLineWidth(1f);
        set.setDrawCircles(false);
        //set.setAxisDependency(YAxis.AxisDependency.RIGHT);

        return set;
    }

    @Override
    protected void LoadEtatInUI() {
        if (!(chart == null)) {
            chart.setTouchEnabled(true);


            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            dataSets.add(getLineDataSetHumeur(etat_humeur, Color.BLUE));
            dataSets.add(getLineDataSetHumeur(etat_angoisse, Color.RED));
            dataSets.add(getLineDataSetHumeur(etat_energie, Color.GREEN));
            dataSets.add(getLineDataSetHumeur(etat_irritabilite, Color.BLACK));
            //dataSets.add(getLineDataSetSommeil(Color.MAGENTA));

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);

            // no description
            chart.getDescription().setEnabled(false);

            Legend l = chart.getLegend();
            l.setEnabled(true);

            chart.setAutoScaleMinMaxEnabled(true);

            xAxis = chart.getXAxis();
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setValueFormatter(new ValueFormatter() {

                private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM H'h'", Locale.getDefault());

                @Override
                public String getFormattedValue(float value) {
                    //long millis = TimeUnit.HOURS.toMillis((long) value);
                    return mFormat.format(new Date((long) value));
                }
            });

            yAxis = chart.getAxisLeft();
            chart.getAxisRight().setEnabled(false);
            yAxis.enableGridDashedLine(10f, 10f, 0f);
            //yAxis.setAxisMaximum(6);
            //yAxis.setAxisMinimum(0);
        }

    }

    public void setEtats(List<etat> etats) {
        this.etats = etats;
        LoadEtatInUI();
    }

    @Override
    protected void SaveEtatFromUI() {

    }
}
