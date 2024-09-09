package presenter;

import it.unimi.di.sweng.esame.model.CodiceIstituto;
import it.unimi.di.sweng.esame.model.ModelObservable;
import it.unimi.di.sweng.esame.model.Supplenze;
import it.unimi.di.sweng.esame.presenter.DisplayPresenter;
import it.unimi.di.sweng.esame.presenter.StrategyOrdinamentoAndPrint;
import it.unimi.di.sweng.esame.views.OutputView;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DisplayPresenterTest {

    @Test
    void testUpdateView(){
        OutputView outputView = mock(OutputView.class);
        ModelObservable model = mock(ModelObservable.class);
        StrategyOrdinamentoAndPrint strategy = mock(StrategyOrdinamentoAndPrint.class);
        DisplayPresenter SUT = new DisplayPresenter(model,strategy ,outputView);
        SUT.update(model);

        List<Supplenze> supplenze = new ArrayList<>();
        supplenze.add(new Supplenze(new CodiceIstituto("PP002"),4,"LO", LocalDate.now()));

        when(model.getState()).thenReturn(supplenze);
        when(strategy.formatPrint(model.getState().get(0))).thenReturn("PP002");
        verify(outputView,times(8)).set(anyInt(),anyString());

    }


}
