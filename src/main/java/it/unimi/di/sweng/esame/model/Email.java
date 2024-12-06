package it.unimi.di.sweng.esame.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Email {
    private final @NotNull String email;

    public Email(@NotNull String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) throw
                new IllegalArgumentException("Formato email errato");
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
