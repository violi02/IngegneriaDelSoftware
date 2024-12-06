package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.*;
import it.unimi.di.sweng.esame.view.InputView;
import org.jetbrains.annotations.NotNull;

public class SellPresenter implements InputPresenter{

    private final @NotNull InputView view;

    private final @NotNull State model;
    public SellPresenter(@NotNull InputView view, @NotNull State state) {
        this.view = view;
        this.model = state;
        view.addHandlers(this);
    }

    @Override
    public void action(@NotNull String text, @NotNull String text1) {
        try {
            String[] primo = text.split(";",2);
            if (primo.length != 2) throw new IllegalArgumentException("Formato non corretto");
            CodiceLibro codiceLibro =  new CodiceLibro(primo[0]);
            if (primo[1].isBlank()) throw new IllegalArgumentException("Il titolo non può essere vuoto");
            String[] secondo = text1.split(";",2);
            Money money = Money.parse(secondo[0]);
            StatoLibro statoLibro = StatoLibro.valueOf(secondo[1]);
            model.inVendita(codiceLibro,primo[1],money,statoLibro);
            view.showSuccess();
        }catch (IllegalArgumentException e){
            if (e.getMessage().startsWith("No enum constant it.unimi.di.sweng.esame.model.StatoLibro")) {
                view.showError("Stato libro non valido");
            } else {
                view.showError(e.getMessage());
            }

        }
    }
}
