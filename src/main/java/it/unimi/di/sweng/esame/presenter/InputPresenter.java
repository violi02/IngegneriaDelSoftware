package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.CodiceInsegnante;
import it.unimi.di.sweng.esame.model.CodiceIstituto;
import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.views.InputView;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputPresenter implements Presenter{

    private final @NotNull InputView inputView;

    private final @NotNull State state;

    public InputPresenter(@NotNull InputView inputView, @NotNull State state) {
        this.inputView = inputView;
        this.state = state;
        inputView.addHandlers(this);
    }

    @Override
    public void action(String text1, String text2) {
        try{
            if (text1.equals("Inserisci")){
                String[] campi = text2.split(":");
                if (campi.length < 4) throw new IllegalArgumentException("Numero insufficiente di campi");
                CodiceIstituto codiceIstituto = new CodiceIstituto(campi[0]);
                int durata = Integer.parseInt(campi[1]);
                if (durata <= 0) throw new IllegalArgumentException("La durata deve essere positiva");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(campi[3], formatter);
                state.inserisciSupplenza(codiceIstituto,durata,campi[2], date);
                inputView.showSuccess();
            } else if (text1.equals("Accetta")){
                String[] campi = text2.split(":");
                CodiceInsegnante codiceInsegnante = new CodiceInsegnante(campi[0]);
                if (campi.length < 3) throw new IllegalArgumentException("Numero insufficiente di campi");
                CodiceIstituto codiceIstituto = new CodiceIstituto(campi[1]);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(campi[2], formatter);
                state.accettaSupplenza(codiceInsegnante,codiceIstituto,date);
                inputView.showSuccess();
            }
        }catch (IllegalArgumentException e){
            inputView.showError(e.getMessage());
        } catch (DateTimeParseException e) {
           inputView.showError("Data inizio non corretta");
        }
    }
}
