package com.snoopytbe.cava.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.NumberPicker;

import com.snoopytbe.cava.Classes.HeuresMinutes;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SommeilDialog extends Dialog {

    @BindView(R.id.dhs_hour)
    NumberPicker heure;
    private HeuresMinutes heuresMinutes;
    private SommeilDialogCallback activityCallback;

    public SommeilDialog(Context context) {
        super(context);
        if (context instanceof SommeilDialogCallback)
            activityCallback = (SommeilDialogCallback) context;
    }

    public static SommeilDialog newInstance(Context context, HeuresMinutes heuresMinutes) {
        SommeilDialog dialog = new SommeilDialog(context);
        dialog.heuresMinutes = heuresMinutes;
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_heures_sommeil);

        ButterKnife.bind(this);

        LoadUI();
    }

    private void LoadUI() {

        String[] heuresAffichees = new String[96];
        HeuresMinutes time = new HeuresMinutes();
        int compteur = 0;

        for (int i = 0; i < 24; i++) {
            time.setHeures(i);
            for (int j = 0; j < 60; j += 15) {
                time.setMinutes(j);
                heuresAffichees[compteur] = time.Lisible();
                compteur++;
            }
        }

        heure.setMinValue(0);
        heure.setMaxValue(compteur - 1);
        heure.setDisplayedValues(heuresAffichees);
        heure.setWrapSelectorWheel(true);
        heure.setValue(this.heuresMinutes.getIntQuinzaine());
    }

    @OnClick(R.id.dhs_boutonOK)
    public void Ok() {
        this.heuresMinutes.setIntQuinzaine(heure.getValue());
        if (activityCallback != null)
            activityCallback.onOkSommeilDialog(this.heuresMinutes);
    }

    @OnClick(R.id.dhs_boutonCancel)
    public void Bye() {
        this.dismiss();
    }

    public interface SommeilDialogCallback {
        void onOkSommeilDialog(HeuresMinutes heuresMinutes);
    }
}
