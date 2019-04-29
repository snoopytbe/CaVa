package com.snoopytbe.cava.MethodesListeEtats;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ListeEtatsFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "ListeEtats";
    private ListeEtats listeEtats;
    private ListeEtatsFragmentCallback activityCallback;

    public ListeEtatsFragment() {
    }

    public static ListeEtatsFragment newInstance(ListeEtats listeEtats) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, listeEtats);
        ListeEtatsFragment fragment = new ListeEtatsFragment();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choisir");
        builder.setItems(listeEtats.getStringListeNiveaux().toArray(new CharSequence[listeEtats.getStringListeNiveaux().size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activityCallback.onEtatClicked(which);
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListeEtatsFragment.ListeEtatsFragmentCallback)
            activityCallback = (ListeEtatsFragment.ListeEtatsFragmentCallback) context;
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
