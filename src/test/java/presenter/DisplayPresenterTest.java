package presenter;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.model.*;
import it.unimi.di.sweng.esame.presenter.DisplayPresenter;
import it.unimi.di.sweng.esame.view.OutputView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DisplayPresenterTest {

    @Test
    void testUpdateView(){
        Model model = mock(Model.class);
        OutputView outputView = mock(OutputView.class);
        DisplayPresenter SUT = new DisplayPresenter(outputView,model);

        Libro l1 = new Libro(new CodiceLibro("A202"),"Follia", Money.parse("15.60"), StatoLibro.ASNEW);
        Libro l2 = new Libro(new CodiceLibro("A312"),"Follia", Money.parse("15.60"), StatoLibro.ASNEW);
        List<Libro> libri = new ArrayList<>(
        );
        libri.add(l1);
        libri.add(l2);

        when(model.getState()).thenReturn(libri);
        SUT.update(model);
        verify(outputView,times(5)).set(anyInt(),anyString());
    }
}
