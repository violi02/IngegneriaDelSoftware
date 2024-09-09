package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.Supplenze;

import java.util.List;

public enum OrdinamentoByComune implements StrategyOrdinamentoAndPrint {
    INSTANCE;


    @Override
    public void sortOrdering(List<Supplenze> supplenze) {
        supplenze.sort(Supplenze.comparatorByDate);

    }


    @Override
    public String formatPrint(Supplenze s) {
        return s.toString();
    }
}
