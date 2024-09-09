package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record CodiceInsegnante(@NotNull String codice) {
    public CodiceInsegnante{
        if (!codice.matches("^[A-Z]{1}[0-9]{2}[A-Z]$"))
            throw new IllegalArgumentException("Codice insegnante non valido");
    }
}
