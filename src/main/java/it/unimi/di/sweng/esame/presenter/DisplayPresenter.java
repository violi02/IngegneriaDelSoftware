package it.unimi.di.sweng.esame.presenter;

import it.unimi.di.sweng.esame.Main;
import it.unimi.di.sweng.esame.Observable;
import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.Libro;
import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.view.OutputView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DisplayPresenter implements Observer<List<Libro>> {

    private final @NotNull Model model;
    private final @NotNull OutputView view;

    public DisplayPresenter(@NotNull OutputView outputView, @NotNull Model model) {
        this.model = model;
        this.view = outputView;
        model.addObserver(this);
    }

    @Override
    public void update(@NotNull Observable<List<Libro>> observable) {
        List<Libro> tmp = observable.getState();
        tmp.sort(Libro.comparator);
        int i = 0;
        for (Libro libro : tmp) {
            if (i < Main.VIEWSIZE){
                view.set(i++,libro.toString());
            }
        }
        while(i < Main.VIEWSIZE) view.set(i++," ");
    }
}
