package model;

import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestModel {


    @Test
    void testInserisciSupplenza(){
        Modello modello = new Modello();
        modello.inserisciSupplenza(new CodiceIstituto("PP002"),1,"LO", LocalDate.now());
        assertThat(modello.getState().size()).isEqualTo(1);
    }

    @Test
    void testreadFileModelOk(){
        Modello modello = new Modello();
        modello.readFile();
        assertThat(modello.getState().size()).isEqualTo(7);
    }

    @Test
    void notifyObserversOK() {
        ModelObservable SUT = new ModelObservable();
        @SuppressWarnings("unchecked")
        Observer<List<Supplenze>> observer1 = mock(Observer.class);
        @SuppressWarnings("unchecked")
        Observer<List<Supplenze>> observer2 = mock(Observer.class);
        SUT.addObserver(observer1);
        SUT.addObserver(observer2);

        SUT.notifyObservers();

        verify(observer1).update(SUT);
        verify(observer2).update(SUT);
    }

    @Test
    void testToStringSupplenze(){
        Supplenze SUT = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.toString()).isEqualTo("Bergamo : dal 03/09/2023 - BG202");
    }

    @Test
    void testCompareToSupplenzeData(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("02/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDate;
        assertThat(comparator.compare(supplenze,supplenze1)).isEqualTo(1);
    }

    @Test
    void testCompareToSupplenzeComune(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Milano",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("02/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDate;
        assertThat(comparator.compare(supplenze,supplenze1)).isPositive();
    }

    @Test
    void testCompareToCodiceComune(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG200"),4,"Bergamo",
                LocalDate.parse("02/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDate;
        assertThat(comparator.compare(supplenze,supplenze1)).isPositive();
    }

    @Test
    void testCompareToEquals(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDate;
        assertThat(comparator.compare(supplenze,supplenze1)).isZero();
    }

    @Test
    void testGetStateModelObservable(){
        ModelObservable SUT = new ModelObservable();
        SUT.readFile();
        assertThat(SUT.getState().size()).isEqualTo(7);
    }

    @Test
    void testInserisciSupplenzaModelObservable(){
        ModelObservable SUT = new ModelObservable();
        SUT.inserisciSupplenza(new CodiceIstituto("BG202"),4,"Bergamo",LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.getState().size()).isEqualTo(1);
    }

    @Test
    void testCompareToSupplenzeDurata(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),7,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG202"),5,"Bergamo",
                LocalDate.parse("02/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDurata;
        assertThat(comparator.compare(supplenze,supplenze1)).isEqualTo(1);
    }

    @Test
    void testCompareToSupplenzeDurataEqual(){
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),7,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Supplenze supplenze1 = new Supplenze(new CodiceIstituto("BG202"),7,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        Comparator<Supplenze> comparator =  Supplenze.comparatorByDurata;
        assertThat(comparator.compare(supplenze,supplenze1)).isZero();
    }

    @Test
    void testToStringSupplenzeByDurata(){
        Supplenze SUT = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.formatString()).isEqualTo("4 : dal 03/09/2023 - BG202");
    }

    @Test
    void testAccettaSupplenzaModelObservable(){
        ModelObservable SUT = new ModelObservable();
        SUT.inserisciSupplenza(new CodiceIstituto("BG202"),4,"Bergamo",
                LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        SUT.accettaSupplenza(new CodiceInsegnante("P00P"),new CodiceIstituto("BG202") ,LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.getState().size()).isEqualTo(0);
    }
}
