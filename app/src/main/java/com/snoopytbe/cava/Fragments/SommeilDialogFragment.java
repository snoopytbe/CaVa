package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.snoopytbe.cava.Classes.HeuresMinutes;
import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SommeilDialogFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "Etat";
    @BindView(R.id.dhs_coucher)
    NumberPicker coucher;
    @BindView(R.id.dhs_lever)
    NumberPicker lever;
    @BindView(R.id.dhs_insomnie)
    NumberPicker insomnie;
    private SommeilDialogFragmentCallback activityCallback;
    private etat etat;

    public SommeilDialogFragment() {
    }

    public static SommeilDialogFragment newInstance(etat etat) {
        SommeilDialogFragment fragment = new SommeilDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialogfragment_heures_sommeil, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SommeilDialogFragmentCallback)
            activityCallback = (SommeilDialogFragmentCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        SaveEtatFromUI();
    }

    @Override
    public void onResume() {

        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();

        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        // Set the width of the dialog proportional to 80% of the screen width
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        // Call super onResume after sizing
        super.onResume();

    }

    private void configureNumberPicker(NumberPicker numberPicker, int MinValue, int MaxValue, String[] DisplayedValue, boolean WrapSelectorWheel, int Value) {
        numberPicker.setMinValue(MinValue);
        numberPicker.setMaxValue(MaxValue);
        numberPicker.setDisplayedValues(DisplayedValue);
        numberPicker.setWrapSelectorWheel(WrapSelectorWheel);
        numberPicker.setValue(Value);
    }

    private void LoadEtatInUI() {


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

        this.configureNumberPicker(coucher, 0, compteur - 1, heuresAffichees, true, this.etat.getQualiteSommeil().getHeureCoucher().getIntQuinzaine());

        this.configureNumberPicker(lever, 0, compteur - 1, heuresAffichees, true, this.etat.getQualiteSommeil().getHeureLever().getIntQuinzaine());

        this.configureNumberPicker(insomnie, 0, compteur - 1, heuresAffichees, false, this.etat.getQualiteSommeil().getHeuresInsomnie().getIntQuinzaine());

    }

    private void SaveEtatFromUI() {
    }

    @OnClick(R.id.dhs_boutonOK)
    public void Ok() {
        HeuresMinutes time = new HeuresMinutes();
        time.setIntQuinzaine(lever.getValue());
        this.etat.getQualiteSommeil().setHeureLever(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());
        time.setIntQuinzaine(coucher.getValue());
        this.etat.getQualiteSommeil().setHeureCoucher(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());
        time.setIntQuinzaine(insomnie.getValue());
        this.etat.getQualiteSommeil().setHeuresInsomnie(new HeuresMinutes(time.getHeures(), time.getMinutes()));
        Log.e("Test", "@OnClick(R.id.dhs_boutonOK) : " + etat.getQualiteSommeil().getHeureLever().Lisible());

        if (activityCallback != null)
            activityCallback.onOkSommeilDialogFragment(this.etat);

        this.dismiss();
    }

    @OnClick(R.id.dhs_boutonCancel)
    public void Bye() {
        this.dismiss();
    }

    public interface SommeilDialogFragmentCallback {
        void onOkSommeilDialogFragment(etat etat);
    }

}
