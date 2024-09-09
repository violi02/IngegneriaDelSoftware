package it.unimi.di.sweng.esame;


import it.unimi.di.sweng.esame.model.ModelObservable;
import it.unimi.di.sweng.esame.model.Modello;
import it.unimi.di.sweng.esame.presenter.*;
import it.unimi.di.sweng.esame.views.DisplayView;
import it.unimi.di.sweng.esame.views.USRView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

  public static final int NUMPOSTAZIONI = 2;
  public static final int NUMVOCIELENCO = 8;

  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage) {

    primaryStage.setTitle("ufficio scolastico regionale");

    USRView[] inserimentoRichiesteView = new USRView[NUMPOSTAZIONI];
    USRView[] inserimentoAccettazioniView = new USRView[NUMPOSTAZIONI];

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      inserimentoRichiesteView[i] = new USRView("Inserisci");
    }

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      inserimentoAccettazioniView[i] = new USRView("Accetta");
    }

    DisplayView leftSideView = new DisplayView("In ordine di comune (e data)", NUMVOCIELENCO);
    DisplayView rightSideView = new DisplayView("In ordine di durata (e data)", NUMVOCIELENCO);

    GridPane gridPane = new GridPane();
    gridPane.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    gridPane.setPadding(new Insets(10, 10, 10, 10));

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      gridPane.add(inserimentoRichiesteView[i], i % 2, 0);
    }

    gridPane.add(leftSideView, 0, 1);
    gridPane.add(rightSideView, 1, 1);

    for (int i = 0; i < NUMPOSTAZIONI; i++) {
      gridPane.add(inserimentoAccettazioniView[i], i % 2,  2);
    }


    //TODO creare presenters e connettere model e view

    ModelObservable model = new ModelObservable();

    for (int i = 0; i <NUMPOSTAZIONI; i++) {
      new InputPresenter(inserimentoRichiesteView[i],model);
    }

    for (int i = 0; i <NUMPOSTAZIONI; i++) {
      new InputPresenter(inserimentoAccettazioniView[i],model);
    }

    new DisplayPresenter(model, OrdinamentoByComune.INSTANCE,leftSideView);
    new DisplayPresenter(model, OrdinamentoByDurata.INSTANCE,rightSideView);

    model.readFile();
    model.notifyObservers();

    Scene scene = new Scene(gridPane);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
