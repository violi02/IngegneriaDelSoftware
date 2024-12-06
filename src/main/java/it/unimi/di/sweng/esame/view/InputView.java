package it.unimi.di.sweng.esame.view;


import it.unimi.di.sweng.esame.presenter.InputPresenter;
import org.jetbrains.annotations.NotNull;

public interface InputView {
    void addHandlers(@NotNull InputPresenter presenter);

    void showError(@NotNull String s);
    void showSuccess();
    void showSuccess(@NotNull String s);
}
