package com.snoopytbe.cava.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import com.snoopytbe.cava.R;
import com.snoopytbe.cava.db.HeuresMinutes;

public class TimeFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "Heure";
    private HeuresMinutes heure;

    public TimeFragment() {
    }

    public static TimeFragment newInstance(HeuresMinutes heure) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, heure);
        TimeFragment fragment = new TimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            heure = (HeuresMinutes) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(
                getActivity(),
                R.style.CustomDatePickerDialog,
                (TimePickerDialog.OnTimeSetListener) getActivity(),
                heure.getHeures(),
                heure.getMinutes(),
                DateFormat.is24HourFormat(getActivity()));
    }
}
