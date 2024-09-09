package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.model.Supplenze;

import java.util.List;

public interface StrategyOrdinamentoAndPrint {

    void sortOrdering(List<Supplenze> supplenze);

    String formatPrint(Supplenze s);
}
