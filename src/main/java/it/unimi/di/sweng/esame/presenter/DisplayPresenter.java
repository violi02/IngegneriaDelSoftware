package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.ModelObservable;
import it.unimi.di.sweng.esame.model.State;
import it.unimi.di.sweng.esame.model.Supplenze;
import it.unimi.di.sweng.esame.views.OutputView;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DisplayPresenter implements Observer<List<Supplenze>> {

    private final @NotNull StrategyOrdinamentoAndPrint strategy;
    private final @NotNull OutputView outputView;
    public DisplayPresenter(@NotNull ModelObservable model, @NotNull StrategyOrdinamentoAndPrint strategy, @NotNull OutputView outputView) {
        this.strategy = strategy;
        this.outputView = outputView;
        model.addObserver(this);
    }

    @Override
    public void update(@NotNull Observable<List<Supplenze>> observable) {
        List<Supplenze> tmp = observable.getState();
        strategy.sortOrdering(tmp);
        int i = 0;
        for (Supplenze supplenze : tmp) {
            if (i < Main.NUMVOCIELENCO) outputView.set(i++,strategy.formatPrint(supplenze));
        }
        while(i <Main.NUMVOCIELENCO){
            outputView.set(i++,"");
        }
    }
}
