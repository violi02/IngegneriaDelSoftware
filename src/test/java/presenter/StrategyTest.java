package presenter;

import it.unimi.di.sweng.esame.model.CodiceIstituto;
import it.unimi.di.sweng.esame.model.Supplenze;
import it.unimi.di.sweng.esame.presenter.OrdinamentoByComune;
import it.unimi.di.sweng.esame.presenter.OrdinamentoByDurata;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyTest {

    Supplenze s1 = new Supplenze(new CodiceIstituto("PP002"),4,"Milano", LocalDate.now());
    Supplenze s2 =  new Supplenze( new CodiceIstituto("PP000"),2,"Bergamo", LocalDate.now());
    @Test
    void testOrder(){
        OrdinamentoByComune SUT = OrdinamentoByComune.INSTANCE;
        List<Supplenze> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        SUT.sortOrdering(list);
        assertThat(list).containsExactly(s2,s1) ;
    }


    @Test
    void testOrderByDurata(){
        OrdinamentoByDurata SUT = OrdinamentoByDurata.INSTANCE;
        List<Supplenze> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        SUT.sortOrdering(list);
        assertThat(list).containsExactly(s2,s1) ;
    }

    @Test
    void testToStringSupplenzeByDurata(){
        OrdinamentoByDurata SUT = OrdinamentoByDurata.INSTANCE;
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.formatPrint(supplenze)).isEqualTo("4 : dal 03/09/2023 - BG202");
    }

    @Test
    void testToStringSupplenzeByComune(){
        OrdinamentoByComune SUT = OrdinamentoByComune.INSTANCE;
        Supplenze supplenze = new Supplenze(new CodiceIstituto("BG202"),4,"Bergamo",LocalDate.parse("03/09/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        assertThat(SUT.formatPrint(supplenze)).isEqualTo("Bergamo : dal 03/09/2023 - BG202");
    }
}
