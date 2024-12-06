package model;

import it.unimi.di.sweng.esame.Observer;
import it.unimi.di.sweng.esame.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ModelTest {

    @Test
    void testMoneyParse(){
        assertThrows(IllegalArgumentException.class, () -> {
            Money money = Money.parse("12,34");
        });
    }

    @Test
    void testLetturaFileOK(){
        ModelState SUT = new ModelState();
        SUT.readFile();
        assertThat(SUT.getLibri().size()).isEqualTo(2);
    }

    @Test
    void testInVendita(){
        ModelState SUT = new ModelState();
        SUT.inVendita(
                new CodiceLibro("A202"),"Follia",Money.parse("15.60"), StatoLibro.ASNEW);
        assertThat(SUT.getLibri().size()).isEqualTo(1);
    }

    @Test
    void testInVenditaConStessoCodice(){
        ModelState SUT = new ModelState();
        SUT.inVendita(
                new CodiceLibro("A202"),"Follia",Money.parse("15.60"), StatoLibro.ASNEW);
        assertThrows(IllegalArgumentException.class, () -> {
            SUT.inVendita(new CodiceLibro("A202"),"Follia 2",Money.parse("14.60"), StatoLibro.ASNEW);
        });
    }

    @Test
    void notifyObserverOK(){
        Model SUT = new Model();
        @SuppressWarnings("unchecked")
        Observer<List<Libro>> observer1 = mock(Observer.class);

        SUT.addObserver(observer1);

        SUT.notifyObservers();

        verify(observer1,times(1)).update(SUT);
    }

    @Test
    void modelGetState(){
        Model SUT = new Model();
        SUT.readFile();
        assertThat(SUT.getState().size()).isEqualTo(2);
    }

    @Test
    void compareLibri1(){
        Libro l1 = new Libro(new CodiceLibro("A202"),"Follia",Money.parse("15.60"), StatoLibro.ASNEW);
        Libro l2 = new Libro(new CodiceLibro("A202"),"Follia",Money.parse("12.60"), StatoLibro.ASNEW);
        assertThat(Libro.comparator.compare(l1,l2)).isPositive();
    }

    @Test
    void compareLibri2(){
        Libro l1 = new Libro(new CodiceLibro("A202"),"Follia",Money.parse("15.60"), StatoLibro.ASNEW);
        Libro l2 = new Libro(new CodiceLibro("A202"),"Quando abbiamo smesso di capire il mondo",Money.parse("15.60"), StatoLibro.ASNEW);
        assertThat(Libro.comparator.compare(l1,l2)).isNegative();
    }

    @Test
    void toStringLibri(){
        Libro l1 = new Libro(new CodiceLibro("A202"),"Follia",Money.parse("15.60"), StatoLibro.ASNEW);
        assertThat(l1.toString()).isEqualTo("A202  Follia  15.60  ASNEW");
    }

    @Test
    void modelReadFileNotify(){
        Model SUT = new Model();
        @SuppressWarnings("unchecked")
        Observer<List<Libro>> observer1 = mock(Observer.class);

        SUT.addObserver(observer1);
        SUT.readFile();

        verify(observer1,times(1)).update(SUT);
    }

    @Test
    void modelInVenditaNotify(){
        Model SUT = new Model();
        @SuppressWarnings("unchecked")
        Observer<List<Libro>> observer1 = mock(Observer.class);

        SUT.addObserver(observer1);
        SUT.inVendita(new CodiceLibro("K123"),"Norwegian Wood",Money.parse("22.23"),StatoLibro.GOOD);

        verify(observer1,times(1)).update(SUT);
    }

    @Test
    void toStringEmail(){
        Email email = new Email("violalicata@gmail.com");
        assertThat(email.toString()).isEqualTo("violalicata@gmail.com");
    }

    @Test
    void testModelBuy(){
        Model SUT = new Model();
        SUT.readFile();
        SUT.buy(new CodiceLibro("H001"),new Email("violalicata@gmail.com"));
        assertThat(SUT.getLibri().size()).isEqualTo(1);
    }

    @Test
    void testCompraNonInVendita(){
        ModelState SUT = new ModelState();
        SUT.readFile();
        assertThrows(IllegalArgumentException.class, () -> {
            SUT.buy(new CodiceLibro("A002"),new Email("violalicata@gmail.com"));
        });
    }

    @Test
    void modelBuyNotify(){
        Model SUT = new Model();
        @SuppressWarnings("unchecked")
        Observer<List<Libro>> observer1 = mock(Observer.class);

        SUT.addObserver(observer1);
        SUT.readFile();
        SUT.buy(new CodiceLibro("H001"),new Email("violalicata@gmail.com"));

        verify(observer1,times(2)).update(SUT);
    }
}
