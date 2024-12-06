package presenter;

import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.presenter.BuyPresenter;
import it.unimi.di.sweng.esame.presenter.InputPresenter;
import it.unimi.di.sweng.esame.view.InputView;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class BuyPresenterTest {

    @Test
    void testEmailNotOK(){
        InputView inputView = mock(InputView.class);
        InputPresenter SUT = new BuyPresenter(inputView, mock(State.class));
        SUT.action("A202","violalicata@@gmail.com");
        verify(inputView).showError("Formato email errato");
    }


    @Test
    void testChiamaModelBuy(){
        State state = mock(State.class);
        InputView inputView = mock(InputView.class);
        InputPresenter SUT = new BuyPresenter(inputView,state);
        SUT.action("A202","violalicata@gmail.com");
        verify(state,times(1)).buy(any(),any());
    }

    @Test
    void testSuccessBuy2(){
        InputView inputView = mock(InputView.class);
        InputPresenter SUT = new BuyPresenter(inputView, mock(State.class));
        SUT.action("A202","violalicata@gmail.com");
        verify(inputView).showSuccess("Receipt for the book A202 has been sent to violalicata@gmail.com");
    }
}
