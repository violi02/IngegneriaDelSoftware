package it.unimi.di.sweng.esame;

import org.jetbrains.annotations.NotNull;

public interface Observable<T> {
    void notifyObservers();
    void addObserver(@NotNull Observer<T> observer);
    @NotNull T getState();
}