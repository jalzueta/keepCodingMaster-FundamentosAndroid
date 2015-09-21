package com.fillingapps.ordering.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.TextInputLayout;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.util.Utils;

import java.lang.ref.WeakReference;

public class SetFellowsDialogFragment extends DialogFragment {

    protected WeakReference<OnFellowsSetListener> mOnFellowsListener;

    protected EditText mNumberOfFellows;
    protected TextInputLayout mTextInputLayout;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_set_fellows, null);
        dialog.setView(dialogView);

        dialog.setTitle(R.string.number_of_fellows_dialog_title);

        dialog.setPositiveButton(android.R.string.ok, null);
        dialog.setNegativeButton(android.R.string.cancel, null);

        Bundle attrs = getArguments();
        int numberOfFellows = attrs.getInt(TableListFragment.TABLE_NUMBER);

        mNumberOfFellows = (EditText) dialogView.findViewById(R.id.number_of_fellows);
        mNumberOfFellows.setText(String.valueOf(numberOfFellows));
        mTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout);
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
                    if (!Utils.isNumeric(mNumberOfFellows.getText().toString())) {
                        mTextInputLayout.setError(getActivity().getString(R.string.set_fellows_errors_hint));
                    } else if (mOnFellowsListener != null && mOnFellowsListener.get() != null) {
                        mOnFellowsListener.get().onFellowsSetListener(SetFellowsDialogFragment.this, mNumberOfFellows.getText().toString());
                    }
                }
            });

            Button negativeButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnFellowsListener != null && mOnFellowsListener.get() != null) {
                        mOnFellowsListener.get().onFellowsCancelListener(SetFellowsDialogFragment.this);
                    }
                }
            });
        }
    }

    public void setOnFellowsSetListener(OnFellowsSetListener onFellowsSetListener) {
        mOnFellowsListener = new WeakReference<>(onFellowsSetListener);
    }

    public interface OnFellowsSetListener {
        void onFellowsSetListener(SetFellowsDialogFragment dialog, String numberOfFellows);
        void onFellowsCancelListener(SetFellowsDialogFragment dialog);
    }
}
