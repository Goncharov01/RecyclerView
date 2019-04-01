package com.example.recyclerview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class InputDialog extends DialogFragment {

    public static final String ARGUMENT_ID = "id bundle";
    public static final String EXTRA_ID = "extra id";
    public static final String EXTRA_NAME = "extra name";
    public static final String EXTRA_HW_COUNT = "extra hw count";
    public static final String EXTRA_TAG = "extra tag";

    public static final String EDIT_DIALOG_TAG = "Edit dialog";
    public static final String ADD_DIALOG_TAG = "Add dialog";
    private static final String EXCEPTION_MESSAGE = "must implement InputDialogListener";

    private EditText nameEditText;
    private EditText hwCountEditText;
    private InputDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view  = inflater.inflate(R.layout.dialog_info_input, null);

        builder.setView(view)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();

                        if (getTag().equals(EDIT_DIALOG_TAG)) {
                            intent.putExtra(EXTRA_ID, getArguments().getLong(ARGUMENT_ID));
                        }

                        intent.putExtra(EXTRA_NAME, nameEditText.getText().toString());
                        intent.putExtra(EXTRA_HW_COUNT, Integer.valueOf(hwCountEditText.getText().toString()));
                        intent.putExtra(EXTRA_TAG, getTag());

                        listener.applyDialogInfo(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        nameEditText = view.findViewById(R.id.name_edit_text);
        hwCountEditText = view.findViewById(R.id.hw_count_edit_text);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (InputDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + EXCEPTION_MESSAGE);
        }
    }

    public interface InputDialogListener {
        void applyDialogInfo(Intent intent);
    }
}