package com.fillingapps.ordering.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.util.Utils;

import java.lang.ref.WeakReference;

public class SetPlateNotesDialogFragment extends DialogFragment {

    protected WeakReference<OnPlateNotesSetListener> mOnPlateNotesSetListener;

    protected EditText mPlateNotes;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_set_plate_notes, null);
        dialog.setView(dialogView);

        dialog.setTitle(R.string.plate_notes_dialog_title);

        dialog.setPositiveButton(android.R.string.ok, null);
        dialog.setNegativeButton(android.R.string.cancel, null);

        mPlateNotes = (EditText) dialogView.findViewById(R.id.plate_notes);
        return dialog.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnPlateNotesSetListener != null && mOnPlateNotesSetListener.get() != null) {
                        mOnPlateNotesSetListener.get().onPlateNotesSetListener(SetPlateNotesDialogFragment.this, mPlateNotes.getText().toString());
                    }
                }
            });

            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnPlateNotesSetListener != null && mOnPlateNotesSetListener.get() != null) {
                        mOnPlateNotesSetListener.get().onPlateNotesCancelListener(SetPlateNotesDialogFragment.this);
                    }
                }
            });
        }
    }

    public void setOnPlatesNotesSetListener(OnPlateNotesSetListener onPlateNotesSetListener) {
        mOnPlateNotesSetListener = new WeakReference<>(onPlateNotesSetListener);
    }

    public interface OnPlateNotesSetListener {
        void onPlateNotesSetListener(SetPlateNotesDialogFragment dialog, String plateNotes);
        void onPlateNotesCancelListener(SetPlateNotesDialogFragment dialog);
    }
}
