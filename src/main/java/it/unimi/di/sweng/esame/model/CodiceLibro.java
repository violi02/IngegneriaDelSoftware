package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class CodiceLibro {
    private final @NotNull String codice;


    public CodiceLibro(@NotNull String codice) {
        if (!codice.matches("^[A-Z]{1}[0-9]{3}")) throw new IllegalArgumentException("Invalid book code");
        this.codice = codice;
    }

    @Override
    public String toString() {
        return codice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodiceLibro that = (CodiceLibro) o;
        return Objects.equals(codice, that.codice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice);
    }
}
