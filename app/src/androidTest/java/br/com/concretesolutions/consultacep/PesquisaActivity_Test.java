package br.com.concretesolutions.consultacep;

import android.test.ActivityInstrumentationTestCase2;

import java.util.concurrent.TimeUnit;

import br.com.concretesolutions.consultacep.modelo.ModeloCEP;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;


/**
 * Classe de teste básica apenas para mostrar testes usando o espresso
 */
public class PesquisaActivity_Test extends ActivityInstrumentationTestCase2<PesquisaActivity_> {

    public PesquisaActivity_Test() {
        super(PesquisaActivity_.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getActivity(); // inicia a activity
    }

    public void testFazUmaPesquisaDeCEP() throws InterruptedException {

        // Na view que tem o id X
        onView(withId(R.id.input_cep))
                // insere o CEP da Concrete Solutions
                .perform(typeText("04565-001"));

        // Na view que tem o id X
        onView(withId(R.id.botao_consulta))
                // clica
                .perform(click());

        /*
        Vamos usar Thread.sleep (na verdade TimeUnit.X.sleep)
        para esperar o resultado da chamada assíncrona
        Não é o ideal... deveríamos mockar o teste para que ele não faça uma
        chamada HTTP. No entanto, não quero complicar o exemplo.

        Quem quiser procurar: https://github.com/square/retrofit
        Vejam a pasta retrofit-mock. Essa dependência pode ser incluída no projeto.
        No arquivo build.gradle eu deixei a dependência comentada.

        Outra alternativa para quando é impossível fazer o teste sem esperar, seria
        implementar uma ViewAction do Espresso. Um exmeplo de implementação está em:
        http://stackoverflow.com/a/22563297
        */
        // Caso o tempo seja curto... aumente :D
        TimeUnit.SECONDS.sleep(4);

        // aqui já deveria ter o diálogo aparecendo. Vamos confirmar que o botão
        // ok está aparecendo e vamos clicá-lo
        onView(withId(R.id.dialog_info_ok))
                .check(matches(isDisplayed()))
                .perform(click());

        // Vamos garantir que a lista possui pelo menos um item agora

        onData(instanceOf(ModeloCEP.class))
                .atPosition(0)
                .check(matches(isDisplayed()));
    }
}
