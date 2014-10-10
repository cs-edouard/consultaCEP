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

import java.util.regex.Pattern;

import br.com.concretesolutions.consultacep.controller.CepController;
import br.com.concretesolutions.consultacep.dialog.ProgressDialog;
import br.com.concretesolutions.consultacep.dialog.ProgressDialog_;
import br.com.concretesolutions.consultacep.modelo.ModeloCEP;
import br.com.concretesolutions.consultacep.util.MascaraEditText;


@EActivity(R.layout.activity_pesquisa)
public class SemConexaoActivity extends Activity {}