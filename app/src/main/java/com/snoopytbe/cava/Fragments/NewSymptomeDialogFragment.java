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
import android.widget.EditText;

import com.snoopytbe.cava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSymptomeDialogFragment extends DialogFragment {

    protected static String ARG_MOMENT = "Moment";
    protected String moment;
    protected NewSymptomeDialogFragmentCallback activityCallback;
    @BindView(R.id.dns_commentaire)
    EditText commentaire;

    public static NewSymptomeDialogFragment newInstance(String moment) {
        NewSymptomeDialogFragment fragment = new NewSymptomeDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MOMENT, moment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moment = (String) getArguments().getSerializable(ARG_MOMENT);
        }
    }

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewSymptomeDialogFragmentCallback)
            activityCallback = (NewSymptomeDialogFragmentCallback) context;
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

    protected int getFragmentLayout() {
        return R.layout.dialogfragment_new_symtome;
    }

    protected void LoadDatainUI() {
    }

    protected void SaveDataofUI() {
    }

    @OnClick(R.id.dns_boutonOK)
    public void Ok() {

        if (activityCallback != null)
            activityCallback.onOkNewSymptomeDialogFragment(this.commentaire.getText().toString(), moment);
        this.dismiss();
    }

    @OnClick(R.id.dns_boutonCancel)
    public void Bye() {
        this.dismiss();
    }

    public interface NewSymptomeDialogFragmentCallback {
        void onOkNewSymptomeDialogFragment(String newSymptome, String moment);
    }


}
