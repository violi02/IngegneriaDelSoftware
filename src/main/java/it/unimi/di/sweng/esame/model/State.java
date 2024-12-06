package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public interface State {
    void inVendita(@NotNull CodiceLibro codiceLibro, @NotNull String titolo, @NotNull Money price, @NotNull StatoLibro statoLibro);

    void buy(@NotNull CodiceLibro codice,@NotNull Email email);
}
