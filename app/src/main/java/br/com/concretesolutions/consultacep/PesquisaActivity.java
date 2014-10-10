package br.com.concretesolutions.consultacep;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.concretesolutions.consultacep.adapter.ListaCepAdapter;
import br.com.concretesolutions.consultacep.controller.CepController;
import br.com.concretesolutions.consultacep.dialog.InfoDialog_;
import br.com.concretesolutions.consultacep.dialog.ProgressDialog;
import br.com.concretesolutions.consultacep.dialog.ProgressDialog_;
import br.com.concretesolutions.consultacep.modelo.ModeloCEP;
import br.com.concretesolutions.consultacep.util.MascaraEditText;


@EActivity(R.layout.activity_pesquisa)
public class PesquisaActivity extends Activity {

    private static final Pattern PADRAO_CEP = Pattern.compile("[0-9]{5}-[0-9]{3}");

    @ViewById
    EditText inputCep;

    @ViewById
    ListView listaResultados;

    @StringRes
    String cepInvalido, problemasNoRequest, cepNaoEncontrado, erroNoServidor;

    @Bean
    CepController controller;

    @Bean
    ListaCepAdapter adapter;

    private List<ModeloCEP> listaDeCeps = new ArrayList<>();

    private ProgressDialog dialog;

    @AfterViews
    void afterView() {

        // Insere máscara de CEP
        inputCep.addTextChangedListener(MascaraEditText.insert("#####-###", inputCep));
        listaResultados.setAdapter(adapter);
    }

    @Click(R.id.botao_consulta)
    void clickaEmConsultar() {

        String cep = inputCep.getText().toString();

        if (cep.length() < 9 || !PADRAO_CEP.matcher(cep).matches()) {
            Toast.makeText(this, cepInvalido, Toast.LENGTH_LONG).show();
            return;
        }

        mostraDialogoDeProgresso();
        controller.buscaCep(MascaraEditText.unmask(cep));
    }

    public void atualizaTelaComResultado(ModeloCEP cep) {
        escondeDialogoDeProgresso();

        InfoDialog_.builder().infoMessage(cep.toString()).build()
                .show(getFragmentManager(), "display_cep");

        listaDeCeps.add(cep);
        adapter.notifyDataSetChanged();
    }

    public void trataErrosDeConexao() {
        escondeDialogoDeProgresso();
        SemConexaoActivity_.intent(this).start();
    }

    public void trataErrosDeHTTP(int status) {
        escondeDialogoDeProgresso();

        String mensagemId;

        if (status == 400)
            mensagemId = problemasNoRequest;

        else if (status == 404)
            mensagemId = cepNaoEncontrado;

        else
            mensagemId = erroNoServidor;

        InfoDialog_.builder().infoMessage(mensagemId).build()
                .show(getFragmentManager(), "info_dialog");
    }

    public void mostraDialogoDeProgresso() {

        if (isFinishing())
            return;

        if (dialog == null)
            dialog = ProgressDialog_.builder().build();

        dialog.show(getFragmentManager(), "progress_dialog");
    }

    public void escondeDialogoDeProgresso() {

        if (dialog == null || isFinishing())
            return;

        dialog.dismiss();
    }

    public List<ModeloCEP> getListaDeCeps() {
        return listaDeCeps;
    }
}