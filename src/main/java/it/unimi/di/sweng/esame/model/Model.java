package it.unimi.di.sweng.esame.model;

import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Model extends ModelState implements Observable<List<Libro>> {

    private final @NotNull List<Observer<List<Libro>>> observers = new ArrayList<>();
    @Override
    public void notifyObservers() {
        for (Observer<List<Libro>> observer : observers) { observer.update(this);
        }
    }

    @Override
    public void inVendita(@NotNull CodiceLibro codiceLibro, @NotNull String titolo, @NotNull Money price, @NotNull StatoLibro statoLibro) {
        super.inVendita(codiceLibro,titolo,price,statoLibro);
        notifyObservers();
    }
    @Override
    public void buy(@NotNull CodiceLibro codice, @NotNull Email email) {
        super.buy(codice,email);
        notifyObservers();
    }

    @Override
    public void readFile() {
        super.readFile();
        notifyObservers();
    }

    @Override
    public void addObserver(@NotNull Observer<List<Libro>> observer) {
        observers.add(observer);
    }

    @Override
    public @NotNull List<Libro> getState() {
        return super.getLibri();
    }
}
