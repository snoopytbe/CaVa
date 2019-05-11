package com.snoopytbe.cava.Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.snoopytbe.cava.Classes.etat;

import butterknife.ButterKnife;

public abstract class DialogHeuresMinutes extends DialogFragment {

    protected static String ARG_PARAM1 = "Etat";
    protected DialogHeuresMinutesCallback activityCallback;
    protected etat etat;

    protected abstract int getFragmentLayout();

    protected abstract void LoadDatainUI();

    protected abstract void SaveDataofUI();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, view);
        LoadDatainUI();
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
        if (context instanceof DialogHeuresMinutesCallback)
            activityCallback = (DialogHeuresMinutesCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        SaveDataofUI();
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

    protected void configureNumberPicker(NumberPicker numberPicker, int MinValue, int MaxValue, String[] DisplayedValue, boolean WrapSelectorWheel, int Value) {
        numberPicker.setMinValue(MinValue);
        numberPicker.setMaxValue(MaxValue);
        numberPicker.setDisplayedValues(DisplayedValue);
        numberPicker.setWrapSelectorWheel(WrapSelectorWheel);
        numberPicker.setValue(Value);
    }

    public interface DialogHeuresMinutesCallback {
        void onOkDialogFragmentHeuresMinutes(etat etat);
    }

}
