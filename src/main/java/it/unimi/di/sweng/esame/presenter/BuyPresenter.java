package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.CodiceLibro;
import it.unimi.di.sweng.esame.model.Email;
import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.view.InputView;
import org.jetbrains.annotations.NotNull;

public class BuyPresenter implements InputPresenter{

    private final @NotNull InputView view;

    private final @NotNull State model;

    public BuyPresenter(@NotNull InputView view,@NotNull State state) {
        this.view = view;
        this.model = state;
        view.addHandlers(this);
    }

    @Override
    public void action(@NotNull String codice, @NotNull String email) {
        try{
            model.buy(new CodiceLibro(codice),new Email(email));
            view.showSuccess("Receipt for the book "+codice+" has been sent to "+email);
        }catch (IllegalArgumentException e){
            view.showError(e.getMessage());
        }
    }
}
