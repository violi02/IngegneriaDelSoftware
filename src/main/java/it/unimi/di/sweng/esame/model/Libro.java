package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.StringJoiner;

public record Libro(@NotNull CodiceLibro codiceLibro, @NotNull String titolo, @NotNull Money prezzo, @NotNull StatoLibro statoLibro) {
    public static Comparator<Libro> comparator = Comparator.comparingInt((Libro o) -> o.prezzo.q()).thenComparing(o -> o.titolo);

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("  ");
        sj.add(codiceLibro.toString()).add(titolo).add(prezzo.toString()).add(statoLibro.toString());
        return sj.toString();
    }
}
