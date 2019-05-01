package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snoopytbe.cava.Classes.etat;
import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JourneeFragment extends Fragment {

    private static final String ARG_PARAM1 = "Etat";
    @BindView(R.id.fpj_date)
    TextView date;
    private JourneeFragmentCallback activityCallback;
    private com.snoopytbe.cava.Classes.etat etat;

    public JourneeFragment() {
    }

    public static JourneeFragment newInstance(etat etat) {
        JourneeFragment fragment = new JourneeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            etat = (etat) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentation_journee, container, false);
        ButterKnife.bind(this, view);
        LoadEtatInUI();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof JourneeFragmentCallback)
            activityCallback = (JourneeFragmentCallback) context;
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

    private void LoadEtatInUI() {
        date.setText(etat.DateLisible());
    }

    private void SaveEtatFromUI() {

    }

    @OnClick(R.id.fpj_cardSommeil)
    public void chargeSommeil() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowSommeilFragment(etat);
    }

    @OnClick(R.id.fpj_cardMatin)
    public void chargeMatin() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Matin");
    }

    @OnClick(R.id.fpj_cardAprem)
    public void chargeAprem() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Aprem");
    }

    @OnClick(R.id.fpj_cardSoir)
    public void chargeSoir() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onChargeEtatClicked(etat, "Soir");
    }

    @OnClick(R.id.fpj_cardTraitement)
    public void chargeTraitement() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.ShowTraitementFragment(etat);
    }

    @OnClick(R.id.fpj_OK)
    public void ok() {
        SaveEtatFromUI();
        if (activityCallback != null)
            activityCallback.onOKFragmentJournee(etat);
    }

    public interface JourneeFragmentCallback {
        void ShowSommeilFragment(etat etat);

        void ShowTraitementFragment(etat etat);
        void onChargeEtatClicked(etat etat, String quand);

        void onOKFragmentJournee(etat etat);
    }
}
