package presenter;

import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.presenter.InputPresenter;
import it.unimi.di.sweng.esame.presenter.Presenter;
import it.unimi.di.sweng.esame.views.InputView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.Mockito.*;

public class InputPresenterTest {

    @ParameterizedTest
    @CsvSource({
            "PP02:1:1:1",
            "P002P:1:1:1",
            "00002:1:1:1"
    })
    void testCodiceIstitutoNonValido(String input) {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Inserisci",input);
        verify(inputView).showError("Codice istituto non valido");
    }

    @Test
    void testNumeroCampiNonCorretti() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Inserisci","PP002:1:1");
        verify(inputView).showError("Numero insufficiente di campi");
    }

    @Test
    void testDataInizioNonValido() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Inserisci","PP002:1:1:11-09-2023");
        verify(inputView).showError("Data inizio non corretta");
    }

    @Test
    void testMesiNonValidi() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Inserisci","PP002:-1:1:11/09/2023");
        verify(inputView).showError("La durata deve essere positiva");
    }

    @Test
    void testOkInsert() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Inserisci","PP002:1:LO:11/09/2023");
        verify(inputView).showSuccess();
    }

    @Test
    void testChiamaInserisciModello(){
        State state = mock(State.class);
        InputPresenter SUT = new InputPresenter(mock(InputView.class),state);
        SUT.action("Inserisci","PP002:1:LO:11/09/2023");
        verify(state,times(1)).inserisciSupplenza(any(),anyInt(),anyString(),any());
    }

    @ParameterizedTest
    @CsvSource({
            "PP02:1:1",
            "0002:1:1",
            "P002Q:1:1"
    })
    void testCodiceInsegnanteNonValido(String input) {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Accetta",input);
        verify(inputView).showError("Codice insegnante non valido");
    }

    @Test
    void testNumeroCampiNonCorretti2() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Accetta","P00P:1");
        verify(inputView).showError("Numero insufficiente di campi");
    }

    @Test
    void testAccettaOK() {
        InputView inputView = mock(InputView.class);
        Presenter SUT = new InputPresenter(inputView,mock(State.class));
        SUT.action("Accetta","P00P:PP002:03/09/2023");
        verify(inputView).showSuccess();
    }

    @Test
    void testChiamaModelloAccettaSupplenza(){
        State state = mock(State.class);
        InputPresenter SUT = new InputPresenter(mock(InputView.class),state);
        SUT.action("Accetta","P00P:PP002:11/09/2023");
        verify(state,times(1)).accettaSupplenza(any(),any(),any());
    }
}
