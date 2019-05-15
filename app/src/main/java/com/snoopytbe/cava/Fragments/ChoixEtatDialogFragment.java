package com.snoopytbe.cava.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.snoopytbe.cava.Classes.ListeEtats;
import com.snoopytbe.cava.R;

public class ChoixEtatDialogFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "ListeEtats";
    private ListeEtats listeEtats;
    private ListeEtatsFragmentCallback activityCallback;

    public ChoixEtatDialogFragment() {
    }

    public static ChoixEtatDialogFragment newInstance(ListeEtats listeEtats) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, listeEtats);
        ChoixEtatDialogFragment fragment = new ChoixEtatDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listeEtats = (ListeEtats) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CaVaDialogTheme);
        //builder.setTitle("Choisir");
        builder.setItems(listeEtats.getStringListeNiveaux().toArray(new CharSequence[listeEtats.getStringListeNiveaux().size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activityCallback.onEtatClicked(which);
            }
        });
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChoixEtatDialogFragment.ListeEtatsFragmentCallback)
            activityCallback = (ChoixEtatDialogFragment.ListeEtatsFragmentCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activityCallback = null;
    }

    public interface ListeEtatsFragmentCallback {
        void onEtatClicked(int numero);
    }

}
