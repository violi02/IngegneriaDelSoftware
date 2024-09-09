package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public interface State {
    void inserisciSupplenza(@NotNull CodiceIstituto codiceIstituto, int durata, @NotNull String comune, LocalDate data);

    void accettaSupplenza(@NotNull CodiceInsegnante codiceInsegnante,@NotNull CodiceIstituto codiceIstituto,LocalDate data);
}
