package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

public record Money(int q) {

    public Money{
        if (q < 0) throw new IllegalArgumentException("Quantità denaro negativa");
    }

    public static @NotNull Money parse(@NotNull String val) {
        try {
            String[] el = val.split("\\.");
            int euros = Integer.parseInt(el[0]);
            int cents = 0;
            if (el.length > 1) {
                cents = Integer.parseInt(el[1]);
                if (el[1].trim().length() == 1)
                    cents *= 10;
            }
            return new Money(euros * 100 + cents);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato numerico non valido");
        }
    }

    @Override
    public @NotNull String toString() {
        return String.format("%d.%02d", q / 100, q % 100);
    }


}
