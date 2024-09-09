package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ModelObservable extends Modello implements Observable<List<Supplenze>> {

    private final @NotNull List<@NotNull Observer<@NotNull List< @NotNull Supplenze>>> observers = new ArrayList<>();
    @Override
    public void notifyObservers() {
        for (Observer<List<Supplenze>> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void addObserver(@NotNull Observer<List<Supplenze>> observer) {
        observers.add(observer);
    }

    @Override
    public void readFile(){
        super.readFile();
        notifyObservers();
    }

    @Override
    public void inserisciSupplenza(@NotNull CodiceIstituto codiceIstituto, int durata, @NotNull String comune, LocalDate data) {
        super.inserisciSupplenza(codiceIstituto,durata,comune,data);
        notifyObservers();
    }
    @Override
    public void accettaSupplenza(@NotNull CodiceInsegnante codiceInsegnante, @NotNull CodiceIstituto codiceIstituto, LocalDate data) {
        super.accettaSupplenza(codiceInsegnante,codiceIstituto,data);
        notifyObservers();
    }

    @Override
    public @NotNull List<Supplenze> getState() {
        return super.getState();
    }
}
