package br.com.concretesolutions.consultacep.dialog;

import android.app.DialogFragment;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import br.com.concretesolutions.consultacep.R;

@EFragment(R.layout.dialog_progress)
public class ProgressDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @AfterViews
    public void afterViews() {
        setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
    }
}