package com.vaibhav.apslabproject;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    private DialogListener listener;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box, null);
        EditText distance = view.findViewById(R.id.distance);
        builder.setView(view)
                .setTitle("Enter the distance between two points")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dist = distance.getText().toString();
                        listener.apply(dist);
                    }
                });

        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener)context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement Dialog Listener");
        }
    }

    public interface DialogListener {
        void apply(String distance);
    }
}
