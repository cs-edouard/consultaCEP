package br.com.concretesolutions.consultacep.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import br.com.concretesolutions.consultacep.view.ItemCepView_;
import br.com.concretesolutions.consultacep.PesquisaActivity;
import br.com.concretesolutions.consultacep.modelo.ModeloCEP;
import br.com.concretesolutions.consultacep.view.ItemCepView;

@EBean
public class ListaCepAdapter extends BaseAdapter {

    @RootContext
    PesquisaActivity activity;

    @Override
    public int getCount() {
        return activity.getListaDeCeps().size();
    }

    @Override
    public ModeloCEP getItem(int position) {
        return activity.getListaDeCeps().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = ItemCepView_.build(activity);

        return ((ItemCepView) convertView).bind(getItem(position));
    }
}
