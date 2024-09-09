package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record CodiceIstituto(@NotNull String codice) implements Comparable<CodiceIstituto> {

    public CodiceIstituto{
        if (!codice.matches("^[A-Z]{2}[0-9]{3}$")) throw new IllegalArgumentException("Codice istituto non valido");
    }

    @Override
    public String toString() {
        return codice;
    }


    @Override
    public int compareTo(@NotNull CodiceIstituto o) {
        int cmp = this.codice.compareTo(o.codice);
        if (cmp == 0) return 0;
        return this.codice.compareTo(o.codice) < 0 ? 1 : -1;
    }
}
