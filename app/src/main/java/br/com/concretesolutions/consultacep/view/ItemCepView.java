package br.com.concretesolutions.consultacep.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.concretesolutions.consultacep.R;
import br.com.concretesolutions.consultacep.modelo.ModeloCEP;

@EViewGroup(R.layout.view_item_cep)
public class ItemCepView extends RelativeLayout {

    @ViewById
    TextView numeroCep, logradouro, cidade, estado;

    public ItemCepView(Context context) {
        super(context);
    }

    public ItemCepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemCepView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ItemCepView bind(ModeloCEP cep) {

        numeroCep.setText(cep.getCep());
        logradouro.setText(cep.getLogradouro());
        cidade.setText(cep.getCidade());
        estado.setText(cep.getEstado());

        return this;
    }
}
