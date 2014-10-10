package br.com.concretesolutions.consultacep.controller;

import android.util.Log;

import com.google.gson.Gson;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;

import br.com.concretesolutions.consultacep.PesquisaActivity;
import br.com.concretesolutions.consultacep.modelo.ModeloCEP;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;

@EBean
public class CepController implements ErrorHandler {

    private static final String TAG = CepController.class.getSimpleName();

    @RootContext
    PesquisaActivity activity;

    private ConsultaCEPApi api;

    @AfterInject
    void afterInject() {

        api = new RestAdapter.Builder()
                .setConverter(new GsonConverter(new Gson()))
                .setClient(new OkClient())
                .setEndpoint("http://correiosapi.apphb.com")
                .setErrorHandler(this)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build().create(ConsultaCEPApi.class);
    }

    @Background
    public void buscaCep(String cep) {

        try {

            final ModeloCEP modeloCEP = api.buscaCep(cep);
            atualiza(modeloCEP);

        } catch (Exception e){
            Log.e(TAG, "Erro ao procurar CEP", e);
        }
    }

    @UiThread
    void atualiza(ModeloCEP modeloCEP) {
        activity.atualizaTelaComResultado(modeloCEP);
    }

    @Override
    public Throwable handleError(RetrofitError cause) {

        if(cause.getKind() == RetrofitError.Kind.NETWORK)
            activity.trataErrosDeConexao();

        else if (cause.getKind() == RetrofitError.Kind.HTTP)
            activity.trataErrosDeHTTP(cause.getResponse().getStatus());

        return cause;
    }

    public static interface ConsultaCEPApi {

        @GET("/cep/{cep}")
        ModeloCEP buscaCep(@Path("cep") String cep);
    }
}
