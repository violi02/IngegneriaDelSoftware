package it.unimi.di.sweng.esame;

import it.unimi.di.sweng.esame.model.Model;
import it.unimi.di.sweng.esame.presenter.BuyPresenter;
import it.unimi.di.sweng.esame.presenter.DisplayPresenter;
import it.unimi.di.sweng.esame.presenter.SellPresenter;
import it.unimi.di.sweng.esame.view.DisplayView;
import it.unimi.di.sweng.esame.view.InputConcreteView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;


public class Main extends Application {
    public static final int VIEWSIZE = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull Stage primaryStage) {

        primaryStage.setTitle("Used Books Online Shop");

        DisplayView bacheca = new DisplayView(VIEWSIZE, "Available Books");

        InputConcreteView inputSell = new InputConcreteView("Code;Title", "Price;Condition", "SELL");
        InputConcreteView inputBuy = new InputConcreteView("Code", "Email", "BUY");

        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.add(inputSell, 0, 0);
        gridPane.add(bacheca, 0, 1);
        gridPane.add(inputBuy, 0, 2);


        //TODO: modificare e completare il seguente codice per istanziare e collegare i vari componenti

        Model model = new Model();
        model.readFile();
        new DisplayPresenter(bacheca,model);
        new SellPresenter(inputSell,model);
        new BuyPresenter(inputBuy,model);
        model.notifyObservers();

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
