package presenter;

import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.presenter.InputPresenter;
import it.unimi.di.sweng.esame.presenter.SellPresenter;
import it.unimi.di.sweng.esame.view.InputView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.Mockito.*;

public class PresenterTest {


    @ParameterizedTest
    @CsvSource({
            "A;B",
            "A00;B",
            "00;B"
    })
    void testCodiceLibroNotOk(String codice){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action(codice,"");
        verify(view).showError("Invalid book code");
    }

    @Test
    void testTitoloVuoto(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201;","");
        verify(view).showError("Il titolo non può essere vuoto");
    }

    @Test
    void numeroParametriNonValido(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201,Ciao","");
        verify(view).showError("Formato non corretto");
    }

    @Test
    void testMoneyNonCorretto(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201;Ciao","-12;ASNEW");
        verify(view).showError("Quantità denaro negativa");
    }

    @Test
    void viewShowSuccessOk(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201;Ciao","12;ASNEW");
        verify(view).showSuccess();
    }

    @Test
    void statoNonValido(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201;Ciao","12;ciao");
        verify(view).showError("Stato libro non valido");
    }

    @Test
    void moneySuccessWithCents(){
        InputView view = mock(InputView.class);
        InputPresenter SUT = new SellPresenter(view,mock(State.class));
        SUT.action("H201;Ciao","12.30;ASNEW");
        verify(view).showSuccess();
    }

}
